import java.util.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

public class FreqBought {

    // Store accessories data
    private static List<Products> allAccessories = new ArrayList<>();
    private static Map<String, Products> accessoryMap = new HashMap<>();

    // Mapping between main products and their recommended accessories
    private static Map<String, String> productToAccessory = new HashMap<>();

    // Load accessories from CSV file
    static {
        loadAccessories();
        setupProductAccessoryMapping();
    }

    /**
     * Load accessories from accessories.csv
     */
    private static void loadAccessories() {
        try {
            // Read the accessories.csv file directly - FIXED PATH
            InputStream inputStream = FreqBought.class.getClassLoader().getResourceAsStream("Products/accessories.csv");
            if (inputStream == null) {
                System.out.println("accessories.csv not found at Products/accessories.csv");
                return;
            }

            try (Scanner scanner = new Scanner(inputStream)) {
                if (scanner.hasNextLine()) {
                    scanner.nextLine(); // skip header
                }

                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] parts = line.split("\\|");

                    if (parts.length == 9) {
                        String name = parts[0].trim();
                        double price = Double.parseDouble(parts[1].trim());
                        String imagePath = parts[2].trim();
                        String details = parts[3].trim();
                        String category = parts[4].trim();
                        String color = parts[5].trim();
                        String material = parts[6].trim();
                        String size = parts[7].trim();
                        double rating = Double.parseDouble(parts[8].trim());

                        Products accessory = new Products(name, imagePath, details, category, color, material, size, price, rating);
                        allAccessories.add(accessory);
                        accessoryMap.put(name, accessory);
                    }
                }
            }
            System.out.println(" Successfully Loaded " + allAccessories.size() + " accessories from Products/accessories.csv");
        } catch (Exception e) {
            System.out.println("Error loading accessories: " + e.getMessage());
            e.printStackTrace();
        }
    }
    /**
     * Set up mapping between main products and their recommended accessories
     */
    private static void setupProductAccessoryMapping() {
        productToAccessory.put("Casual Hoodie", "Beanie");
        productToAccessory.put("Graphic Tee", "Bangles");
        productToAccessory.put("Jacket", "Earmuffs");
        productToAccessory.put("Party Dress", "Necklace");
        productToAccessory.put("Creme Trench Coat", "Plain Sweater");
        productToAccessory.put("Office Blazer", "Purse");
        productToAccessory.put("Floral Dress", "Sandals");
        productToAccessory.put("Sweater", "Shoulder Bag");
        productToAccessory.put("Preppy Sweater", "White Shirt");
        productToAccessory.put("Track Suit", "Sneakers");
        productToAccessory.put("Denim Jacket", "Watch");
        productToAccessory.put("Checkered Tee", "Ripped Jeans");
    }

    /**
     * Get the recommended accessory for a specific product
     * @param productName The name of the main product
     * @return The accessory product, or null if not found
     */
    public static Products getAccessoryForProduct(String productName) {
        String accessoryName = productToAccessory.get(productName);
        if (accessoryName != null) {
            return accessoryMap.get(accessoryName);
        }
        return null;
    }

    /**
     * Create a panel showing the featured accessory for a product
     * @param mainProduct The main product being viewed
     * @return JPanel with the accessory display
     */
    public static JPanel createFeaturedAccessoryPanel(Products mainProduct, ClassLoader classLoader) {
        System.out.println("=== Looking for accessory for: " + mainProduct.getName());

        // TEMPORARY PLACEHOLDER - Full functionality coming in next iteration
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(245, 241, 232));
        panel.setBorder(new EmptyBorder(20, 40, 30, 40));

        // Title section
        JLabel titleLabel = new JLabel("COMPLETE THE LOOK");
        titleLabel.setFont(new Font("Georgia", Font.BOLD, 22));
        titleLabel.setForeground(new Color(212, 175, 55));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titleLabel);

        JLabel subtitleLabel = new JLabel("Frequently bought together");
        subtitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        subtitleLabel.setForeground(new Color(150, 150, 150));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(subtitleLabel);

        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Separator
        JSeparator sep = new JSeparator();
        sep.setMaximumSize(new Dimension(600, 1));
        sep.setForeground(new Color(220, 210, 200));
        sep.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(sep);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Placeholder message
        JLabel placeholderLabel = new JLabel("✨ Accessories coming in next iteration! ✨");
        placeholderLabel.setFont(new Font("SansSerif", Font.ITALIC, 14));
        placeholderLabel.setForeground(new Color(150, 150, 150));
        placeholderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(placeholderLabel);

        return panel;

    /* FULL CODE FOR NEXT ITERATION - COMMENTED OUT
    Products accessory = getAccessoryForProduct(mainProduct.getName());

    if (accessory == null) {
        System.out.println("No accessory found for: " + mainProduct.getName());
    } else {
        System.out.println("Found accessory: " + accessory.getName());
    }

    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setBackground(new Color(245, 241, 232));
    panel.setBorder(new EmptyBorder(20, 40, 30, 40));

    // Title section
    JLabel titleLabel = new JLabel("COMPLETE THE LOOK");
    titleLabel.setFont(new Font("Georgia", Font.BOLD, 22));
    titleLabel.setForeground(new Color(212, 175, 55));
    titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    panel.add(titleLabel);

    JLabel subtitleLabel = new JLabel("Frequently bought together");
    subtitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
    subtitleLabel.setForeground(new Color(150, 150, 150));
    subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    panel.add(subtitleLabel);

    panel.add(Box.createRigidArea(new Dimension(0, 20)));

    // Separator
    JSeparator sep = new JSeparator();
    sep.setMaximumSize(new Dimension(600, 1));
    sep.setForeground(new Color(220, 210, 200));
    sep.setAlignmentX(Component.CENTER_ALIGNMENT);
    panel.add(sep);
    panel.add(Box.createRigidArea(new Dimension(0, 20)));

    if (accessory == null) {
        JLabel noAccessory = new JLabel("No featured accessory available for this item.");
        noAccessory.setFont(new Font("SansSerif", Font.ITALIC, 14));
        noAccessory.setForeground(new Color(150, 150, 150));
        noAccessory.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(noAccessory);
    } else {
        // Create the accessory card
        JPanel accessoryCard = createAccessoryCard(accessory);
        JPanel centeredCard = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centeredCard.setBackground(new Color(245, 241, 232));
        centeredCard.add(accessoryCard);
        panel.add(centeredCard);
    }

    return panel;
    */
    }


    /**
     * Creates a card for the accessory with image, description, and add to cart button
     */
    private static JPanel createAccessoryCard(Products accessory) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(280, 380));
        card.setMaximumSize(new Dimension(280, 380));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(220, 220, 220), 1),
                new EmptyBorder(20, 20, 20, 20)
        ));

        // Product image - WITH DEBUGGING
        System.out.println("=== LOADING ACCESSORY IMAGE ===");
        System.out.println("Product: " + accessory.getName());
        System.out.println("Image Path from CSV: " + accessory.getImagePath());

        try {
            java.net.URL imageUrl = FreqBought.class.getResource(accessory.getImagePath());
            System.out.println("Full Image URL: " + imageUrl);

            if (imageUrl != null) {
                ImageIcon icon = new ImageIcon(imageUrl);
                Image scaledImage = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
                imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                card.add(imageLabel);
                System.out.println("✓ Image loaded successfully");
            } else {
                System.out.println("✗ Image URL is NULL - File not found!");
                JLabel noImage = new JLabel("📦");
                noImage.setFont(new Font("SansSerif", Font.PLAIN, 60));
                noImage.setAlignmentX(Component.CENTER_ALIGNMENT);
                card.add(noImage);
            }
        } catch (Exception e) {
            System.out.println("✗ Error loading image: " + e.getMessage());
            e.printStackTrace();
            JLabel noImage = new JLabel("📦");
            noImage.setFont(new Font("SansSerif", Font.PLAIN, 60));
            noImage.setAlignmentX(Component.CENTER_ALIGNMENT);
            card.add(noImage);
        }
        System.out.println("=============================");
        card.add(Box.createRigidArea(new Dimension(0, 15)));

        // Product name
        JLabel nameLabel = new JLabel(accessory.getName());
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(nameLabel);

        // Product category
        JLabel categoryLabel = new JLabel(accessory.getCategory());
        categoryLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        categoryLabel.setForeground(new Color(150, 150, 150));
        categoryLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(categoryLabel);

        card.add(Box.createRigidArea(new Dimension(0, 8)));

        // Product price
        JLabel priceLabel = new JLabel("$" + accessory.getPrice());
        priceLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        priceLabel.setForeground(new Color(138, 28, 28));
        priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(priceLabel);

        card.add(Box.createRigidArea(new Dimension(0, 15)));

        // Add to cart button
        JButton addButton = new JButton("ADD TO CART");
        addButton.setMaximumSize(new Dimension(200, 45));
        addButton.setPreferredSize(new Dimension(180, 40));
        addButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        addButton.setBackground(new Color(212, 175, 55));
        addButton.setForeground(Color.BLACK);
        addButton.setFocusPainted(false);
        addButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));


        card.add(addButton);

        return card;
    }

    /**
     * Get all accessories (for testing)
     */
    public static List<Products> getAllAccessories() {
        return allAccessories;
    }

    /**
     * Simple cart storage for now
     */
    private static Map<String, Integer> tempCart = new HashMap<>();
    private static Map<String, Products> productCache = new HashMap<>();

    public static void addToCart(Products product, int quantity) {
        String productName = product.getName();
        productCache.put(productName, product);

        if (tempCart.containsKey(productName)) {
            tempCart.put(productName, tempCart.get(productName) + quantity);
        } else {
            tempCart.put(productName, quantity);
        }

        System.out.println("Added " + quantity + " x " + productName + " to cart");
    }

    public static int getTotalItemCount() {
        int total = 0;
        for (int qty : tempCart.values()) {
            total += qty;
        }
        return total;
    }

    public static double getTotalPrice() {
        double total = 0;
        for (Map.Entry<String, Integer> entry : tempCart.entrySet()) {
            Products p = productCache.get(entry.getKey());
            if (p != null) {
                total += p.getPrice() * entry.getValue();
            }
        }
        return total;
    }

    public static Map<String, Integer> getCart() {
        return tempCart;
    }

    public static void clearCart() {
        tempCart.clear();
        productCache.clear();
    }
}