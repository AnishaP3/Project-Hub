import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UI extends JPanel {

    CardLayout cardLayout = new CardLayout(); // Allows to switch between pages
    JPanel pages = new JPanel(cardLayout);
    List<Products> productsList;
    JPanel ProductDisplay;
    Filter filter;
    JLabel cartCountLabel;

    // Active filter state
    Set<String> activeColors = new HashSet<>();
    Set<String> activeSizes = new HashSet<>();
    Set<String> activeCategories = new HashSet<>();
    Set<String> activeMaterials = new HashSet<>();
    double activeMinRating = 0.0;

    public UI() {
        setLayout(new BorderLayout());

        // Creating panel for shopping page (where the shopping grid and filter panel are)
        JPanel shoppingPage = new JPanel();
        shoppingPage.setLayout(new BorderLayout());
        shoppingPage.setBackground(new Color(245, 241, 232));

        // Create a panel for product grid
        ProductDisplay = new JPanel();
        ProductDisplay.setLayout(new FlowLayout(FlowLayout.LEFT, 25, 25));
        ProductDisplay.setBackground(new Color(245, 241, 232));
        ProductDisplay.setBorder(new EmptyBorder(20, 0, 20, 0));

        // Loading product list
        productsList = Products.readProductCSV(getClass().getClassLoader());

        // =========================
        // LANDING PAGE
        // =========================

        // main panel for landing page
        JPanel title = new JPanel();
        title.setLayout(new BorderLayout());

        // panel for images at the bottom
        JPanel bottomImages = new JPanel(new FlowLayout(FlowLayout.CENTER, 60, 40));
        bottomImages.setOpaque(false);
        bottomImages.setBorder(new EmptyBorder(30, 0, 60, 0));

        // Getting the images
        ImageIcon iconA = new ImageIcon(getClass().getResource("/clothes/image1_title.jpg"));
        ImageIcon iconB = new ImageIcon(getClass().getResource("/clothes/image2_title.jpg"));
        ImageIcon iconC = new ImageIcon(getClass().getResource("/clothes/image3_title.jpg"));

        // Adjusting images to the right size
        int cardImgSize = 240;
        JLabel img1 = new JLabel(new ImageIcon(scaleToFit(iconA.getImage(), cardImgSize, cardImgSize)));
        JLabel img2 = new JLabel(new ImageIcon(scaleToFit(iconB.getImage(), cardImgSize, cardImgSize)));
        JLabel img3 = new JLabel(new ImageIcon(scaleToFit(iconC.getImage(), cardImgSize, cardImgSize)));
        img1.setHorizontalAlignment(JLabel.CENTER);
        img2.setHorizontalAlignment(JLabel.CENTER);
        img3.setHorizontalAlignment(JLabel.CENTER);

        // Creating panel for first image
        JPanel card1 = new JPanel(new GridBagLayout());
        card1.setBackground(Color.WHITE);
        card1.setPreferredSize(new Dimension(260, 260));
        card1.setBorder(new LineBorder(new Color(220, 220, 220), 1, true));
        card1.add(img1);

        // panel for second image
        JPanel card2 = new JPanel(new GridBagLayout());
        card2.setBackground(Color.WHITE);
        card2.setPreferredSize(new Dimension(260, 260));
        card2.setBorder(new LineBorder(new Color(220, 220, 220), 1, true));
        card2.add(img2);

        // panel for third image
        JPanel card3 = new JPanel(new GridBagLayout());
        card3.setBackground(Color.WHITE);
        card3.setPreferredSize(new Dimension(260, 260));
        card3.setBorder(new LineBorder(new Color(220, 220, 220), 1, true));
        card3.add(img3);

        // Adding images to bottom panel
        bottomImages.add(card1);
        bottomImages.add(card2);
        bottomImages.add(card3);
        // Adding bottom panel to the main panel
        title.add(bottomImages, BorderLayout.SOUTH);

        // Creating panel for title and start shopping button
        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(new EmptyBorder(80, 0, 20, 0));

        // Creating title label
        JLabel titleLabel = new JLabel("Glamify Shopping");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 70));
        titleLabel.setForeground(new Color(212, 175, 55));
        titleLabel.setOpaque(false);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBackground(new Color(0, 0, 0, 120));
        titleLabel.setBorder(new EmptyBorder(10, 20, 10, 20));

        // Creating start shopping button
        JButton shoppingButton = new JButton("Start Shopping");
        shoppingButton.setBackground(new Color(212, 175, 55));
        shoppingButton.setForeground(Color.BLACK);
        shoppingButton.setFont(new Font("SansSerif", Font.BOLD, 20));
        shoppingButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        shoppingButton.setOpaque(true);
        shoppingButton.setBorder(new EmptyBorder(10, 25, 10, 25));
        shoppingButton.setFocusPainted(false);
        shoppingButton.addActionListener(e -> cardLayout.show(pages, "SHOPPING"));

        // =========================
        // TOP NAVIGATION BAR
        // =========================

        // Creating header panel
        JPanel header = new JPanel();
        header.setLayout(new BorderLayout());
        header.setBackground(new Color(13, 13, 13));
        header.setPreferredSize(new Dimension(0, 60));

        // Adding Shopping cart to the header (right side)
        // Create cart panel with clickable icon and count badge
        JPanel cartPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        cartPanel.setOpaque(false);

        ImageIcon cartIcon = new ImageIcon(getClass().getResource("Cart/shopping_cart.png"));
        Image cartImage = cartIcon.getImage();
        Image newCartImage = cartImage.getScaledInstance(40, 40, 0);
        cartIcon = new ImageIcon(newCartImage);

