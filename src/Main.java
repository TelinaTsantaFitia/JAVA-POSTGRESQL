import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        DataRetriever retriever = new DataRetriever();
        List<Category> categories = retriever.getAllCategories();
        for (Category cat : categories) {
            System.out.println(cat);
        }

        List<Product> productsPage1 = retriever.getProductList(1, 3);
        for (Product p : productsPage1) {
            System.out.println(p);
        }

        List<Product> productsPage2 = retriever.getProductList(2, 3);
        for (Product p : productsPage2) {
            System.out.println(p);
        }

        Instant dateMin = Instant.now().minus(90, ChronoUnit.DAYS); // il y a 90 jours
        Instant dateMax = Instant.now(); // maintenant
        List<Product> filteredProducts = retriever.getProductsByCriteria(
                "Laptop",      // productName
                "Informatique",// categoryName
                dateMin,       // creationMin
                dateMax        // creationMax
        );
        for (Product p : filteredProducts) {
            System.out.println(p);
        }
    }
}
