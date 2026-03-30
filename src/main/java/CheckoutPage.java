import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.*;
import java.util.List;

public class CheckoutPage extends JPanel {

    private int total = 0;
    public List<Products> allProducts;

    private JPanel contentPanel;
    private JPanel cartPanel;
    private UI ui;

    CheckoutPage(UI ui) {
        this.ui = ui;
        Cart.loadCartFromCSV();
        checkOutUI();
        reloadPage();

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

        cartPanel = new JPanel();
        cartPanel.setLayout(new BoxLayout(cartPanel, BoxLayout.Y_AXIS));

        Map<String, Integer> cartMap = Cart.readCart();
        for (Map.Entry<String, Integer> entry : cartMap.entrySet()) {
            JPanel itemPanel = itemsInCart(entry.getKey(), entry.getValue());
            cartPanel.add(itemPanel);
            cartPanel.add(Box.createVerticalStrut(15));
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
        recommendations.setBorder(new EmptyBorder(200,200,200,200));
        centerPanel.add(recommendations, BorderLayout.EAST);

    }

    public JPanel itemsInCart(String productName, int quantity) {
        JPanel mainProductPanel = new JPanel(new BorderLayout());
        mainProductPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY,1));
        mainProductPanel.setBackground(Color.WHITE);
        mainProductPanel.setMaximumSize(new Dimension(1000, 400));

        //getting the matching photo to product
        Products currentProduct = null;
        for (Products p : allProducts) {
            if (p.getName().trim().equalsIgnoreCase(productName.trim())) {
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
        JLabel nameLabel = new JLabel(productName);
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 35));
        JLabel priceLabel = new JLabel(String.format("$%.2f", currentProduct.getPrice()));
        priceLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));

        JLabel quantityLabel = new JLabel("Quantity: " + quantity);
        quantityLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));

        JButton addQuantityButton = new JButton("+");
        JButton subtractQuantityButton = new JButton("-");
        addQuantityButton.setFont(new Font("SansSerif", Font.PLAIN, 20));
        addQuantityButton.setForeground(Color.WHITE);
        subtractQuantityButton.setForeground(Color.WHITE);

        addQuantityButton.setBackground(new Color(20, 18, 14));
        subtractQuantityButton.setFont(new Font("SansSerif", Font.PLAIN, 20));
        subtractQuantityButton.setBackground(new Color(20, 18, 14));

        Products finalProduct = currentProduct;
        addQuantityButton.addActionListener(e -> {
            Cart.addProduct(finalProduct);
            Cart.updateCartCSV(Cart.readCart(), allProducts);
            quantityLabel.setText("Quantity: " + Cart.readCart().get(productName));

        });
        subtractQuantityButton.addActionListener(e -> {
            Map<String, Integer> cartMap = Cart.readCart();

            if(cartMap.containsKey(productName)){
                int newQuantity = cartMap.get(productName) - 1;
                if(newQuantity <= 0){
                    cartPanel.remove(mainProductPanel);
                    Cart.removeProduct(productName);

                    cartPanel.revalidate();
                    cartPanel.repaint();
                }
                else{
                    cartMap.put(productName, newQuantity);
                }
                Cart.updateCartCSV(cartMap, allProducts);
                quantityLabel.setText("Quantity: " + cartMap.getOrDefault(productName, 0));


            };
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

    public void getAllProducts() {
        Products productClass = new Products();
        allProducts = productClass.readProductCSV(getClass().getClassLoader());
        Cart.loadCartFromCSV();
    }

    public void reloadPage(){
        removeAll();
        getAllProducts();
        checkOutUI();
        revalidate();
        repaint();
    }

}