//        JButton cartButton = new JButton(cartIcon);
        JButton cartButton = new JButton("🛒");
        cartButton.setFont(new Font("SansSerif", Font.PLAIN, 25));
        cartButton.setForeground(Color.WHITE);
        cartButton.setBorder(new EmptyBorder(12, 0, 0, 15));
        cartButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cartButton.setBackground(new Color(0, 0, 0));
        cartButton.addActionListener(e -> {
            // Find the checkout page and refresh it before showing
            for (Component comp : pages.getComponents()) {
                if (comp instanceof CheckoutPage) {
                    ((CheckoutPage) comp).reloadPage();
                    break;
                }
            }
            cardLayout.show(pages, "CHECKOUT");
        });

        // Cart count badge
        cartCountLabel = new JLabel("0");
        cartCountLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        cartCountLabel.setForeground(Color.WHITE);
        cartCountLabel.setBackground(new Color(0, 0, 0));
        cartCountLabel.setOpaque(true);
        cartCountLabel.setBorder(new EmptyBorder(2, 5, 2, 8));
        cartCountLabel.setVisible(false);

        cartPanel.add(cartCountLabel);
        cartPanel.add(cartButton);

        JLabel companyName = new JLabel("\uD835\uDCD6\uD835\uDCF5\uD835\uDCEA\uD835\uDCF6\uD835\uDCF2\uD835\uDCEF\uD835\uDD02");
        companyName.setFont(new Font("SansSerif", Font.PLAIN, 25));
        companyName.setForeground(Color.WHITE);
        companyName.setBorder(new EmptyBorder(10, 20, 10, 20));
        // Add to header
        header.add(companyName, BorderLayout.WEST);
        header.add(cartPanel, BorderLayout.EAST);

        // Adding Components to their respective panels
        centerPanel.add(titleLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        centerPanel.add(shoppingButton);
        title.add(centerPanel, BorderLayout.CENTER);

        // =========================
        // SHOPPING PAGE
        // =========================

        filter = new Filter(productsList);

        // Creating Filter panel
        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.Y_AXIS));
        filterPanel.setBackground(Color.WHITE);

        // Creating Filter Scroll
        JScrollPane filterScroll = new JScrollPane(filterPanel);
        filterScroll.setPreferredSize(new Dimension(260, 0));
        filterScroll.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(220, 220, 220)));
        filterScroll.getVerticalScrollBar().setUnitIncrement(12);
        filterScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // "Filter" header row
        JPanel optionsRow = buildSectionHeader("Filters", false, null, null);
        filterPanel.add(optionsRow);
        filterPanel.add(buildDivider());

        // SIZE section
        String[] sizeOptions = {"Small", "Medium", "Large"};
        JPanel sizeContent = buildCheckboxGrid(sizeOptions, activeSizes);
        filterPanel.add(buildCollapsibleSection("Size", sizeContent, true));
        filterPanel.add(buildDivider());

        // COLOUR section
        String[] colorOptions = {"Grey", "Black", "White", "Cream", "Pink", "Blue", "Red", "Beige"};
        JPanel colorContent = buildCheckboxGrid(colorOptions, activeColors);
        filterPanel.add(buildCollapsibleSection("Colour", colorContent, false));
        filterPanel.add(buildDivider());

        // CATEGORY section
        String[] categoryOptions = {"Hoodie", "Shirt", "T-Shirt", "Sweater", "Dress", "Jacket", "Coat", "Blazer", "Track suit"};
        JPanel categoryContent = buildCheckboxGrid(categoryOptions, activeCategories);
        filterPanel.add(buildCollapsibleSection("Category", categoryContent, false));
        filterPanel.add(buildDivider());

        // MATERIAL section
        String[] materialOptions = {"Cotton", "Polyester", "Wool", "Acrylic", "Denim", "Chiffon", "Viscose"};
        JPanel materialContent = buildCheckboxGrid(materialOptions, activeMaterials);
        filterPanel.add(buildCollapsibleSection("Material", materialContent, false));
        filterPanel.add(buildDivider());

        // RATING section
        String[] ratingOptions = {"3+", "3.5+", "4+", "4.5+", "5+"};
        JPanel ratingContent = buildCheckboxGrid(ratingOptions, new HashSet<>());
        // Rating uses single-select logic — wire each checkbox manually
        JPanel ratingSection = buildCollapsibleSection("Rating", ratingContent, false);
        filterPanel.add(ratingSection);

        // Wire up all checkboxes to the combined filter
        wireCheckboxes(sizeContent,     activeSizes,      "size");
        wireCheckboxes(colorContent,    activeColors,     "color");
        wireCheckboxes(categoryContent, activeCategories, "category");
        wireCheckboxes(materialContent, activeMaterials,  "material");
        wireRatingCheckboxes(ratingContent);

        // Adds each product to the product grid
        for (Products product : productsList) {
            JPanel productPanel = createProductPanel(product);
            ProductDisplay.add(wrap(productPanel));
        }

        // Sets shopping page attributes
        ProductDisplay.setPreferredSize(new Dimension(700, 1200));
        JScrollPane scrollPane = new JScrollPane(ProductDisplay);
        shoppingPage.add(scrollPane, BorderLayout.CENTER);
        shoppingPage.setOpaque(true);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setOpaque(false);

        // Adding components to the pages
        shoppingPage.add(filterScroll, BorderLayout.WEST);
        pages.add(title, "TITLE");
        pages.add(shoppingPage, "SHOPPING");

        // Making the recommendations page
        RecommendationPage recPage = new RecommendationPage(this);
        pages.add(recPage, "RECOMMENDATIONS");

        //Making the Quiz page:
        QuizPage quiz = new QuizPage(cardLayout, pages, recPage);
        pages.add(quiz, "QUIZ");

        // Making the Checkout Page
        CheckoutPage checkout = new CheckoutPage(this);
        pages.add(checkout, "CHECKOUT");

        // Making the Friends Page
        Friends friends = new Friends(productsList, this);
        pages.add(friends, "FRIENDS");

        //making the menu buttons
        JButton homeButton = createMenuButton("ʜᴏᴍᴇ", e -> cardLayout.show(pages, "TITLE"));
        JButton shoppingMenuButton = createMenuButton("ᴘʀᴏᴅᴜᴄᴛs", e -> cardLayout.show(pages, "SHOPPING"));
        JButton quizButton = createMenuButton("ǫᴜɪᴢ", e -> cardLayout.show(pages, "QUIZ"));
        JButton friendsButton = createMenuButton("ꜰʀɪᴇɴᴅs", e -> cardLayout.show(pages, "FRIENDS"));

        // Creating panel for Company name and home button and adding them to it
        JPanel leftHeader = new JPanel(new FlowLayout(FlowLayout.LEFT, 7, 5));
        leftHeader.setOpaque(false);
        leftHeader.add(companyName);

        //menu buttons
        leftHeader.add(homeButton);
        leftHeader.add(shoppingMenuButton);
        leftHeader.add(quizButton);
        leftHeader.add(friendsButton);

        header.add(leftHeader, BorderLayout.WEST);
        add(header, BorderLayout.NORTH);
        add(pages, BorderLayout.CENTER);
        ProductDisplay.setOpaque(false);

        // Show quiz invitation popup shortly after the window appears
        QuizPopUp.show(this, cardLayout, pages);
    }

    // =========================
    // PRODUCT CARD TEMPLATE
    // =========================

    public JPanel createProductPanel(Products products) {
        // Creates a panel for product grid
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(260, 340));
        panel.setMaximumSize(new Dimension(260, 340));
        panel.setBackground(Color.WHITE);
        panel.setBorder(
                BorderFactory.createCompoundBorder(
                        new LineBorder(new Color(26, 26, 26), 1, true),
                        new EmptyBorder(10, 10, 10, 10)
                )
        );
        panel.setOpaque(true);

        // Adjusting images for each product to the right size
        int boxW = 240;
        int boxH = 180;
        ImageIcon icon = new ImageIcon(getClass().getResource(products.getImagePath()));
        Image img = icon.getImage();
        double scale = Math.min((double) boxW / img.getWidth(null), (double) boxH / img.getHeight(null));
        int newW = (int) (img.getWidth(null) * scale);
        int newH = (int) (img.getHeight(null) * scale);
        Image scaled = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(scaled));

        JPanel imageContainer = new JPanel(new GridBagLayout());
        imageContainer.setPreferredSize(new Dimension(boxW, boxH));
        imageContainer.setBackground(Color.WHITE);
        imageContainer.add(imageLabel);
        panel.add(imageContainer);

        // Product name Button --> Leads to Product Details
        JLabel productName = new JLabel(products.getName());
        productName.setFont(new Font("Segoe UI", Font.BOLD, 16));
        productName.setCursor(new Cursor(Cursor.HAND_CURSOR));
        productName.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        productName.setBorder(new EmptyBorder(5, 0, 2, 0));
        productName.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ProductDetail detail = new ProductDetail(products, cardLayout, pages, UI.this);
                pages.add(detail, "DETAIL");
                cardLayout.show(pages, "DETAIL");
            }
        });
        panel.add(productName);

        // Adding the Price
        JLabel price = new JLabel("$" + products.getPrice());
        price.setFont(new Font("Segoe UI", Font.BOLD, 20));
        price.setForeground(new Color(138, 28, 28));
        price.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        price.setBorder(new EmptyBorder(2, 0, 6, 0));
        panel.add(price);

        // Adding the Star rating
        JLabel stars = new JLabel(buildStarString(products.getRating()) + "  " + products.getRating());
        stars.setFont(new Font("SansSerif", Font.PLAIN, 14));
        stars.setForeground(new Color(200, 150, 0));
        stars.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        stars.setBorder(new EmptyBorder(0, 0, 8, 0));
        panel.add(stars);

        // Add to Cart button
        JButton addToCartButton = new JButton("Add To Cart");
        addToCartButton.setBackground(new Color(212, 175, 55));
        addToCartButton.setForeground(Color.BLACK);
        addToCartButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        addToCartButton.setFocusPainted(false);

        addToCartButton.addActionListener(e -> {
            // Only add if not already in cart (quantity starts at 1, adjust in checkout)
            if (Cart.readCart().containsKey(products.getName())) {
                addToCartButton.setText("Already in cart");
                Timer t = new Timer(1200, ev -> addToCartButton.setText("Add To Cart"));
                t.setRepeats(false);
                t.start();
                return;
            }
            Cart.addProduct(products);
            Cart.updateCartCSV(Cart.readCart(), productsList);

            // update cart badge
            int count = Cart.getTotalCountOfItems();
            cartCountLabel.setText(String.valueOf(count));
            cartCountLabel.setVisible(count > 0);
        });

        panel.add(addToCartButton);

        return panel;
    }

    // Builds a 5-star string supporting half stars (e.g. 4.5 → "★★★★½")
    public String buildStarString(double rating) {
        StringBuilder sb = new StringBuilder();
        int full = (int) rating;
        boolean half = (rating - full) >= 0.5;
        int empty = 5 - full - (half ? 1 : 0);
        for (int i = 0; i < full; i++) {
            sb.append("★");
        }
        if (half) {
            sb.append("½");
        }
        for (int i = 0; i < empty; i++){
            sb.append("☆");
        }
        return sb.toString();
    }

    // Wraps the cards so they have nice spacing
    public JPanel wrap(JPanel card) {
        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        wrapper.setOpaque(false);
        wrapper.add(card);
        return wrapper;
    }

    // Scales images to fit
    public Image scaleToFit(Image img, int maxW, int maxH) {
        int w = img.getWidth(null);
        int h = img.getHeight(null);
        double scale = Math.min((double) maxW / w, (double) maxH / h);
        return img.getScaledInstance((int) (w * scale), (int) (h * scale), Image.SCALE_SMOOTH);
    }

    // Refreshes products
    public void refreshProducts(List<Products> list) {
        ProductDisplay.removeAll();
        for (Products product : list) {
            JPanel productPanel = createProductPanel(product);
            ProductDisplay.add(wrap(productPanel));
        }
        ProductDisplay.revalidate();
        ProductDisplay.repaint();
    }


    // ========================
    // FILTER HELPER METHODS
    // ========================

    // Delegates all active filters to the Filter object (AND logic)
    public void applyAllFilters() {
        List<Products> result = filter.applyFilters(
                activeColors,
                activeSizes,
                activeCategories,
                activeMaterials,
                activeMinRating
        );
        refreshProducts(result);
    }

    // Builds a collapsible section: header row that shows/hides the content panel
    public JPanel buildCollapsibleSection(String title, JPanel content, boolean startExpanded) {
        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setBackground(Color.WHITE);
        wrapper.setAlignmentX(Component.LEFT_ALIGNMENT);

        boolean[] expanded = {startExpanded};
        JLabel toggleLabel = new JLabel(startExpanded ? "−" : "+");
        toggleLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        toggleLabel.setForeground(new Color(150, 150, 150));

        JPanel header = buildSectionHeader(title, startExpanded, content, toggleLabel);
        content.setVisible(startExpanded);

        header.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                expanded[0] = !expanded[0];
                content.setVisible(expanded[0]);
                toggleLabel.setText(expanded[0] ? "−" : "+");
                wrapper.revalidate();
                wrapper.repaint();
            }
            public void mouseEntered(MouseEvent e) { header.setCursor(new Cursor(Cursor.HAND_CURSOR)); }
        });

        wrapper.add(header);
        wrapper.add(content);
        return wrapper;
    }

    // Builds a section header row with title and +/− toggle
    public JPanel buildSectionHeader(String title, boolean expanded, JPanel content, JLabel toggleLabel) {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setMaximumSize(new Dimension(Integer.MAX_VALUE, 52));
        header.setBorder(new EmptyBorder(14, 20, 14, 20));

        JLabel titleLabel = new JLabel(title);
        if (title.equals("Filters")) {
            titleLabel.setFont(new Font("Serif", Font.BOLD, 18));
            titleLabel.setForeground(new Color(212, 175, 55));
            titleLabel.setHorizontalAlignment(JLabel.CENTER);
            header.add(titleLabel, BorderLayout.CENTER);
        } else {
            titleLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
            titleLabel.setForeground(new Color(30, 30, 30));
            header.add(titleLabel, BorderLayout.WEST);
        }
        if (toggleLabel != null) header.add(toggleLabel, BorderLayout.EAST);

        return header;
    }

    // Builds a 2-column grid of checkboxes from the given options, centered in the panel
    public JPanel buildCheckboxGrid(String[] options, Set<String> activeSet) {
        JPanel grid = new JPanel(new GridLayout(0, 2, 0, 0));
        grid.setBackground(Color.WHITE);
        grid.setAlignmentX(Component.LEFT_ALIGNMENT);

        for (String opt : options) {
            JCheckBox cb = new JCheckBox(opt);
            cb.setFont(new Font("SansSerif", Font.PLAIN, 14));
            cb.setForeground(new Color(40, 40, 40));
            cb.setBackground(Color.WHITE);
            cb.setFocusPainted(false);
            cb.setBorder(new EmptyBorder(6, 8, 6, 8));
            grid.add(cb);
        }

        // Wrap in a centering panel
        JPanel centered = new JPanel(new BorderLayout());
        centered.setBackground(Color.WHITE);
        centered.setBorder(new EmptyBorder(4, 0, 12, 0));
        centered.add(grid, BorderLayout.NORTH);
        return centered;
    }

    // Wires checkboxes in a grid panel to a Set and triggers combined filter on change
    public void wireCheckboxes(JPanel centeredWrapper, Set<String> activeSet, String filterType) {
        JPanel grid = (JPanel) centeredWrapper.getComponent(0);
        for (Component comp : grid.getComponents()) {
            if (comp instanceof JCheckBox cb) {
                cb.addActionListener(e -> {
                    if (cb.isSelected()) activeSet.add(cb.getText());
                    else activeSet.remove(cb.getText());
                    applyAllFilters();
                });
            }
        }
    }

    // Wires rating checkboxes as single-select (radio-like behaviour)
    public void wireRatingCheckboxes(JPanel centeredWrapper) {
        JPanel grid = (JPanel) centeredWrapper.getComponent(0);
        List<JCheckBox> boxes = new ArrayList<>();
        for (Component comp : grid.getComponents()) {
            if (comp instanceof JCheckBox cb) boxes.add(cb);
        }
        for (JCheckBox cb : boxes) {
            cb.addActionListener(e -> {
                if (cb.isSelected()) {
                    activeMinRating = Double.parseDouble(cb.getText().replace("+", ""));
                    for (JCheckBox other : boxes) if (other != cb) other.setSelected(false);
                } else {
                    activeMinRating = 0.0;
                }
                applyAllFilters();
            });
        }
    }

    // Thin horizontal divider line
    public JPanel buildDivider() {
        JPanel div = new JPanel();
        div.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        div.setPreferredSize(new Dimension(0, 1));
        div.setBackground(new Color(220, 220, 220));
        return div;
    }

    // ----------------------
    // Iteration 2 helper functions
    // ----------------------

    //This function helps make menu buttons
    public static JButton createMenuButton(String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(0, 0, 0, 0)); // transparent
        button.setBorder(new EmptyBorder(5, 15, 5, 15));
        button.setFocusPainted(false);
        button.setOpaque(false);
        button.addActionListener(actionListener);
        return button;
    }
}