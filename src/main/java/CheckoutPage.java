import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

public class CheckoutPage extends JPanel {

    public List<Products> allProducts;
    private JPanel contentPanel;
    private JPanel cartPanel;

    private JLabel totalPriceLabel;
    private JLabel taxLabel;
    private JLabel subtotalLabel;
    private JPanel recommendationsPanel;

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

        JPanel totalPanel = totalPanelSection();
        wrapper.add(totalPanel, BorderLayout.SOUTH);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.WHITE);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        //TODO: RECOMMENDATION PANEL
        recommendationsPanel = new JPanel();
        recommendationsPanel.setLayout(new BoxLayout(recommendationsPanel, BoxLayout.Y_AXIS));
        recommendationsPanel.setBackground(Color.WHITE);
        recommendationsPanel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(220, 210, 180), 1),
                new EmptyBorder(20, 20, 20, 20)
        ));

        JLabel recTitle = new JLabel("✨ YOU MIGHT ALSO LIKE ✨");
        recTitle.setFont(new Font("Georgia", Font.BOLD, 14));
        recTitle.setForeground(new Color(212, 175, 55));
        recTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        recommendationsPanel.add(recTitle);
        recommendationsPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        centerPanel.add(recommendationsPanel, BorderLayout.EAST);

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

        // ADD THIS NULL CHECK
        if (currentProduct == null) {
            System.out.println("ERROR: Product not found: '" + productName + "'");
            JPanel errorPanel = new JPanel();
            errorPanel.add(new JLabel("Product not found: " + productName));
            return errorPanel;
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

        //PRICE * QUANTITY:
        JLabel quantityPrice = new JLabel(String.format("$%.2f",currentProduct.getPrice() * quantity));
        quantityPrice.setHorizontalAlignment(JLabel.CENTER);
        quantityPrice.setVerticalAlignment(JLabel.CENTER);
        quantityPrice.setFont(new Font("SansSerif", Font.BOLD, 30));
        quantityPrice.setBackground(Color.WHITE);
        quantityPrice.setBorder(new EmptyBorder(10,10,10,10));

        //INFORMATION:
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(new EmptyBorder(10,10,10,10));
        JLabel nameLabel = new JLabel(productName);
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 35));


        // ========== ADDED: Make product name clickable ==========
        nameLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        nameLabel.setForeground(new Color(212, 175, 55));
        Products finalProduct = currentProduct;
        nameLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ProductDetail detail = new ProductDetail(finalProduct, ui.cardLayout, ui.pages, ui);
                ui.pages.add(detail, "DETAIL");
                ui.cardLayout.show(ui.pages, "DETAIL");
            }
        });
        // =============== END ADDED =================

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

       // Products finalProduct = currentProduct;
        addQuantityButton.addActionListener(e -> {
            Cart.addProduct(finalProduct);
            Cart.updateCartCSV(Cart.readCart(), allProducts);
            Map<String, Integer> cartMap = Cart.readCart();

            int updatedQuantity = cartMap.get(productName);
            quantityLabel.setText("Quantity: " + Cart.readCart().get(productName));
            quantityPrice.setText(String.format("$%.2f", finalProduct.getPrice() * updatedQuantity));
            updateTotal();
            reloadPage();
        });
        subtractQuantityButton.addActionListener(e -> {
            Map<String, Integer> cartMap = Cart.readCart();

            if(cartMap.containsKey(productName)){
                int newQuantity = cartMap.get(productName) - 1;
                if(newQuantity <= 0){
                    cartPanel.remove(mainProductPanel);
                    Cart.removeProduct(productName);

                    Cart.updateCartCSV(Cart.readCart(), allProducts);
                    reloadPage();
                    return;

                }
                else{
                    cartMap.put(productName, newQuantity);
                }
                Cart.updateCartCSV(cartMap, allProducts);
                int updatedQuantity = cartMap.get(productName);
                quantityLabel.setText("Quantity: " + cartMap.getOrDefault(productName, 0));
                quantityPrice.setText(String.format("$%.2f", finalProduct.getPrice() * updatedQuantity));
                updateTotal();

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

        mainProductPanel.add(imageContainer, BorderLayout.WEST);
        mainProductPanel.add(quantityPrice, BorderLayout.EAST);
        mainProductPanel.add(infoPanel, BorderLayout.CENTER);

        // === ADDED: Accessory button (only at the bottom, after everything is created) ====
        Products accessory = FreqBought.getAccessoryForCheckout(productName);
        if (accessory != null) {
            JPanel accessoryRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
            accessoryRow.setBackground(Color.WHITE);

            JLabel plusIcon = new JLabel("+");
            plusIcon.setFont(new Font("SansSerif", Font.BOLD, 12));
            plusIcon.setForeground(new Color(212, 175, 55));

            JLabel accessoryName = new JLabel(accessory.getName());
            accessoryName.setFont(new Font("SansSerif", Font.PLAIN, 10));
            accessoryName.setForeground(new Color(100, 100, 100));

            JButton addAccessoryBtn = new JButton("ADD");
            addAccessoryBtn.setFont(new Font("SansSerif", Font.BOLD, 9));
            addAccessoryBtn.setBackground(new Color(212, 175, 55));
            addAccessoryBtn.setForeground(Color.BLACK);
            addAccessoryBtn.setFocusPainted(false);
            addAccessoryBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            addAccessoryBtn.setPreferredSize(new Dimension(80, 22));
            addAccessoryBtn.addActionListener(e -> {
                Cart.addProduct(accessory);
                Cart.addProductToCartCSV(accessory);
                Cart.updateCartCSV(Cart.readCart(), allProducts);
                reloadPage();

                int count = Cart.getTotalCountOfItems();
                ui.cartCountLabel.setText(String.valueOf(count));
                ui.cartCountLabel.setVisible(count > 0);
            });

            accessoryRow.add(plusIcon);
            accessoryRow.add(accessoryName);
            accessoryRow.add(addAccessoryBtn);

            // Add the accessory row to infoPanel (this exists now)
            infoPanel.add(Box.createVerticalStrut(10));
            infoPanel.add(accessoryRow);
        }
        // ========== END ADDED ==========

        return mainProductPanel;
    }

    public JPanel totalPanelSection(){
        JPanel totalMainPanel = new JPanel();
        totalMainPanel.setLayout(new BoxLayout(totalMainPanel, BoxLayout.Y_AXIS));
        totalMainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        totalMainPanel.setBackground(new Color(218, 190, 90));

        JPanel subtotalRow = new JPanel(new BorderLayout());
        subtotalRow.setBackground(totalMainPanel.getBackground());
        JLabel subtotalText = new JLabel("Subtotal:");
        subtotalText.setFont(new Font("SansSerif", Font.PLAIN, 20));
        subtotalLabel = new JLabel("$0.00 CAD");
        subtotalLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
        subtotalRow.add(subtotalText, BorderLayout.WEST);
        subtotalRow.add(subtotalLabel, BorderLayout.EAST);

        JPanel taxRow = new JPanel(new BorderLayout());
        taxRow.setBackground(totalMainPanel.getBackground());
        JLabel taxText = new JLabel("Tax (13%):");
        taxText.setFont(new Font("SansSerif", Font.PLAIN, 20));
        taxLabel = new JLabel("$0.00 CAD");
        taxLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
        taxRow.add(taxText, BorderLayout.WEST);
        taxRow.add(taxLabel, BorderLayout.EAST);

        JPanel totalRow = new JPanel(new BorderLayout());
        totalRow.setBackground(totalMainPanel.getBackground());
        JLabel totalText = new JLabel("Total:");
        totalText.setFont(new Font("SansSerif", Font.BOLD, 22));
        totalPriceLabel = new JLabel("$0.00 CAD");
        totalPriceLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        totalRow.add(totalText, BorderLayout.WEST);
        totalRow.add(totalPriceLabel, BorderLayout.EAST);

        // Checkout button
        JButton checkoutButton = new JButton("Checkout");
        checkoutButton.setFont(new Font("SansSerif", Font.BOLD, 20));
        checkoutButton.setForeground(Color.WHITE);
        checkoutButton.setBackground(new Color(20, 18, 14));
        checkoutButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        checkoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        checkoutButton.addActionListener(e -> {
            // Clear the cart
            Cart.clearCart();
            Cart.updateCartCSV(new HashMap<>(), allProducts);

            // Update the cart badge in the main UI
            ui.cartCountLabel.setText("0");
            ui.cartCountLabel.setVisible(false);

            // Show confirmation message
            JOptionPane.showMessageDialog(this,
                    "Thank you for your purchase!\nYour order has been placed.",
                    "Checkout Successful",
                    JOptionPane.INFORMATION_MESSAGE);

            // Refresh the checkout page
            reloadPage();

            // FORCE update the total to show $0.00
            subtotalLabel.setText("$0.00 CAD");
            taxLabel.setText("$0.00 CAD");
            totalPriceLabel.setText("$0.00 CAD");
        });

        totalMainPanel.add(subtotalRow);
        totalMainPanel.add(Box.createVerticalStrut(15));
        totalMainPanel.add(taxRow);
        totalMainPanel.add(Box.createVerticalStrut(15));
        totalMainPanel.add(totalRow);
        totalMainPanel.add(Box.createVerticalStrut(30));
        totalMainPanel.add(checkoutButton);

        updateTotal();
        return totalMainPanel;
    }

    public void updateTotal(){
        Map<String, Integer> cartMap = Cart.readCart();
        double subtotal = 0;

        for(Map.Entry<String, Integer> entry : cartMap.entrySet()){
            String productName = entry.getKey();
            int quantity = entry.getValue();

            Products products = null;
            for(Products p : allProducts){
                if(p.getName().equals(productName)){
                    products = p;
                    break;
                }
            }

            if (products != null) {
                subtotal += products.getPrice() * quantity;
            }

            double tax = subtotal * 0.13;
            double total = subtotal + tax;

            subtotalLabel.setText(String.format("$%.2f CAD", subtotal));
            taxLabel.setText(String.format("$%.2f CAD", tax));
            totalPriceLabel.setText(String.format("$%.2f CAD", total));
        }
    }

    public void getAllProducts() {
        Products productClass = new Products();
        allProducts = productClass.readProductCSV(getClass().getClassLoader());
        // Also load accessories so they can be displayed in cart
        List<Products> accessories = FreqBought.getAllAccessories();
        if (accessories != null) {
            allProducts.addAll(accessories);
        }
        Cart.loadCartFromCSV();
    }

    public void reloadPage() {
        // Reload cart data from CSV
        Cart.loadCartFromCSV();

        // Clear the cart panel
        if (cartPanel != null) {
            cartPanel.removeAll();
        }

        Map<String, Integer> cartMap = Cart.readCart();

        if (cartMap.isEmpty()) {
            JLabel emptyLabel = new JLabel("Your cart is empty. Start shopping!");
            emptyLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
            emptyLabel.setForeground(new Color(150, 150, 150));
            emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            if (cartPanel != null) {
                cartPanel.add(emptyLabel);
            }
        } else {
            if (cartPanel != null) {
                for (Map.Entry<String, Integer> entry : cartMap.entrySet()) {
                    JPanel itemPanel = itemsInCart(entry.getKey(), entry.getValue());
                    cartPanel.add(itemPanel);
                    cartPanel.add(Box.createVerticalStrut(15));
                }
            }
        }

        // Update recommendations panel
        if (recommendationsPanel != null) {
            recommendationsPanel.removeAll();

            JLabel recTitle = new JLabel("✨ YOU MIGHT ALSO LIKE ✨");
            recTitle.setFont(new Font("Georgia", Font.BOLD, 14));
            recTitle.setForeground(new Color(212, 175, 55));
            recTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
            recommendationsPanel.add(recTitle);
            recommendationsPanel.add(Box.createRigidArea(new Dimension(0, 15)));

            if (!cartMap.isEmpty()) {
                JLabel recSubtitle = new JLabel("Complete your look with these accessories");
                recSubtitle.setFont(new Font("SansSerif", Font.PLAIN, 11));
                recSubtitle.setForeground(new Color(150, 150, 150));
                recSubtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
                recommendationsPanel.add(recSubtitle);
                recommendationsPanel.add(Box.createRigidArea(new Dimension(0, 10)));

                Set<String> addedRecs = new HashSet<>();
                for (Map.Entry<String, Integer> entry : cartMap.entrySet()) {
                    Products accessory = FreqBought.getAccessoryForCheckout(entry.getKey());
                    if (accessory != null && !addedRecs.contains(accessory.getName())) {
                        addedRecs.add(accessory.getName());
                        JPanel recCard = createRecommendationCard(accessory);
                        recommendationsPanel.add(recCard);
                        recommendationsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
                    }
                }
            } else {
                JLabel noRecs = new JLabel("Add items to see recommendations");
                noRecs.setFont(new Font("SansSerif", Font.ITALIC, 11));
                noRecs.setForeground(new Color(150, 150, 150));
                noRecs.setAlignmentX(Component.CENTER_ALIGNMENT);
                recommendationsPanel.add(noRecs);
            }
        }

        updateTotal();

        // Refresh the panels
        if (cartPanel != null) {
            cartPanel.revalidate();
            cartPanel.repaint();
        }
        if (recommendationsPanel != null) {
            recommendationsPanel.revalidate();
            recommendationsPanel.repaint();
        }
    }

    private JPanel createRecommendationCard(Products accessory) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(new Color(250, 248, 240));
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(220, 210, 180), 1),
                new EmptyBorder(10, 10, 10, 10)
        ));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        // Image
        JPanel imagePanel = new JPanel(new GridBagLayout());
        imagePanel.setBackground(new Color(250, 248, 240));
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource(accessory.getImagePath()));
            Image scaled = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            JLabel imageLabel = new JLabel(new ImageIcon(scaled));
            imagePanel.add(imageLabel);
        } catch (Exception e) {
            JLabel noImage = new JLabel("📦");
            noImage.setFont(new Font("SansSerif", Font.PLAIN, 30));
            imagePanel.add(noImage);
        }

        // Info
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(new Color(250, 248, 240));

        JLabel nameLabel = new JLabel(accessory.getName());
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 12));

        JLabel priceLabel = new JLabel(String.format("$%.2f", accessory.getPrice()));
        priceLabel.setFont(new Font("SansSerif", Font.PLAIN, 11));
        priceLabel.setForeground(new Color(138, 28, 28));

        infoPanel.add(nameLabel);
        infoPanel.add(priceLabel);

        // Add button
        JButton addBtn = new JButton("+ ADD");
        addBtn.setFont(new Font("SansSerif", Font.BOLD, 10));
        addBtn.setBackground(new Color(212, 175, 55));
        addBtn.setForeground(Color.BLACK);
        addBtn.setFocusPainted(false);
        addBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addBtn.setPreferredSize(new Dimension(65, 25));
        addBtn.addActionListener(e -> {
            Cart.addProduct(accessory);
            Cart.addProductToCartCSV(accessory);
            Cart.updateCartCSV(Cart.readCart(), allProducts);
            reloadPage();

            int count = Cart.getTotalCountOfItems();
            ui.cartCountLabel.setText(String.valueOf(count));
            ui.cartCountLabel.setVisible(count > 0);
        });

        card.add(imagePanel, BorderLayout.WEST);
        card.add(infoPanel, BorderLayout.CENTER);
        card.add(addBtn, BorderLayout.EAST);

        return card;
    }

}