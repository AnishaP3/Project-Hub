import java.util.ArrayList;
import java.util.List;

public class Filter {
    List<Products> products = new ArrayList<>();

    // Constructor
    public Filter(List<Products> products) {
        this.products = products;
    }

    // Filters products by category
    public List<Products> filterByCategory(String category) {
        List<Products> filtered = new ArrayList<>();
        for (Products p : products) {
            if (p.getCategory().equalsIgnoreCase(category)) {
                filtered.add(p);
            }
        }
        return filtered;
    }

    // Filters products by product
    public List<Products> filterByColor(String color) {
        List<Products> filtered = new ArrayList<>();
        for (Products p : products) {
            String[] colors = p.getColor().split(",");
            for (String c : colors) {
                if (c.trim().equalsIgnoreCase(color)) {
                    filtered.add(p);
                    break;
                }
            }
        }
        return filtered;
    }

    // Filters Products by Material
    public List<Products> filterByMaterial(String material) {
        List<Products> filtered = new ArrayList<>();
        for (Products p : products) {
            String[] materials = p.getMaterial().split(",");
            for (String m : materials) {
                if (m.trim().equalsIgnoreCase(material)) {
                    filtered.add(p);
                    break;
                }
            }
        }
        return filtered;
    }

    // Filters Products by Size
    public List<Products> filterBySize(String size) {
        List<Products> filtered = new ArrayList<>();
        for (Products p : products) {
            String[] sizes = p.getSize().split(",");
            for (String s : sizes) {
                if (s.trim().equalsIgnoreCase(size)) {
                    filtered.add(p);
                    break;
                }
            }
        }
        return filtered;
    }

    // Filters Products by Rating
    public List<Products> filterByRating(double rating) {
        List<Products> filtered = new ArrayList<>();
        for (Products p : products) {
            if (p.getRating() >= rating) {
                filtered.add(p);
            }
        }
        return filtered;
    }
}
