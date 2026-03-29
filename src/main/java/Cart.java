import java.util.HashMap;
import java.util.Map;
import java.io.FileWriter;
import java.io.IOException;

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

    public String getName(){
        return name;
    }
    public double getPrice(){
        return price;
    }
    public int getQuantity(){
        return quantity;
    }

    public void setName(String name){
        this.name = name;
    }
    public void setPrice(double price){
        this.price = price;
    }
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

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

    public static void writeToCSV(Products p) {
        String filePath = "resources/Cart/cart.csv";
        try (FileWriter fw = new FileWriter(filePath, true)) {
            fw.write(p.getName() + "," +
                    p.getPrice() + "," +
                    cart.getOrDefault(p.getName(), 1) + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
