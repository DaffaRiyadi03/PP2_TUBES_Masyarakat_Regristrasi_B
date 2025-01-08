package tubes.ewaste.model;

public class JenisSampah {
    private int id;
    private String name;
    private String description;
    private int categoryId;  // Kategori yang terkait dengan jenis item
    private String imageUrl; // Tambahkan field imageUrl
    private Kategori category;  // Jika ingin menampilkan objek kategori secara penuh

    // Getters dan setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getImageUrl() { // Tambahkan getter untuk imageUrl
         return imageUrl;
     }

     public void setImageUrl(String imageUrl) { // Tambahkan setter untuk imageUrl
         this.imageUrl = imageUrl;
     }

    public Kategori getCategory() {
        return category;
    }

    public void setCategory(Kategori category) {
        this.category = category;
    }
    @Override
    public String toString() {
        return name; // This will display the category name in the ComboBox
    }
}
