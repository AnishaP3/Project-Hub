import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Products {
    private String name;
    private String imagePath;
    private String details;
    private String category;
    private String color;
    private String material;
    private String size;
    private double price;
    private double rating;

    // Constructor
    Products(String name, String imagePath, String details, String category, String color, String material, String size, double price, double rating) {
        this.name = name;
        this.imagePath = imagePath;
        this.details = details;
        this.category = category;
        this.color = color;
        this.material = material;
        this.size = size;
        this.price = price;
        this.rating = rating;
    }

    Products(){}

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    public String getMaterial() { return material; }
    public void setMaterial(String material) { this.material = material; }
    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }
    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    // Helper Method to Read CSV File
    public static List<Products> readProductCSV(ClassLoader classLoader) {
        List<Products> products = new ArrayList<>();

        try (InputStream inputStream = classLoader.getResourceAsStream("Products/products.csv")) {
            if (inputStream == null) {
                throw new RuntimeException("CSV file not found in resources folder.");
            }

            try (Scanner scanner = new Scanner(inputStream)) {
                if (scanner.hasNextLine()) {
                    scanner.nextLine(); // skip header
                }

                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] parts = line.split("\\|");

                    if (parts.length == 9) {
                        String name = parts[0].trim();
                        double price = Double.parseDouble(parts[1].trim());
                        String imagePath = parts[2].trim();
                        String details = parts[3].trim();
                        String category = parts[4].trim();
                        String color = parts[5].trim();
                        String  material = parts[6].trim();
                        String  size = parts[7].trim();
                        double rating = Double.parseDouble(parts[8].trim());

                        products.add(new Products(name, imagePath, details, category, color, material, size, price, rating));
                    } else {
                        System.out.println("Skipping invalid line: " + line);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error reading products from CSV file", e);
        }

        return products;
    }
}