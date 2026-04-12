import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit Tests for RecommendationSystem.recommendProducts()
 *
 * UT-05-TB: Multiple quiz answers → only products matching answered questions are returned
 * UT-06-TB: Budget = "20-50" → only products within that price range are recommended
 */
public class RecommendationSystemTest {

    private RecommendationSystem rs;

    // Path must match what RecommendationSystem.readQuizResultsCSV() reads from
    private static final String QUIZ_CSV_PATH = "resources/Quiz/quizResults.csv";

    @BeforeEach
    public void setUp() throws IOException {
        rs = new RecommendationSystem();

        // Make sure the directory exists before each test writes to it
        File dir = new File("resources/Quiz");
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    private void writeQuizCSV(String lifestyle, String colour, String tone,
                              String budget, String style, String fabric) throws IOException {
        try (FileWriter fw = new FileWriter(QUIZ_CSV_PATH, false)) {
            // Write a dummy header line so the scanner always has a "previous" line
            fw.write("lifestyle,colour,tone,budget,style,fabric\n");
            fw.write(String.join(",", lifestyle, colour, tone, budget, style, fabric) + "\n");
        }
    }

    // -----------------------------------------------------------------------
    // UT-05-TB
    // Multiple answers from the quiz → recommendations based on answered questions
    // -----------------------------------------------------------------------

    @Test
    public void testUT05_multipleAnswers_returnsRelevantRecommendations() throws IOException {
        // Arrange
        writeQuizCSV("Sporty", "Black", "Bright", "20-50", "Streetwear", "Cotton");

        // Act
        List<Products> results = rs.recommendProducts(getClass().getClassLoader());

        // Assert 1 – system must return at least one recommendation
        assertFalse(results.isEmpty(),
                "Expected at least one recommendation when all six questions are answered");

        // Assert 2 – no duplicate entries in the result list
        long distinctCount = results.stream().distinct().count();
        assertEquals(distinctCount, results.size(),
                "Recommendation list should not contain duplicates");

        // Assert 3 – every item in the list is a valid Product with a name
        for (Products p : results) {
            assertNotNull(p.getName(),
                    "Every recommended product must have a non-null name");
        }
    }

    @Test
    public void testUT05_partialAnswers_returnsBroaderButNonEmptyList() throws IOException {
        // Arrange: only lifestyle and style answered; the rest are blank
        writeQuizCSV("Sporty", "", "", "", "Streetwear", "");

        // Act
        List<Products> results = rs.recommendProducts(getClass().getClassLoader());

        // Assert – method must not crash and must return valid Product objects
        assertNotNull(results, "Result list must not be null");
        for (Products p : results) {
            assertNotNull(p.getName(), "Every recommended product must have a name");
        }
    }

    // -----------------------------------------------------------------------
    // UT-06-TB
    // Budget = "20-50" → only products within $20–$50 are recommended
    // -----------------------------------------------------------------------

    @Test
    public void testUT06_budgetFilter_onlyReturnsProductsInRange() throws IOException {
        // Arrange – budget only; all other answers blank so only budget points are scored
        writeQuizCSV("", "", "", "20-50", "", "");

        // Act
        List<Products> results = rs.recommendProducts(getClass().getClassLoader());

        // Assert – every returned product must be within the $20–$50 range
        double[] range = rs.budgetRange("20-50");
        assertFalse(results.isEmpty(),
                "Expected at least one product priced between $20 and $50 in the catalogue");

        for (Products p : results) {
            assertTrue(p.getPrice() >= range[0] && p.getPrice() <= range[1],
                    "Product '" + p.getName() + "' has price $" + p.getPrice() +
                            " which is outside the expected range [$" + range[0] + ", $" + range[1] + "]");
        }
    }

    @Test
    public void testUT06_budgetRange_correctBoundsForTwentyToFifty() {
        double[] range = rs.budgetRange("20-50");

        assertEquals(20.0, range[0], 0.001,
                "Lower bound of '20-50' budget should be 20.0");
        assertEquals(50.0, range[1], 0.001,
                "Upper bound of '20-50' budget should be 50.0");
    }

    @Test
    public void testUT06_budgetRange_isInclusive() {
        double[] range = rs.budgetRange("20-50");

        // Exactly at lower bound – must be inside
        assertTrue(20.0 >= range[0] && 20.0 <= range[1],
                "$20.00 should be within the '20-50' range (inclusive lower bound)");

        // Exactly at upper bound – must be inside
        assertTrue(50.0 >= range[0] && 50.0 <= range[1],
                "$50.00 should be within the '20-50' range (inclusive upper bound)");

        // Just below lower bound – must be outside
        assertFalse(19.99 >= range[0] && 19.99 <= range[1],
                "$19.99 should NOT be within the '20-50' range");

        // Just above upper bound – must be outside
        assertFalse(50.01 >= range[0] && 50.01 <= range[1],
                "$50.01 should NOT be within the '20-50' range");
    }
}