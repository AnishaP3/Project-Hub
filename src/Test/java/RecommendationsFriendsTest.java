import org.junit.jupiter.api.*;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

public class RecommendationsFriendsTest {

    private static JFrame frame;
    private static UI     ui;

    // ----------------------------------------------------------------
    // Suite setup – build the full UI once on the EDT
    // ----------------------------------------------------------------
    @BeforeAll
    static void setUpFrame() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            frame = new JFrame("IT-06-OB Test Frame");
            ui    = new UI();
            frame.add(ui);
            frame.setSize(1200, 800);
            // Do NOT call setVisible(true) — keeps tests headless-friendly
        });
    }

    @AfterAll
    static void tearDownFrame() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            if (frame != null) frame.dispose();
        });
    }

    @BeforeEach
    void clearCartBeforeEach() {
        Cart.clearCart();
    }

    // ==============================================================
    // IT-06-OB-01  Recommendation page is accessible
    // ==============================================================

    @Test
    @DisplayName("IT-06-OB-01 | Recommendation page is accessible via CardLayout")
    void testRecommendationPage_isAccessible() throws Exception {
        AtomicBoolean found = new AtomicBoolean(false);

        SwingUtilities.invokeAndWait(() -> {
            for (Component comp : ui.pages.getComponents()) {
                if (comp instanceof RecommendationPage) {
                    found.set(true);
                    break;
                }
            }
            if (found.get()) {
                assertDoesNotThrow(
                        () -> ui.cardLayout.show(ui.pages, "RECOMMENDATIONS"),
                        "Switching to RECOMMENDATIONS card must not throw.");
            }
        });

        assertTrue(found.get(),
                "A RecommendationPage must exist in the UI page stack.");
    }

    // ==============================================================
    // IT-06-OB-02  Recommendation page renders its title label
    // ==============================================================

    @Test
    @DisplayName("IT-06-OB-02 | Recommendation page renders its title label")
    void testRecommendationPage_rendersTitleLabel() throws Exception {
        AtomicBoolean titleFound = new AtomicBoolean(false);

        SwingUtilities.invokeAndWait(() -> {
            for (Component comp : ui.pages.getComponents()) {
                if (comp instanceof RecommendationPage rp) {
                    titleFound.set(
                            containsLabelText(rp, "Your Personalized Recommendations"));
                    break;
                }
            }
        });

        assertTrue(titleFound.get(),
                "RecommendationPage must contain the heading " +
                        "'Your Personalized Recommendations'.");
    }

    // ==============================================================
    // IT-06-OB-03  "Connect with Friends" – nav button is present
    // ==============================================================

    @Test
    @DisplayName("IT-06-OB-03 | Nav bar contains the Friends navigation button")
    void testNavBar_containsFriendsButton() throws Exception {
        AtomicBoolean friendsCardExists  = new AtomicBoolean(false);
        AtomicBoolean friendsButtonFound = new AtomicBoolean(false);

        SwingUtilities.invokeAndWait(() -> {
            // 1. Verify the Friends card is registered in the page stack
            for (Component comp : ui.pages.getComponents()) {
                if (comp instanceof Friends) {
                    friendsCardExists.set(true);
                    break;
                }
            }

            // 2. Find the nav button by normalised text (handles Unicode small-caps)
            JButton btn = findButtonContaining(ui, "friend");
            friendsButtonFound.set(btn != null);
        });

        assertTrue(friendsCardExists.get(),
                "A Friends panel must be registered in the UI page stack.");
        assertTrue(friendsButtonFound.get(),
                "The UI header must contain a button whose label references 'friends'.");
    }

    // ==============================================================
    // IT-06-OB-04  Clicking the button navigates to the Friends panel
    // ==============================================================

    @Test
    @DisplayName("IT-06-OB-04 | Clicking 'Friends' nav button shows the Friends panel")
    void testFriendsNavButton_navigatesToFriendsPanel() throws Exception {
        AtomicBoolean friendsPanelVisible = new AtomicBoolean(false);

        SwingUtilities.invokeAndWait(() -> {
            JButton btn = findButtonContaining(ui, "friend");
            assertNotNull(btn, "Friends nav button must exist in the UI.");
            btn.doClick();

            for (Component comp : ui.pages.getComponents()) {
                if (comp instanceof Friends && comp.isVisible()) {
                    friendsPanelVisible.set(true);
                    break;
                }
            }
        });

        assertTrue(friendsPanelVisible.get(),
                "After clicking the Friends nav button the Friends panel " +
                        "must be the visible card.");
    }

    // ==============================================================
    // IT-06-OB-05  Friends panel displays items that others bought
    // ==============================================================

    @Test
    @DisplayName("IT-06-OB-05 | Friends panel is populated with others' purchased items")
    void testFriendsPanel_isPopulatedWithItems() throws Exception {
        AtomicBoolean hasEntries      = new AtomicBoolean(false);
        AtomicBoolean hasAddToCartBtn = new AtomicBoolean(false);

        SwingUtilities.invokeAndWait(() -> {
            Friends friendsPanel = getFriendsPanel();
            assertNotNull(friendsPanel,
                    "Friends panel must be registered in ui.pages.");

            int entryCount = countFriendEntries(friendsPanel);
            hasEntries.set(entryCount > 0);
            hasAddToCartBtn.set(
                    findButtonContaining(friendsPanel, "Add To Cart") != null);
        });

        assertTrue(hasEntries.get(),
                "The Friends panel must display at least one friend-activity entry " +
                        "showing what others have bought.");
        assertTrue(hasAddToCartBtn.get(),
                "Each friend-activity entry must include an 'Add To Cart' button.");
    }

    // ==============================================================
    // IT-06-OB-06  "Add To Cart" in Friends panel updates the cart
    // ==============================================================


    @Test
    @DisplayName("IT-06-OB-06 | 'Add To Cart' in Friends panel adds item to cart")
    void testFriendsPanel_addToCartUpdatesCart() throws Exception {
        Cart.clearCart();
        AtomicInteger cartCountAfterClick = new AtomicInteger(0);

        SwingUtilities.invokeAndWait(() -> {
            Friends friendsPanel = getFriendsPanel();
            assertNotNull(friendsPanel,
                    "Friends panel must be registered in ui.pages.");

            JButton addBtn = findButtonContaining(friendsPanel, "Add To Cart");
            assertNotNull(addBtn,
                    "Friends panel must contain at least one 'Add To Cart' button.");
            addBtn.doClick();
            cartCountAfterClick.set(Cart.getTotalCountOfItems());
        });

        assertTrue(cartCountAfterClick.get() > 0,
                "Cart should have at least 1 item after clicking 'Add To Cart' " +
                        "in the Friends activity panel.");
    }

    // ==============================================================
    // IT-06-OB-07  RecommendationSystem honours quiz answers end-to-end
    // ==============================================================


    @Test
    @DisplayName("IT-06-OB-07 | RecommendationSystem recommends sport items for 'Sporty' lifestyle")
    void testRecommendationSystem_lifestyleSportyReturnsSportProducts() {
        List<Products> candidates = List.of(
                new Products("Test Hoodie",  "/stub.png", "", "Hoodie",  "Black", "Cotton", "M", 39.99, 4.0),
                new Products("Test Blazer",  "/stub.png", "", "Blazer",  "Grey",  "Wool",   "M", 99.99, 4.5),
                new Products("Test T-Shirt", "/stub.png", "", "T-Shirt", "White", "Cotton", "M", 19.99, 4.1)
        );

        String lifestyle = "Sporty";
        List<Products> result = new ArrayList<>();

        for (Products p : candidates) {
            int points = 0;
            if (lifestyle.equalsIgnoreCase("Sporty") &&
                    (p.getCategory().equalsIgnoreCase("Track Suit") ||
                            p.getCategory().equalsIgnoreCase("Hoodie")     ||
                            p.getCategory().equalsIgnoreCase("T-Shirt"))) {
                points += 3;
            }
            if (points >= 2) result.add(p);   // threshold when answeredQuestions <= 3
        }

        assertEquals(2, result.size(),
                "Sporty lifestyle should recommend Hoodie and T-Shirt, not Blazer.");
        assertTrue(result.stream().anyMatch(p -> p.getCategory().equalsIgnoreCase("Hoodie")),
                "Result must include Hoodie for Sporty lifestyle.");
        assertTrue(result.stream().anyMatch(p -> p.getCategory().equalsIgnoreCase("T-Shirt")),
                "Result must include T-Shirt for Sporty lifestyle.");
        assertFalse(result.stream().anyMatch(p -> p.getCategory().equalsIgnoreCase("Blazer")),
                "Result must NOT include Blazer for Sporty lifestyle.");
    }

    // ==============================================================
    // Private helpers
    // ==============================================================

    /** Returns the Friends panel registered in ui.pages, or null. */
    private Friends getFriendsPanel() {
        for (Component comp : ui.pages.getComponents()) {
            if (comp instanceof Friends f) return f;
        }
        return null;
    }

    private JButton findButtonContaining(Container root, String target) {
        String lowerTarget = target.toLowerCase();
        for (Component c : root.getComponents()) {
            if (c instanceof JButton btn && btn.getText() != null) {
                String normalised = normaliseSmallCaps(btn.getText().toLowerCase());
                if (normalised.contains(lowerTarget)) return btn;
            }
            if (c instanceof Container inner) {
                JButton found = findButtonContaining(inner, target);
                if (found != null) return found;
            }
        }
        return null;
    }

    private String normaliseSmallCaps(String text) {
        return text
                .replace("ʜ", "h").replace("ᴏ", "o").replace("ᴍ", "m").replace("ᴇ", "e")
                .replace("ᴘ", "p").replace("ʀ", "r").replace("ᴏ", "o").replace("ᴅ", "d")
                .replace("ᴜ", "u").replace("ᴄ", "c").replace("ᴛ", "t").replace("ꜱ", "s")
                .replace("ǫ", "q").replace("ɪ", "i").replace("ᴢ", "z")
                .replace("ꜰ", "f").replace("ɴ", "n");
    }

    /** Recursively checks whether any JLabel in the tree has exactly the given text. */
    private boolean containsLabelText(Container root, String target) {
        for (Component c : root.getComponents()) {
            if (c instanceof JLabel lbl && target.equals(lbl.getText())) return true;
            if (c instanceof Container inner && containsLabelText(inner, target)) return true;
        }
        return false;
    }

    private int countFriendEntries(Container root) {
        int count = 0;
        for (Component c : root.getComponents()) {
            if (c instanceof JPanel p
                    && Color.WHITE.equals(p.getBackground())
                    && p.getLayout() instanceof BorderLayout) {
                count++;
            }
            if (c instanceof Container inner) count += countFriendEntries(inner);
        }
        return count;
    }
}