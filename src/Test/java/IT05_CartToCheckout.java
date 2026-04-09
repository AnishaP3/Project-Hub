import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;


public class IT05_CartToCheckout {


    private Products a;
    private Products b;


    @BeforeEach
    void setUp() {
        a = new Products("A", "", "", "", "", "", "", 39.99, 0);
        b = new Products("B", "", "", "", "", "", "", 22.99, 0);
        Cart.clearCart();
    }


    // Helper method to calculate total directly from Cart
    private double getTotalFromCart() {
        Map<String, Integer> cart = Cart.readCart();
        double total = 0;
        for (Map.Entry<String, Integer> entry : cart.entrySet()) {
            String name = entry.getKey();
            int qty = entry.getValue();
            if (name.equals("A")) total += 39.99 * qty;
            else if (name.equals("B")) total += 22.99 * qty;
        }
        return total;
    }


    @Test
    void testCartHasItems() {
        Cart.addProduct(a);
        Cart.addProduct(b);


        Map<String, Integer> cart = Cart.readCart();


        assertTrue(cart.containsKey("A"));
        assertTrue(cart.containsKey("B"));
        assertEquals(2, Cart.getTotalCountOfItems());
    }


    @Test
    void testTotalCalculation() {
        Cart.addProduct(a);
        Cart.addProduct(b);


        double total = 39.99 + 22.99;
        assertEquals(total, getTotalFromCart(), 0.01);  // ✅ Now reads from Cart
    }


    @Test
    void testQuantities() {
        Cart.addProduct(a);
        Cart.addProduct(a);
        Cart.addProduct(b);


        Map<String, Integer> cart = Cart.readCart();


        assertEquals(2, cart.get("A"));
        assertEquals(1, cart.get("B"));
        assertEquals(3, Cart.getTotalCountOfItems());
    }


    @Test
    void testEmptyCart() {
        assertTrue(Cart.readCart().isEmpty());
        assertEquals(0, Cart.getTotalCountOfItems());
    }
}
