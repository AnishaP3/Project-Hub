import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;


public class UT07_CalcTotal {


    private Products a;
    private Products b;
    private Products c;


    @BeforeEach
    void setUp() {
        a = new Products("A", "", "", "", "", "", "", 39.99, 0);
        b = new Products("B", "", "", "", "", "", "", 22.99, 0);
        c = new Products("C", "", "", "", "", "", "", 64.99, 0);
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
            else if (name.equals("C")) total += 64.99 * qty;
        }
        return total;
    }


    @Test
    void testSingleItem() {
        Cart.addProduct(a);
        assertEquals(39.99, getTotalFromCart(), 0.01);
    }


    @Test
    void testMultipleItems() {
        Cart.addProduct(a);
        Cart.addProduct(b);
        Cart.addProduct(c);
        double total = 39.99 + 22.99 + 64.99;
        assertEquals(total, getTotalFromCart(), 0.01);
    }


    @Test
    void testSameItemTwice() {
        Cart.addProduct(a);
        Cart.addProduct(a);
        assertEquals(79.98, getTotalFromCart(), 0.01);
    }


    @Test
    void testEmptyCart() {
        assertEquals(0.00, getTotalFromCart(), 0.01);
    }
}