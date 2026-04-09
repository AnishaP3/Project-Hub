import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;


public class UT08_AddToCart {


    private Products item;


    @BeforeEach
    void setUp() {
        item = new Products("Hoodie", "", "", "", "", "", "", 39.99, 0);
        Cart.clearCart();
    }


    @Test
    void testAddOnce() {
        Cart.addProduct(item);
        assertEquals(1, Cart.readCart().get("Hoodie"));
        assertEquals(1, Cart.getTotalCountOfItems());
    }


    @Test
    void testAddTwice() {
        Cart.addProduct(item);
        Cart.addProduct(item);
        assertEquals(2, Cart.readCart().get("Hoodie"));
        assertEquals(2, Cart.getTotalCountOfItems());
    }


    @Test
    void testAddDifferent() {
        Products a = new Products("A", "", "", "", "", "", "", 10, 0);
        Products b = new Products("B", "", "", "", "", "", "", 20, 0);


        Cart.addProduct(a);
        Cart.addProduct(b);


        assertEquals(2, Cart.getTotalCountOfItems());
    }
}
