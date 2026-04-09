import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;


public class IT04_ProductToCart {


    private Products item;


    @BeforeEach
    void setUp() {
        item = new Products("Hoodie", "", "", "", "", "", "", 39.99, 0);
        Cart.clearCart();
    }


    @Test
    void testAddFromProductCard() {
        Cart.addProduct(item);
        assertTrue(Cart.readCart().containsKey("Hoodie"));
        assertEquals(1, Cart.readCart().get("Hoodie"));
    }


    @Test
    void testAddMultipleTimes() {
        Cart.addProduct(item);
        Cart.addProduct(item);
        Cart.addProduct(item);
        assertEquals(3, Cart.readCart().get("Hoodie"));
        assertEquals(3, Cart.getTotalCountOfItems());
    }


    @Test
    void testAddDifferentItems() {
        Products a = new Products("A", "", "", "", "", "", "", 10, 0);
        Products b = new Products("B", "", "", "", "", "", "", 20, 0);


        Cart.addProduct(a);
        Cart.addProduct(b);


        assertTrue(Cart.readCart().containsKey("A"));
        assertTrue(Cart.readCart().containsKey("B"));
        assertEquals(2, Cart.getTotalCountOfItems());
    }
}
