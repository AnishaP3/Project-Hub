import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.InputStream;
import java.util.*;
import java.util.List;

public class CheckoutPage extends JPanel {

    private int total = 0;
    public static List<CartItem> cart = new ArrayList<>();
    public List<Products> products;

    private JPanel contentPanel;
    private UI ui;

    CheckoutPage(UI ui) {
        this.ui = ui;
        checkOutUI();

    }

    public static class CartItem {
        String name;
        double price;
        int quantity;

        CartItem(String name, double price, int quantity) {
            this.name = name;
            this.price = price;
            this.quantity = quantity;
        }
    }

    public void checkOutUI() {

        setLayout(new BorderLayout());
        setBackground(new Color(230, 230, 230));

        getAllProducts();
        readCartCSV(getClass().getClassLoader());

        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setBackground(new Color(245, 238, 210));
        wrapper.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);

        for (CartItem item : cart) {
            JPanel itemPanel = itemsInCart(item);
            contentPanel.add(itemPanel);
            contentPanel.add(Box.createVerticalStrut(15)); // spacing
        }

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(15);
        scrollPane.setBorder(null);
        scrollPane.setPreferredSize(new Dimension(700, 500)); // medium card size
        wrapper.add(scrollPane);

        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        totalPanel.setBackground(new Color(218, 190, 90));
        totalPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        JLabel totalLabel = new JLabel("Total: $");
        totalLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        totalPanel.add(totalLabel);
        wrapper.add(totalPanel);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(230, 230, 230));
        centerPanel.add(wrapper);
        add(centerPanel, BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    public JPanel itemsInCart(CartItem item) {
        JPanel mainProductPanel = new JPanel(new BorderLayout());
        mainProductPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY,1));
        mainProductPanel.setBackground(Color.WHITE);
        mainProductPanel.setMaximumSize(new Dimension(600, 150));

        Products currentProduct = null;
        for (Products p : products) {
            if (p.getName().equals(item.name)) {
                currentProduct = p;
                break;
            }
        }

        int imageSize = 200;
        ImageIcon imageIcon = new ImageIcon(getClass().getResource(currentProduct.getImagePath()));
        Image scaledImage = imageIcon.getImage().getScaledInstance(imageSize, imageSize, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
        imageLabel.setBorder(new EmptyBorder(10,10,10,10));
        mainProductPanel.add(imageLabel, BorderLayout.WEST);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(new EmptyBorder(10,10,10,10));
        JLabel nameLabel = new JLabel(item.name);
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        JLabel priceLabel = new JLabel(String.format("$%.2f", item.price));
        priceLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        JLabel quantityLabel = new JLabel("Quantity: " + item.quantity);
        quantityLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));

        infoPanel.add(nameLabel);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(priceLabel);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(quantityLabel);

        mainProductPanel.add(infoPanel, BorderLayout.CENTER);

        return mainProductPanel;
    }

    public void readCartCSV(ClassLoader classLoader) {
        cart.clear();
        Map<String, CartItem> mergedCart = new LinkedHashMap<>();

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

                            CartItem existing = mergedCart.get(name);
                            existing.quantity += quantity;
                        } else {
                            mergedCart.put(name, new CartItem(name, price, quantity));
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
        products = productClass.readProductCSV(getClass().getClassLoader());
    }
}