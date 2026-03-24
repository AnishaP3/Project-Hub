import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Filter {
    List<Products> products = new ArrayList<>();

    // Constructor
    public Filter(List<Products> products) {
        this.products = products;
    }

    // Applies all active filters together
    public List<Products> applyFilters(Set<String> colors,
                                       Set<String> sizes,
                                       Set<String> categories,
                                       Set<String> materials,
                                       double minRating) {
        List<Products> result = new ArrayList<>(products);

        // COLOR FILTER
        /* Products may have multiple colours so keep the
         * product if any of the colours match the active filter
         */

        if (!colors.isEmpty()) {
            Set<String> lowerColors = new HashSet<>();
            for (String col : colors) {
                lowerColors.add(col.toLowerCase());
            }
            result.removeIf(p -> {
                for (String c : p.getColor().split(",")) {
                    if (lowerColors.contains(c.trim().toLowerCase())) {
                        return false; // keep product
                    }
                }
                return true; // remove product
            });
        }


        // SIZE FILTER
        /* Products may have multiple sizes so keep the
         * product if any of the sizes match the active filter
         */
        if (!sizes.isEmpty()) {
            result.removeIf(p -> {
                for (String s : p.getSize().split(",")) {
                    if (sizes.contains(s.trim())) {
                        return false;
                    }
                }
                return true;
            });
        }


        // CATEGORY FILTER
        // Filter by selected category/categories
        if (!categories.isEmpty()) {
            result.removeIf(p -> !categories.contains(p.getCategory().trim()));
        }


        // MATERIAL FILTER
        /* Products may have multiple materials so keep the
         * product if any of the materials match the active filter
         */
        if (!materials.isEmpty()) {
            result.removeIf(p -> {
                for (String m : p.getMaterial().split(",")) {
                    if (materials.contains(m.trim())) {
                        return false;
                    }
                }
                return true;
            });
        }

        // RATING FILTER
        // Keep products greater than or equal to the rating selected
        if (minRating > 0.0) {
            result.removeIf(p -> p.getRating() < minRating);
        }

        return result;
    }
}
