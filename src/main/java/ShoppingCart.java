//import java.util.HashMap;
//import java.util.Map;
//import java.util.ArrayList;
//import java.util.List;
//
/// **
// * ShoppingCart - Manages the shopping cart functionality independently from Cart.java
// * This class handles all cart operations including adding, removing, and calculating totals
// */
//public class ShoppingCart {
//    // Store product name and quantity
//    private static Map<String, Integer> cart = new HashMap<>();
//    // Store product objects for quick reference (cached from CSV data)
//    private static Map<String, Products> productCache = new HashMap<>();
//
//    /**
//     * Adds a product to the cart
//     * @param product The product to add
//     * @param quantity The quantity to add (default 1)
//     */
//    public static void addProduct(Products product, int quantity) {
//        String productName = product.getName();
//
//        // Cache the product object for later use
//        productCache.put(productName, product);
//
//        if (cart.containsKey(productName)) {
//            // Product already in cart, increase quantity
//            int currentQty = cart.get(productName);
//            cart.put(productName, currentQty + quantity);
//            System.out.println("Added " + quantity + " x " + productName + " to cart. New quantity: " + (currentQty + quantity));
//        } else {
//            // New product, add to cart
//            cart.put(productName, quantity);
//            System.out.println("Added " + quantity + " x " + productName + " to cart");
//        }
//    }
//
//    /**
//     * Removes one instance of a product from cart
//     * @param productName The name of the product to remove
//     */
//    public static void removeOneProduct(String productName) {
//        if (cart.containsKey(productName)) {
//            int currentQty = cart.get(productName);
//            if (currentQty > 1) {
//                cart.put(productName, currentQty - 1);
//                System.out.println("Removed 1 x " + productName + " from cart. Remaining: " + (currentQty - 1));
//            } else {
//                cart.remove(productName);
//                productCache.remove(productName);
//                System.out.println("Removed " + productName + " from cart completely");
//            }
//        }
//    }
//
//    /**
//     * Removes all instances of a product from cart
//     * @param productName The name of the product to remove completely
//     */
//    public static void removeAllProducts(String productName) {
//        if (cart.containsKey(productName)) {
//            cart.remove(productName);
//            productCache.remove(productName);
//            System.out.println("Removed all " + productName + " from cart");
//        }
//    }
//
//    /**
//     * Gets the total number of items in cart (sum of all quantities)
//     * @return total item count
//     */
//    public static int getTotalItemCount() {
//        int total = 0;
//        for (int quantity : cart.values()) {
//            total += quantity;
//        }
//        return total;
//    }
//
//    /**
//     * Calculates the total price of all items in cart
//     * Uses cached product objects to get prices
//     * @return total price
//     */
//    public static double getTotalPrice() {
//        double total = 0.0;
//
//        for (Map.Entry<String, Integer> entry : cart.entrySet()) {
//            String productName = entry.getKey();
//            int quantity = entry.getValue();
//            Products product = productCache.get(productName);
//
//            if (product != null) {
//                total += product.getPrice() * quantity;
//            } else {
//                System.err.println("Warning: Product " + productName + " not found in cache");
//            }
//        }
//
//        return total;
//    }
//
//    /**
//     * Gets all cart items with their details
//     * @return List of CartItem objects containing product and quantity
//     */
//    public static List<CartItem> getCartItems() {
//        List<CartItem> items = new ArrayList<>();
//
//        for (Map.Entry<String, Integer> entry : cart.entrySet()) {
//            String productName = entry.getKey();
//            int quantity = entry.getValue();
//            Products product = productCache.get(productName);
//
//            if (product != null) {
//                items.add(new CartItem(product, quantity));
//            }
//        }
//
//        return items;
//    }
//
//    /**
//     * Gets a specific product from cache
//     * @param productName The name of the product
//     * @return The Products object or null if not found
//     */
//    public static Products getProductFromCache(String productName) {
//        return productCache.get(productName);
//    }
//
//    /**
//     * Clears the entire cart
//     */
//    public static void clearCart() {
//        cart.clear();
//        productCache.clear();
//        System.out.println("Cart cleared");
//    }
//
//    /**
//     * Checks if cart is empty
//     * @return true if cart is empty
//     */
//    public static boolean isEmpty() {
//        return cart.isEmpty();
//    }
//
//    /**
//     * Gets a formatted summary of cart contents
//     * @return String with cart summary
//     */
//    public static String getCartSummary() {
//        if (cart.isEmpty()) {
//            return "Your cart is empty";
//        }
//
//        StringBuilder summary = new StringBuilder();
//        summary.append("=== SHOPPING CART ===\n");
//
//        for (Map.Entry<String, Integer> entry : cart.entrySet()) {
//            String productName = entry.getKey();
//            int quantity = entry.getValue();
//            Products product = productCache.get(productName);
//
//            if (product != null) {
//                double subtotal = product.getPrice() * quantity;
//                summary.append(String.format("%s x%d - $%.2f each = $%.2f\n",
//                        productName, quantity, product.getPrice(), subtotal));
//            }
//        }
//
//        summary.append(String.format("\nTotal items: %d\n", getTotalItemCount()));
//        summary.append(String.format("Total price: $%.2f\n", getTotalPrice()));
//        summary.append("=====================");
//
//        return summary.toString();
//    }
//
//    /**
//     * CartItem - Helper class to hold cart item data
//     */
//    public static class CartItem {
//        private Products product;
//        private int quantity;
//
//        public CartItem(Products product, int quantity) {
//            this.product = product;
//            this.quantity = quantity;
//        }
//
//        public Products getProduct() {
//            return product;
//        }
//
//        public int getQuantity() {
//            return quantity;
//        }
//
//        public double getSubtotal() {
//            return product.getPrice() * quantity;
//        }
//
//        public void increaseQuantity() {
//            quantity++;
//        }
//
//        public void decreaseQuantity() {
//            if (quantity > 0) {
//                quantity--;
//            }
//        }
//    }
//}