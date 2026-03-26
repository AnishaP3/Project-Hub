import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class CartTest {
    // Clears the cart
    @Before
    public void setUp() {
        Cart.clearCart();
    }

    @Test
    public void testAddProduct() {
        // Create a test product
        Products product = new Products("Casual Hoodie", "/clothes/casualHoodie.jpg", "A relaxed everyday casual wear hoodie, perfect for lounging or running errands. Material: 80% Cotton, 20% Polyester. Color: Heather Grey. Sizes Available: S, L. Price: $39.99", "Hoodie", "Grey", "Cotton,Polyester", "Small,Large", 39.99, 4.5);

        //Test the method
        Cart.addProduct(product);
        Map<String, Integer> cart = Cart.readCart();

        // Assert
        assertEquals(1, cart.size());
        assertTrue(cart.containsKey("Casual Hoodie"));
        assertEquals(1, (int) cart.get("Casual Hoodie"));
    }

    @Test
    public void testRemoveProduct() {
        // Create a product
        Products product = new Products("Casual Hoodie", "/clothes/casualHoodie.jpg", "A relaxed everyday casual wear hoodie, perfect for lounging or running errands. Material: 80% Cotton, 20% Polyester. Color: Heather Grey. Sizes Available: S, L. Price: $39.99", "Hoodie", "Grey", "Cotton,Polyester", "Small,Large", 39.99, 4.5);
        Products productB = new Products("Preppy Sweater", "/clothes/preppy.jpg", "A refined smart-casual / outing sweater ideal for college settings, outings, and casual Fridays. Material: Cotton Blend. Color: Beige. Sizes Available: S, M. Price: $45.99" ,"Sweater", "Beige", "Cotton", "Small,Medium", 45.99, 3.5);

        // Test the methods
        Cart.addProduct(product);
        Cart.addProduct(productB);
        Cart.removeProduct("Casual Hoodie");
        Map<String, Integer> cart = Cart.readCart();

        // Assert
        assertEquals(1, cart.size());
    }

    @Test
    public void testTotalCount() {
        // Create products
        Products productA = new Products("Casual Hoodie", "/clothes/casualHoodie.jpg", "A relaxed everyday casual wear hoodie, perfect for lounging or running errands. Material: 80% Cotton, 20% Polyester. Color: Heather Grey. Sizes Available: S, L. Price: $39.99", "Hoodie", "Grey", "Cotton,Polyester", "Small,Large", 39.99, 4.5);
        Products productB = new Products("Preppy Sweater", "/clothes/preppy.jpg", "A refined smart-casual / outing sweater ideal for college settings, outings, and casual Fridays. Material: Cotton Blend. Color: Beige. Sizes Available: S, M. Price: $45.99" ,"Sweater", "Beige", "Cotton", "Small,Medium", 45.99, 3.5);

        // Test methods
        Cart.addProduct(productA);
        Cart.addProduct(productA);
        Cart.addProduct(productB);

        // Assert
        assertEquals(3, Cart.getTotalCountOfItems());
    }

    @Test
    public void testEmptyCartCount() {
        assertEquals(0, Cart.getTotalCountOfItems());
    }

    @Test
    public void testClearCart() {
        // Create a product
        Products product = new Products("Casual Hoodie", "/clothes/casualHoodie.jpg", "A relaxed everyday casual wear hoodie, perfect for lounging or running errands. Material: 80% Cotton, 20% Polyester. Color: Heather Grey. Sizes Available: S, L. Price: $39.99", "Hoodie", "Grey", "Cotton,Polyester", "Small,Large", 39.99, 4.5);

        // Test methods
        Cart.addProduct(product);
        Cart.clearCart();

        // Assert
        assertEquals(0, Cart.readCart().size());
    }
}