import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.InputStream;
import java.util.*;
import java.util.List;

public class CheckoutPage extends JPanel {

    private int total = 0;
    public static List<Cart> cart = new ArrayList<>();
    public List<Products> allProducts;

    private JPanel contentPanel;
    private UI ui;

    CheckoutPage(UI ui) {
        this.ui = ui;
        checkOutUI();

    }

    public void checkOutUI() {

        setLayout(new BorderLayout());
        setBackground(new Color(230, 230, 230));

        getAllProducts();

        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BorderLayout());
        wrapper.setBackground(new Color(245, 238, 210));
        wrapper.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        JPanel cartPanel = new JPanel();
        cartPanel.setLayout(new BoxLayout(cartPanel, BoxLayout.Y_AXIS));
        for (Cart item : cart) {
            JPanel itemPanel = itemsInCart(item);
            cartPanel.add(itemPanel);
            cartPanel.add(Box.createVerticalStrut(15)); // spacing
        }
        wrapper.add(cartPanel, BorderLayout.CENTER);

        JScrollPane scrollPane = new JScrollPane(wrapper);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(15);
        scrollPane.setBorder(null);
        scrollPane.setPreferredSize(new Dimension(700, 500));
        scrollPane.setVisible(true);

        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        totalPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        totalPanel.setBackground(new Color(218, 190, 90));
        JLabel totalLabel = new JLabel("Total: $");
        totalLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        totalPanel.add(totalLabel);
        wrapper.add(totalPanel, BorderLayout.SOUTH);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.WHITE);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        //TODO: RECOMMENDATION PANEL
        JPanel recommendations = new JPanel();
        recommendations.setBackground(Color.PINK);
        recommendations.setBorder(new EmptyBorder(300,300,300,300));
        centerPanel.add(recommendations, BorderLayout.EAST);

        revalidate();
        repaint();
    }

    public JPanel itemsInCart(Cart item) {
        JPanel mainProductPanel = new JPanel(new BorderLayout());
        mainProductPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY,1));
        mainProductPanel.setBackground(Color.WHITE);
        mainProductPanel.setMaximumSize(new Dimension(1000, 400));

        //getting the matching photo to product
        Products currentProduct = null;
        for (Products p : allProducts) {
            if (p.getName().equals(item.name)) {
                currentProduct = p;
                break;
            }
        }

        //IMAGE:
        int imageSize = 200;
        ImageIcon imageIcon = new ImageIcon(getClass().getResource(currentProduct.getImagePath()));
        Image originalImage = imageIcon.getImage();

        int originalWidth = originalImage.getWidth(null);
        int originalHeight = originalImage.getHeight(null);

        double scaleFactor = Math.min((double) imageSize / originalWidth, (double) imageSize / originalHeight);
        int newWidth = (int) (originalWidth * scaleFactor);
        int newHeight = (int) (originalHeight * scaleFactor);
        Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));

        JPanel imageContainer = new JPanel(new BorderLayout());
        imageContainer.setPreferredSize(new Dimension(200, 200));
        imageContainer.setBackground(Color.WHITE);
        imageContainer.setBorder(new EmptyBorder(10,10,10,10));

        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setVerticalAlignment(JLabel.CENTER);
        imageContainer.add(imageLabel, BorderLayout.CENTER);
        mainProductPanel.add(imageContainer, BorderLayout.WEST);

        //INFORMATION:
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(new EmptyBorder(10,10,10,10));
        JLabel nameLabel = new JLabel(item.name);
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 35));
        JLabel priceLabel = new JLabel(String.format("$%.2f", item.price));
        priceLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));

        JLabel quantityLabel = new JLabel("Quantity: " + item.quantity);
        quantityLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));

        JButton addQuantityButton = new JButton("+");
        JButton subtractQuantityButton = new JButton("-");
        addQuantityButton.setFont(new Font("SansSerif", Font.PLAIN, 20));
        addQuantityButton.setForeground(Color.WHITE);
        subtractQuantityButton.setForeground(Color.WHITE);

        addQuantityButton.setBackground(new Color(20, 18, 14));
        subtractQuantityButton.setFont(new Font("SansSerif", Font.PLAIN, 20));
        subtractQuantityButton.setBackground(new Color(20, 18, 14));

        addQuantityButton.addActionListener(e -> {
            item.quantity += 1;
            quantityLabel.setText("Quantity: " + item.quantity);
        });
        subtractQuantityButton.addActionListener(e -> {
            item.quantity -= 1;
            quantityLabel.setText("Quantity: " + item.quantity);
        });

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setMaximumSize(new Dimension(100,100));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(addQuantityButton);
        buttonPanel.add(subtractQuantityButton);

        infoPanel.add(nameLabel);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(priceLabel);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(quantityLabel);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(buttonPanel);

        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        priceLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        quantityLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        mainProductPanel.add(infoPanel, BorderLayout.CENTER);

        return mainProductPanel;
    }

    public void readCartCSV(ClassLoader classLoader) {
        cart.clear();
        Map<String, Cart> mergedCart = new LinkedHashMap<>();

        try (InputStream inputStream = classLoader.getResourceAsStream("Cart/cart.csv")) {
            if (inputStream == null) {
                throw new RuntimeException("CSV file not found in resources folder.");
            }
            try (Scanner scanner = new Scanner(inputStream)) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine().trim();
                    if (line.isEmpty()) continue; // skip empty lines
                    String[] parts = line.split(",");
                    if (parts.length == 3) {
                        String name = parts[0].trim();
                        double price = Double.parseDouble(parts[1].trim());
                        int quantity = Integer.parseInt(parts[2].trim());

                        if (mergedCart.containsKey(name)) {

                            Cart existing = mergedCart.get(name);
                            existing.quantity += quantity;
                        } else {
                            mergedCart.put(name, new Cart(name, price, quantity));
                        }

                    } else {
                        System.out.println("Skipping invalid line: " + line);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error reading cart CSV", e);
        }
        cart.addAll(mergedCart.values());
    }

    public void getAllProducts() {
        Products productClass = new Products();
        allProducts = productClass.readProductCSV(getClass().getClassLoader());
        readCartCSV(getClass().getClassLoader());
    }

}