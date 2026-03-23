import java.util.List;

public class FreqBought {

    /**
     * Adds a product to the cart with specified quantity
     * @param product The product to add
     * @param quantity The quantity to add (default should be 1 from UI)
     */
    public static void addToCart(Products product, int quantity) {
        ShoppingCart.addProduct(product, quantity);
    }

    /**
     * Removes one instance of a product from cart
     * @param productName The name of the product to remove one of
     */
    public static void removeOneFromCart(String productName) {
        ShoppingCart.removeOneProduct(productName);
    }

    /**
     * Removes all instances of a product from cart
     * @param productName The name of the product to remove completely
     */
    public static void removeAllFromCart(String productName) {
        ShoppingCart.removeAllProducts(productName);
    }

    /**
     * Gets the total number of items in cart
     * @return total item count
     */
    public static int getTotalItemCount() {
        return ShoppingCart.getTotalItemCount();
    }

    /**
     * Calculates the total price of all items in cart
     * @return total price
     */
    public static double getTotalPrice() {
        return ShoppingCart.getTotalPrice();
    }

    /**
     * Gets all cart items with their details
     * @return List of CartItem objects
     */
    public static List<ShoppingCart.CartItem> getCartItems() {
        return ShoppingCart.getCartItems();
    }

    /**
     * Clears the entire cart
     */
    public static void clearCart() {
        ShoppingCart.clearCart();
    }

    /**
     * Checks if cart is empty
     * @return true if empty
     */
    public static boolean isCartEmpty() {
        return ShoppingCart.isEmpty();
    }

    /**
     * Gets a formatted summary for debugging
     * @return Cart summary string
     */
    public static String getCartSummary() {
        return ShoppingCart.getCartSummary();
    }
}