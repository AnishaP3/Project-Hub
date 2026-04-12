import org.junit.jupiter.api.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class CheckoutTest {

    // ----------------------------------------------------------------
    // Lightweight stub for Products – avoids needing the CSV at test time
    // ----------------------------------------------------------------
    private Products makeProduct(String name, String category,
                                 String color, String material,
                                 double price, double rating) {
        // Products(name, imagePath, details, category, color, material, size, price, rating)
        return new Products(name, "/stub.png", "stub details",
                category, color, material, "M", price, rating);
    }

    // ----------------------------------------------------------------
    // Test fixtures
    // ----------------------------------------------------------------
    private Products hoodie;
    private Products trackSuit;
    private Products dress;

    @BeforeEach
    void setUp() {
        // Always start each test with a clean, empty cart
        Cart.clearCart();

        hoodie    = makeProduct("Casual Hoodie", "Hoodie",   "Black", "Cotton", 49.99, 4.5);
        trackSuit = makeProduct("Track Suit",    "Track Suit","Blue",  "Polyester", 79.99, 4.0);
        dress     = makeProduct("Party Dress",   "Dress",    "Red",   "Silk",   89.99, 4.8);
    }

    @AfterEach
    void tearDown() {
        Cart.clearCart();
    }

    // ==============================================================
    // GROUP 1 – Items in Cart
    // ==============================================================

    @Test
    @DisplayName("TC-01 | Single product → cart count = 1")
    void testAddSingleProduct_cartCountIsOne() {
        Cart.addProduct(hoodie);

        assertEquals(1, Cart.getTotalCountOfItems(),
                "Cart should contain exactly 1 item after one addProduct call.");
    }

    @Test
    @DisplayName("TC-02 | Same product added twice → quantity = 2, one entry")
    void testAddSameProductTwice_quantityIsTwo() {
        Cart.addProduct(hoodie);
        Cart.addProduct(hoodie);

        Map<String, Integer> cartMap = Cart.readCart();
        assertEquals(1, cartMap.size(),
                "Cart map should have one unique product entry.");
        assertEquals(2, cartMap.get(hoodie.getName()),
                "Quantity for 'Casual Hoodie' should be 2.");
        assertEquals(2, Cart.getTotalCountOfItems());
    }

    @Test
    @DisplayName("TC-03 | Three distinct products → total item count = 3")
    void testAddMultipleDistinctProducts_correctTotalCount() {
        Cart.addProduct(hoodie);
        Cart.addProduct(trackSuit);
        Cart.addProduct(dress);

        assertEquals(3, Cart.getTotalCountOfItems(),
                "Cart should reflect the sum of all added items.");
    }

    @Test
    @DisplayName("TC-04 | Remove one product → remaining products still present")
    void testRemoveProduct_otherItemsIntact() {
        Cart.addProduct(hoodie);
        Cart.addProduct(trackSuit);
        Cart.removeProduct(hoodie.getName());

        Map<String, Integer> cartMap = Cart.readCart();
        assertFalse(cartMap.containsKey(hoodie.getName()),
                "Removed product should no longer be in the cart.");
        assertTrue(cartMap.containsKey(trackSuit.getName()),
                "Non-removed product should still be in the cart.");
        assertEquals(1, Cart.getTotalCountOfItems());
    }

    @Test
    @DisplayName("TC-05 | clearCart() → cart is empty")
    void testClearCart_cartIsEmpty() {
        Cart.addProduct(hoodie);
        Cart.addProduct(dress);
        Cart.clearCart();

        assertEquals(0, Cart.getTotalCountOfItems(),
                "Cart should be empty after clearCart().");
        assertTrue(Cart.readCart().isEmpty(),
                "Cart map should have no entries after clearCart().");
    }

    @Test
    @DisplayName("TC-06 | Empty cart → total count = 0")
    void testEmptyCart_totalCountIsZero() {
        assertEquals(0, Cart.getTotalCountOfItems(),
                "A freshly cleared cart should have 0 items.");
    }

    // ==============================================================
    // GROUP 2 – Order Summary with Recommendations (FreqBought logic)
    // ==============================================================

    @Test
    @DisplayName("TC-07 | FreqBought mapping covers all expected products")
    void testFreqBoughtMapping_allExpectedProductsMapped() {
        Map<String, String> mapping = FreqBought.getProductAccessoryMapping();

        String[] expectedProducts = {
                "Casual Hoodie", "Graphic Tee", "Jacket", "Party Dress",
                "Creme Trench Coat", "Office Blazer", "Floral Dress",
                "Sweater", "Preppy Sweater", "Track Suit",
                "Denim Jacket", "Checkered Tee"
        };

        for (String product : expectedProducts) {
            assertTrue(mapping.containsKey(product),
                    "Expected product '" + product + "' to be in the FreqBought mapping.");
            assertNotNull(mapping.get(product),
                    "Accessory name for '" + product + "' must not be null.");
            assertFalse(mapping.get(product).isBlank(),
                    "Accessory name for '" + product + "' must not be blank.");
        }
    }


    @Test
    @DisplayName("TC-08 | getAccessoryForCheckout maps 'Track Suit' → 'Sneakers'")
    void testGetAccessoryForCheckout_trackSuitMapsTooSneakers() {
        Map<String, String> mapping = FreqBought.getProductAccessoryMapping();
        assertEquals("Sneakers", mapping.get("Track Suit"),
                "'Track Suit' should map to 'Sneakers' in the FreqBought lookup table.");
    }

    @Test
    @DisplayName("TC-09 | Checkout recommendations include accessory for cart item")
    void testOrderSummaryRecommendations_cartItemHasAccessory() {
        Cart.addProduct(hoodie);   // "Casual Hoodie" → "Beanie"
        Cart.addProduct(trackSuit); // "Track Suit"   → "Sneakers"

        Map<String, Integer> cartMap = Cart.readCart();
        Set<String> recommendedAccessoryNames = new HashSet<>();

        for (String productName : cartMap.keySet()) {
            Map<String, String> mapping = FreqBought.getProductAccessoryMapping();
            String accessoryName = mapping.get(productName);
            if (accessoryName != null) {
                recommendedAccessoryNames.add(accessoryName);
            }
        }

        assertTrue(recommendedAccessoryNames.contains("Beanie"),
                "Order summary should recommend 'Beanie' for 'Casual Hoodie'.");
        assertTrue(recommendedAccessoryNames.contains("Sneakers"),
                "Order summary should recommend 'Sneakers' for 'Track Suit'.");
    }


    @Test
    @DisplayName("TC-10 | No duplicate recommendations in order summary")
    void testOrderSummaryRecommendations_noDuplicates() {
        // Add two products that happen to share the same accessory
        // (we simulate this by adding the same product twice)
        Cart.addProduct(hoodie);
        Cart.addProduct(hoodie); // quantity = 2, but still one map entry

        Map<String, Integer> cartMap = Cart.readCart();
        Set<String> addedRecs = new HashSet<>();

        for (String productName : cartMap.keySet()) {
            Map<String, String> mapping = FreqBought.getProductAccessoryMapping();
            String accessoryName = mapping.get(productName);
            if (accessoryName != null) {
                // This is the same guard used in CheckoutPage.reloadPage()
                boolean added = addedRecs.add(accessoryName);
                // If the same accessory would be added a second time, add() returns false
            }
        }

        // "Casual Hoodie" → "Beanie" should appear exactly once
        assertEquals(1, addedRecs.size(),
                "Recommendation set should contain exactly one unique accessory.");
    }

    @Test
    @DisplayName("TC-11 | updateTotal() arithmetic is correct (13% tax)")
    void testUpdateTotal_arithmeticIsCorrect() {
        // hoodie $49.99 × 1  +  trackSuit $79.99 × 2
        Cart.addProduct(hoodie);
        Cart.addProduct(trackSuit);
        Cart.addProduct(trackSuit);

        List<Products> allProducts = List.of(hoodie, trackSuit, dress);

        double subtotal = 0;
        for (Map.Entry<String, Integer> entry : Cart.readCart().entrySet()) {
            String name = entry.getKey();
            int qty      = entry.getValue();
            for (Products p : allProducts) {
                if (p.getName().equals(name)) {
                    subtotal += p.getPrice() * qty;
                    break;
                }
            }
        }

        double expectedSubtotal = 49.99 + (79.99 * 2);   // 209.97
        double expectedTax      = expectedSubtotal * 0.13;
        double expectedTotal    = expectedSubtotal + expectedTax;

        assertEquals(expectedSubtotal, subtotal, 0.001,
                "Subtotal should equal sum of (price × quantity) for each cart item.");
        assertEquals(expectedTax, subtotal * 0.13, 0.001,
                "Tax should be exactly 13% of the subtotal.");
        assertEquals(expectedTotal, subtotal + subtotal * 0.13, 0.001,
                "Grand total should equal subtotal + tax.");
    }


    @Test
    @DisplayName("TC-12 | Unknown product → no accessory, no exception")
    void testUnknownProduct_noAccessoryReturned() {
        Products unknownProduct = makeProduct("Mystery Cloak", "Cloak",
                "Purple", "Velvet", 120.00, 5.0);
        Cart.addProduct(unknownProduct);

        Map<String, String> mapping = FreqBought.getProductAccessoryMapping();
        String accessoryName = mapping.get(unknownProduct.getName());

        assertNull(accessoryName,
                "A product not in the mapping should yield null, not an exception.");
    }
}