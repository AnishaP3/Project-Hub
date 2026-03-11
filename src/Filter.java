import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Filter {
    List<Products> products = new ArrayList<>();

    // Constructor
    public Filter(List<Products> products) {
        this.products = products;
    }

    public List<Products> applyFilters(Set<String> colors,
                                       Set<String> sizes,
                                       Set<String> categories,
                                       Set<String> materials,
                                       double minRating) {
        List<Products> result = new ArrayList<>(products);

        if (!colors.isEmpty()) {
            result.removeIf(p -> {
                for (String c : p.getColor().split(","))
                    if (colors.contains(c.trim())) return false;
                return true;
            });
        }
        if (!sizes.isEmpty()) {
            result.removeIf(p -> {
                for (String s : p.getSize().split(","))
                    if (sizes.contains(s.trim())) return false;
                return true;
            });
        }
        if (!categories.isEmpty()) {
            result.removeIf(p -> !categories.contains(p.getCategory().trim()));
        }
        if (!materials.isEmpty()) {
            result.removeIf(p -> {
                for (String m : p.getMaterial().split(","))
                    if (materials.contains(m.trim())) return false;
                return true;
            });
        }
        if (minRating > 0.0) {
            result.removeIf(p -> p.getRating() < minRating);
        }

        return result;
    }
}