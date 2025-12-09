import java.time.Instant;

public class Product {

    private int id;
    private String name;
    private Instant creationDatetime;
    private Category category;       

    // Constructeur
    public Product(int id, String name, Instant creationDatetime, Category category) {
        this.id = id;
        this.name = name;
        this.creationDatetime = creationDatetime;
        this.category = category;
    }

    // Getters et setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Instant getCreationDatetime() { return creationDatetime; }
    public void setCreationDatetime(Instant creationDatetime) { this.creationDatetime = creationDatetime; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }


}
