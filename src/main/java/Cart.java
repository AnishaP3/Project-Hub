import java.util.HashMap;
import java.util.Map;

public class Cart {
    private static Map<String, Integer> cart = new HashMap<>();

    public static void addProduct(Products p) {

    }

    public static void removeProduct(String product) {

    }

    public static Map<String, Integer> readCart() {
        return cart;
    }

    public static int getTotalCount() {
        return 0;
    }

    public static void clearCart() {
        cart.clear();
    }
}