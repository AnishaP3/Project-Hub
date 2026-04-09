import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Cart {
    private static final Map<String, Integer> cart = new HashMap<>();

    String name;
    double price;
    int quantity;

    Cart(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
    Cart(){}

    // Getters
    public String getName(){return name;}
    public double getPrice(){return price;}
    public int getPriceQuantity(){return quantity;}

    // Setters
    public void setName(String name){this.name = name;}
    public void setPrice(double price){this.price = price;}
    public void setQuantity(int quantity){this.quantity = quantity;}

    // Adds the product to the cart
    public static void addProduct(Products p) {
        if (cart.containsKey(p.getName())) {
            cart.put(p.getName(), cart.get(p.getName()) + 1);
        } else {
            cart.put(p.getName(), 1);
        }
    }

    // Removes the product from the cart
    public static void removeProduct(String product) {
        cart.remove(product);
    }

    // Returns the cart
    public static Map<String, Integer> readCart() {
        return cart;
    }

    // Returns total items in the cart
    public static int getTotalCountOfItems()   {
        int total = 0;
        for (int quantity : cart.values()) {
            total += quantity;
        }
        return total;
    }

    // Clears the cart
    public static void clearCart() {
        cart.clear();
    }

    // Clears the cart csv
    public static void clearCartCSV() {
        String filePath = "resources/Cart/cart.csv";
        try (java.io.PrintWriter writer = new java.io.PrintWriter(filePath)) {
            // write nothing — empties the file
        } catch (Exception e) {
            e.printStackTrace();
        }
        cart.clear();
    }

    // Adds the product to the Cart csv
    public static void addProductToCartCSV(Products p) {
        String filePath = "resources/Cart/cart.csv";
        Map<String, Cart> merged = new HashMap<>();

        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            for (String line : lines) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String name = parts[0];
                    double price = Double.parseDouble(parts[1]);
                    int quantity = Integer.parseInt(parts[2]);
                    merged.put(name, new Cart(name, price, quantity));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (merged.containsKey(p.getName())) {
            Cart existing = merged.get(p.getName());
            existing.quantity += 1;
        } else {
            merged.put(p.getName(), new Cart(p.getName(), p.getPrice(), 1));
        }

        try (PrintWriter writer = new PrintWriter(filePath)) {
            for (Cart item : merged.values()) {
                writer.println(item.name + "," + item.price + "," + item.quantity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Updates the cart csv with changes
    public static void updateCartCSV(Map<String, Integer> cartMap, List<Products> allProducts) {
        String filePath = filePath = "resources/Cart/cart.csv";
        try (PrintWriter writer = new PrintWriter(filePath)) {
            for (Map.Entry<String, Integer> entry : cartMap.entrySet()) {
                String productName = entry.getKey();
                int quantity = entry.getValue();

                double price = 0;
                for (Products p : allProducts) {
                    if (p.getName().equalsIgnoreCase(productName)) {
                        price = p.getPrice();
                        break;
                    }
                }

                writer.println(productName + "," + price + "," + quantity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Loads cart items from the csv
    public static void loadCartFromCSV() {
        String filePath = "resources/Cart/cart.csv";
        try (Scanner scanner = new Scanner(new File(filePath))) {
            cart.clear(); // clear existing items
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(",");
                if (parts.length == 3) {
                    String name = parts[0].trim();
                    int quantity = Integer.parseInt(parts[2].trim());
                    cart.put(name, quantity);  // THIS WORKS because cart is static
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}