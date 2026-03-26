import java.util.HashMap;
import java.util.Map;

public class Cart {
    private static final Map<String, Integer> cart = new HashMap<>();

    public static void addProduct(Products p) {
        if (cart.containsKey(p.getName())) {
            cart.put(p.getName(), cart.get(p.getName()) + 1);
        } else {
            cart.put(p.getName(), 1);
        }
    }
    public static void removeProduct(String product) {
        cart.remove(product);
    }
    public static Map<String, Integer> readCart() {
        return cart;
    }

    public static int getTotalCountOfItems() {
        int total = 0;
        for (int quantity : cart.values()) {
            total += quantity;
        }
        return total;
    }
    public static void clearCart() {
        cart.clear();
    }



}
