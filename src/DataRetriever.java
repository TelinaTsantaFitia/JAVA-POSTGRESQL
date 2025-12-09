import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class DataRetriever {

    private Connection connection;

    public DataRetriever() {
        DBConnection db = new DBConnection();
        this.connection = db.getDBConnection();
    }

    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT id, name FROM Product_category";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                categories.add(new Category(
                        rs.getInt("id"),
                        rs.getString("name")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories;
    }

    
    public List<Product> getProductList(int page, int size) {
        List<Product> products = new ArrayList<>();
        int offset = (page - 1) * size;

        String sql = "SELECT p.id, p.name, p.creation_datetime, c.id AS cat_id, c.name AS cat_name " +
                     "FROM Product p " +
                     "LEFT JOIN Product_category c ON p.id = c.product_id " +
                     "ORDER BY p.id " +
                     "LIMIT ? OFFSET ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, size);
            ps.setInt(2, offset);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Category category = null;
                if (rs.getInt("cat_id") != 0) {
                    category = new Category(rs.getInt("cat_id"), rs.getString("cat_name"));
                }

                Product product = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getTimestamp("creation_datetime").toInstant(),
                        category
                );

                products.add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    public List<Product> getProductsByCriteria(String productName, String categoryName,
                                               Instant creationMin, Instant creationMax) {
        List<Product> products = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
            "SELECT p.id, p.name, p.creation_datetime, c.id AS cat_id, c.name AS cat_name " +
            "FROM Product p LEFT JOIN Product_category c ON p.id = c.product_id WHERE 1=1"
        );

        if (productName != null) {
            sql.append(" AND p.name ILIKE ?");
        }
        if (categoryName != null) {
            sql.append(" AND c.name ILIKE ?");
        }
        if (creationMin != null) {
            sql.append(" AND p.creation_datetime >= ?");
        }
        if (creationMax != null) {
            sql.append(" AND p.creation_datetime <= ?");
        }

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            int index = 1;

            if (productName != null) ps.setString(index++, "%" + productName + "%");
            if (categoryName != null) ps.setString(index++, "%" + categoryName + "%");
            if (creationMin != null) ps.setTimestamp(index++, Timestamp.from(creationMin));
            if (creationMax != null) ps.setTimestamp(index++, Timestamp.from(creationMax));

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Category category = null;
                if (rs.getInt("cat_id") != 0) {
                    category = new Category(rs.getInt("cat_id"), rs.getString("cat_name"));
                }

                Product product = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getTimestamp("creation_datetime").toInstant(),
                        category
                );

                products.add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    
}
