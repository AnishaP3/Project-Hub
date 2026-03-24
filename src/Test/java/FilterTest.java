import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class FilterTest {

    // Test products
    Products productA;
    Products productB;
    Products productC;
    Filter filter;

    // Creating the products to test
    @BeforeEach
    public void setup() {
        productA = new Products(
                "Black Hoodie",
                "img/hoodie.png",
                "Warm hoodie",
                "Hoodie",
                "Black",
                "Cotton",
                "S,M",
                50.0,
                4.5
        );

        productB = new Products(
                "Red Dress",
                "img/dress.png",
                "Elegant dress",
                "Dress",
                "Red",
                "Polyester",
                "L",
                80.0,
                3.0
        );

        productC = new Products(
                "White T-Shirt",
                "img/shirt.png",
                "Casual tee",
                "T-Shirt",
                "Black, White",
                "Cotton",
                "M",
                20.0,
                5.0
        );

        List<Products> mockList = List.of(productA, productB, productC);
        filter = new Filter(mockList);
    }

    // Testing the colour filter with the colour black
    @Test
    public void testColorFilter() {
        // Checking with colour black
        Set<String> colors = Set.of("black");
        Set<String> empty = Set.of();

        // Checking with the filters applied
        List<Products> result = filter.applyFilters(
                colors,
                empty,
                empty,
                empty,
                0
        );

        // Assert
        assertTrue(result.contains(productA));
        assertTrue(result.contains(productC));
        assertFalse(result.contains(productB));
    }

    // Testing the size
    @Test
    public void testSizeFilter() {
        // Checking with size medium
        Set<String> sizes = Set.of("M");
        Set<String> empty = Set.of();

        // Checking with the filters applied
        List<Products> result = filter.applyFilters(
                empty,
                sizes,
                empty,
                empty,
                0
        );

        // Assert
        assertTrue(result.contains(productA)); // S,M
        assertTrue(result.contains(productC)); // M
        assertFalse(result.contains(productB)); // L
    }

    // Testing the category filter
    @Test
    public void testCategoryFilter() {
        // checking category hoodie
        Set<String> categories = Set.of("Hoodie");
        Set<String> empty = Set.of();

        // Checking with the filters applied
        List<Products> result = filter.applyFilters(
                empty,
                empty,
                categories,
                empty,
                0
        );

        //Assert
        assertTrue(result.contains(productA));
        assertFalse(result.contains(productB));
        assertFalse(result.contains(productC));
    }

    // Testing material filter
    @Test
    public void testMaterialFilter() {
        // Checking cotton material
        Set<String> materials = Set.of("Cotton");
        Set<String> empty = Set.of();

        // Checking with the filters applied
        List<Products> result = filter.applyFilters(
                empty,
                empty,
                empty,
                materials,
                0
        );

        // Assert
        assertTrue(result.contains(productA));
        assertTrue(result.contains(productC));
        assertFalse(result.contains(productB));
    }

    // Testing the rating filter
    @Test
    public void testRatingFilter() {
        Set<String> empty = Set.of();

        // Checking with the filters applied (rating of 4.0)
        List<Products> result = filter.applyFilters(
                empty,
                empty,
                empty,
                empty,
                4.0
        );

        // Assert
        assertTrue(result.contains(productA)); // 4.5
        assertTrue(result.contains(productC)); // 5.0
        assertFalse(result.contains(productB)); // 3.0
    }

    // Testing multiple filters at once
    @Test
    public void testMultipleFilters() {
        // Checking with colour black and material cotton, and rating of 4.0
        Set<String> colors = Set.of("Black");
        Set<String> materials = Set.of("Cotton");
        Set<String> empty = Set.of();

        // Checking with the filters applied
        List<Products> result = filter.applyFilters(
                colors,
                empty,
                empty,
                materials,
                4.0
        );

        // Assert
        assertTrue(result.contains(productA));
        assertTrue(result.contains(productC));
        assertFalse(result.contains(productB));
    }

    // Testing no filters
    @Test
    public void testNoFilters() {
        Set<String> empty = Set.of();

        // Checking with no filters applied
        List<Products> result = filter.applyFilters(
                empty,
                empty,
                empty,
                empty,
                0
        );

        // Assert
        assertEquals(3, result.size());
    }

    // Testing when there are no matches
    @Test
    public void testNoMatches() {
        // checking with colour purple (isn't an option)
        Set<String> colors = Set.of("Purple");
        Set<String> empty = Set.of();

        // Checking with the filters applied
        List<Products> result = filter.applyFilters(
                colors,
                empty,
                empty,
                empty,
                0
        );

        // Assert
        assertTrue(result.isEmpty());
    }

    // Testing when there are multiple colours
    @Test
    public void testMultiValueColor() {
        // checking with clothes that have multiple colours
        Set<String> colors = Set.of("White");
        Set<String> empty = Set.of();

        // Checking with the filters applied
        List<Products> result = filter.applyFilters(
                colors,
                empty,
                empty,
                empty,
                0
        );

        // Assert
        assertTrue(result.contains(productC)); // "Black, White"
        assertFalse(result.contains(productA));
        assertFalse(result.contains(productB));
    }
}