import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProductsTest {

    // Test Getters
    @Test
    void getters() {
        // Create a test product
        Products product = new Products("Casual Hoodie", "/clothes/casualHoodie.jpg", "A relaxed everyday casual wear hoodie, perfect for lounging or running errands. Material: 80% Cotton, 20% Polyester. Color: Heather Grey. Sizes Available: S, L. Price: $39.99", "Hoodie", "Grey", "Cotton,Polyester", "Small,Large", 39.99, 4.5);

        // Using the getters
        double rating = product.getRating();
        String name = product.getName();
        double price = product.getPrice();
        String category = product.getCategory();
        String material = product.getMaterial();
        String size = product.getSize();
        String colour = product.getColor();

        // Assert
        assertEquals("Casual Hoodie", name);
        assertEquals(4.5, rating, 0.0);
        assertEquals(39.99, price, 0.0);
        assertEquals("Cotton,Polyester", material);
        assertEquals("Small,Large", size);
        assertEquals("Grey", colour);
        assertEquals("Hoodie", category);
    }

    // Test Setters
    @Test
    void setters() {
        // Creating test product
        Products product = new Products("Casual Hoodie", "/clothes/casualHoodie.jpg", "A relaxed everyday casual wear hoodie, perfect for lounging or running errands. Material: 80% Cotton, 20% Polyester. Color: Heather Grey. Sizes Available: S, L. Price: $39.99", "Hoodie", "Grey", "Cotton,Polyester", "Small,Large", 39.99, 4.5);

        // Testing setters
        product.setRating(3.8);
        product.setName("Black Hoodie");
        product.setPrice(30.00);
        product.setSize("Medium");

        // Seeing if they return the changed values
        double rating = product.getRating();
        String name = product.getName();
        double price = product.getPrice();
        String category = product.getCategory();
        String material = product.getMaterial();
        String size = product.getSize();
        String colour = product.getColor();

        // Assert
        assertEquals("Black Hoodie", name);
        assertEquals(3.8, rating, 0.0);
        assertEquals(30.00, price, 0.0);
        assertEquals("Cotton,Polyester", material);
        assertEquals("Medium", size);
        assertEquals("Grey", colour);
        assertEquals("Hoodie", category);
    }

    // Testing loading CSV
    @Test
    void loadCSV() {

        // Load products from csv
        List<Products> products = Products.readProductCSV(getClass().getClassLoader());

        // Asserts
        assertNotNull(products);
        assertFalse(products.isEmpty());

        // Check first product has valid data
        Products first = products.get(0);
        assertFalse(first.getName().isEmpty());
        assertFalse(first.getCategory().isEmpty());
        assertFalse(first.getColor().isEmpty());
        assertFalse(first.getMaterial().isEmpty());
        assertFalse(first.getSize().isEmpty());
        assertFalse(first.getImagePath().isEmpty());
        assertFalse(first.getDetails().isEmpty());
        assertFalse(first.getPrice() <= 0);
        assertFalse(first.getRating() < 0);
    }

    //testing if products are read correctly from the CSV file
    @Test
    void readProductsCSV(){
        ClassLoader classLoader = getClass().getClassLoader();
        List<Products> products = Products.readProductCSV(classLoader);

        assertNotNull(products);

        Products products1 = products.get(0);
        assertEquals("Casual Hoodie", products1.getName());
        assertEquals(39.99, products1.getPrice());
        assertEquals("Hoodie", products1.getCategory());
        assertEquals("Grey", products1.getColor());
        assertEquals("Cotton,Polyester", products1.getMaterial());
        assertEquals("Small,Large", products1.getSize());
        assertEquals(4.5, products1.getRating());
    }
}



