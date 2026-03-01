import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Products {
    String name;
    String imagePath;
    String details;
    double price;

    Products(String name, String imagePath, String details, double price) {
        this.name = name;
        this.imagePath = imagePath;
        this.details = details;
        this.price = price;
    }
    Products(){}

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getImagePath() {
        return imagePath;
    }
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public String getDetails() {
        return details;
    }
    public void setDetails(String details) {
        this.details = details;
    }

    // Helper Method to Read CSV File
    public static List<Products> readProductCSV(ClassLoader classLoader) {
        List<Products> products = new ArrayList<>();

        // Use getResourceAsStream to load the CSV file from the resources folder
        try (InputStream inputStream = classLoader.getResourceAsStream("csv/products.csv")) {
            if (inputStream == null) {
                throw new RuntimeException("CSV file not found in resources folder.");
            }

            try (Scanner scanner = new Scanner(inputStream)) {
                if (scanner.hasNextLine()) {
                    scanner.nextLine();
                }

                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] parts = line.split("\\|");

                    if (parts.length == 4) {
                        String name = parts[0].trim();
                        double price = Double.parseDouble(parts[1].trim());
                        String imagePath = parts[2].trim();
                        String details = parts[3].trim();

                        // Create Product object and add to list
                        Products product = new Products(name, imagePath, details, price);
                        products.add(product);
                    }
                    else {
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