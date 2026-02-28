import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class UI extends JPanel {

    // =========================
    // MAIN UI SETUP
    // =========================

    public UI() {

        // Setting UI layout
        setLayout(new BorderLayout());

        // =========================
        // PAGE SWITCHING (TITLE + SHOPPING)
        // =========================
        CardLayout cardLayout = new CardLayout();
        JPanel pages = new JPanel(cardLayout);

        // Creating Shopping Page Panel
        JPanel shoppingPage = new JPanel();
        shoppingPage.setLayout(new BorderLayout());
        shoppingPage.setBackground(new Color(245, 241, 232));

        // Creating Product Display grid Panel
        JPanel ProductDisplay = new JPanel();
        ProductDisplay.setLayout(new GridLayout(4,3,20,20));
        ProductDisplay.setBackground(new Color(245, 241, 232));

        // =========================
        // LANDING PAGE (TITLE SCREEN)
        // =========================

        // Title Panel
        JPanel title = new JPanel();
        title.setLayout(new BorderLayout());

        // =========================
        // PANEL FOR IMAGES IN TITLE PAGE
        // =========================

        JPanel bottomImages = new JPanel(new FlowLayout(FlowLayout.CENTER, 60, 40));
        bottomImages.setOpaque(false);
        bottomImages.setBorder(new EmptyBorder(30, 0, 60, 0));

        // Load images
        ImageIcon iconA = new ImageIcon(getClass().getResource("/images/image1_title.jpg"));
        ImageIcon iconB = new ImageIcon(getClass().getResource("/images/image2_title.jpg"));
        ImageIcon iconC = new ImageIcon(getClass().getResource("/images/image3_title.jpg"));

        // Scale images
        JLabel img1 = new JLabel(new ImageIcon(iconA.getImage().getScaledInstance(230, 230, Image.SCALE_SMOOTH)));
        JLabel img2 = new JLabel(new ImageIcon(iconB.getImage().getScaledInstance(230, 230, Image.SCALE_SMOOTH)));
        JLabel img3 = new JLabel(new ImageIcon(iconC.getImage().getScaledInstance(230, 230, Image.SCALE_SMOOTH)));


        // Create separate cards for each image
        JPanel card1 = new JPanel(new BorderLayout());
        card1.setBackground(Color.WHITE);
        card1.setPreferredSize(new Dimension(260, 260));
        card1.setBorder(new LineBorder(new Color(220,220,220), 1, true));
        card1.add(img1, BorderLayout.CENTER);

        JPanel card2 = new JPanel(new BorderLayout());
        card2.setBackground(Color.WHITE);
        card2.setPreferredSize(new Dimension(260, 260));
        card2.setBorder(new LineBorder(new Color(220,220,220), 1, true));
        card2.add(img2, BorderLayout.CENTER);

        JPanel card3 = new JPanel(new BorderLayout());
        card3.setBackground(Color.WHITE);
        card3.setPreferredSize(new Dimension(260, 260));
        card3.setBorder(new LineBorder(new Color(220,220,220), 1, true));
        card3.add(img3, BorderLayout.CENTER);

        // Add cards to bottom panel
        bottomImages.add(card1);
        bottomImages.add(card2);
        bottomImages.add(card3);

        // Add to title panel
        title.add(bottomImages, BorderLayout.SOUTH);

        // Center panel (title + button)
        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(new EmptyBorder(80, 0, 20, 0));

        // Title label
        JLabel titleLabel = new JLabel("Glamify Shopping");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 70));
        titleLabel.setForeground(new Color(212, 175, 55));
        titleLabel.setOpaque(false);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBackground(new Color(0, 0, 0, 120));
        titleLabel.setBorder(new EmptyBorder(10, 20, 10, 20));

        // Start Shopping Button
        JButton shoppingButton = new JButton("Start Shopping");
        shoppingButton.setBackground(new Color(212, 175, 55));
        shoppingButton.setForeground(Color.BLACK);
        shoppingButton.setFont(new Font("SansSerif", Font.BOLD, 20));
        shoppingButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        shoppingButton.setOpaque(true);
        shoppingButton.setBorder(new EmptyBorder(10, 25, 10, 25));
        shoppingButton.setFocusPainted(false);
        shoppingButton.addActionListener(e-> {
            cardLayout.show(pages, "SHOPPING");
        });

        // =========================
        // TOP NAVIGATION BAR
        // =========================
        JPanel header =  new JPanel();
        header.setLayout(new BorderLayout());
        header.setBackground(new Color(13, 13, 13));
        header.setPreferredSize(new Dimension(0, 60));
        // Adding Cart
        JLabel cart = new JLabel("🛒");
        JLabel companyName = new JLabel("\uD835\uDCD6\uD835\uDCF5\uD835\uDCEA\uD835\uDCF6\uD835\uDCF2\uD835\uDCEF\uD835\uDD02");
        cart.setFont(new Font("SansSerif", Font.PLAIN, 20));
        cart.setForeground(Color.WHITE);
        cart.setBorder(new EmptyBorder(10, 20, 10, 20));
        // Adding Company Name
        companyName.setFont(new Font("SansSerif", Font.PLAIN, 25));
        companyName.setForeground(Color.WHITE);
        companyName.setBorder(new EmptyBorder(10, 20, 10, 20));
        header.add(cart, BorderLayout.EAST);

        // Home Button
        JButton homeButton = new JButton("ʜᴏᴍᴇ");
        homeButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        homeButton.setForeground(Color.WHITE);
        homeButton.setBackground(new Color(0,0,0,0));
        homeButton.setBorder(new EmptyBorder(5, 15, 5, 15));
        homeButton.setFocusPainted(false);
        homeButton.setOpaque(false);
        homeButton.addActionListener(e-> {
            cardLayout.show(pages, "TITLE");
        });

        // Adding components to the landing page
        centerPanel.add(titleLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        centerPanel.add(shoppingButton);
        title.add(centerPanel,BorderLayout.CENTER);

        // =========================
        // SHOPPING PAGE (FILTER PANEL + PRODUCT GRID)
        // =========================

        // Filter Panel
        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.Y_AXIS));
        filterPanel.setPreferredSize(new Dimension(250, 0));
        filterPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        filterPanel.setOpaque(false);

        // Creating Products
        JPanel product1 = createProductPanel("Casual Hoodie", 39.99, "/images/clothes/casualHoodie.jpg");
        JPanel product2 = createProductPanel("Checkered Tee", 24.99, "/images/clothes/checkeredTee.jpg");
        JPanel product3 = createProductPanel("Graphic Tee", 22.99, "/images/clothes/graphicTee.jpg");
        JPanel product4 = createProductPanel("Sweater", 44.99, "/images/clothes/sweater.jpg");
        JPanel product5 = createProductPanel("Floral Dress", 59.99, "/images/clothes/floralDress.jpg");
        JPanel product6 = createProductPanel("Party Dress", 79.99, "/images/clothes/partyDress.jpg");
        JPanel product7 = createProductPanel("Denim Jacket", 64.99, "/images/clothes/denim.jpg");
        JPanel product8 = createProductPanel("Creme Trench Coat", 89.99, "/images/clothes/cremeTrenchCoat.jpg");
        JPanel product9 = createProductPanel("Jacket", 54.99, "/images/clothes/jacket.jpg");
        JPanel product10 = createProductPanel("Office Blazer", 69.99, "/images/clothes/officeBlazer.jpg");
        JPanel product11 = createProductPanel("Track Suit", 49.99, "/images/clothes/athleteOutfit.jpg");
        JPanel product12 = createProductPanel("Preppy Sweater", 45.99, "/images/clothes/preppy.jpg");

        // Adding them to the shopping page
        ProductDisplay.add(wrap(product1));
        ProductDisplay.add(wrap(product2));
        ProductDisplay.add(wrap(product3));
        ProductDisplay.add(wrap(product4));
        ProductDisplay.add(wrap(product5));
        ProductDisplay.add(wrap(product6));
        ProductDisplay.add(wrap(product7));
        ProductDisplay.add(wrap(product8));
        ProductDisplay.add(wrap(product9));
        ProductDisplay.add(wrap(product10));
        ProductDisplay.add(wrap(product11));
        ProductDisplay.add(wrap(product12));

        // Scroll Panel for the shopping grid
        ProductDisplay.setPreferredSize(new Dimension(700, 1200));
        JScrollPane scrollPane = new JScrollPane(ProductDisplay);
        shoppingPage.add(scrollPane, BorderLayout.CENTER);
        shoppingPage.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setOpaque(false);

        // Adding components
        shoppingPage.add(filterPanel, BorderLayout.WEST);
        pages.add(title, "TITLE");
        pages.add(shoppingPage, "SHOPPING");
        JPanel leftHeader = new JPanel(new FlowLayout(FlowLayout.LEFT, 7, 5));
        leftHeader.setOpaque(false);
        leftHeader.add(companyName);
        leftHeader.add(homeButton);
        header.add(leftHeader, BorderLayout.WEST);
        add(header, BorderLayout.NORTH);
        add(pages, BorderLayout.CENTER);
        ProductDisplay.setOpaque(false);

    }

    // =========================
    // PRODUCT CARD TEMPLATE
    // Creates a single product card with image, name, price, and button
    // =========================
    public JPanel createProductPanel(String name, double priceValue, String imagePath){
        // Creating the panel for the product
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setMaximumSize(new Dimension(300, Integer.MAX_VALUE));
        panel.setBackground(Color.WHITE);
        panel.setBorder(
                BorderFactory.createCompoundBorder(
                        new LineBorder(new Color(26, 26, 26), 1, true),
                        new EmptyBorder(10, 10, 10, 10)
                )
        );
        panel.setOpaque(true);


        // Loading the image for the product
        int boxW = 225;
        int boxH = 150;
        ImageIcon icon = new ImageIcon(getClass().getResource(imagePath));
        Image img = icon.getImage();

        // scale to fit inside the box while keeping aspect ratio
        double scale = Math.min((double)boxW / img.getWidth(null), (double)boxH / img.getHeight(null));
        int newW = (int)(img.getWidth(null) * scale);
        int newH = (int)(img.getHeight(null) * scale);
        Image scaled = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(scaled));

        // center the image inside the box
        JPanel imageContainer = new JPanel(new GridBagLayout());
        imageContainer.setPreferredSize(new Dimension(boxW, boxH));
        imageContainer.setBackground(Color.WHITE); // or your card color
        imageContainer.add(imageLabel);

        panel.add(imageContainer);

        // Adding the product name
        JLabel productName = new JLabel(name);
        productName.setFont(new Font("Segoe UI", Font.BOLD, 16));
        productName.setCursor(new Cursor(Cursor.HAND_CURSOR));
        productName.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        productName.setBorder(new EmptyBorder(5,0,2,0));
        productName.addMouseListener(new java.awt.event.MouseAdapter() {
            // TODO: Add Product Details

        }
        );
        panel.add(productName);

        // Price
        JLabel price = new JLabel("$" + String.valueOf(priceValue));
        price.setFont(new Font("Segoe UI", Font.BOLD, 20));
        price.setForeground(new Color(138, 28, 28));
        price.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        price.setBorder(new EmptyBorder(2, 0, 10, 0));
        panel.add(price);

        // Creating the Add to Cart Button
        JButton addToCartButton = new JButton("Add To Cart");
        addToCartButton.setBackground(new Color(212, 175, 55));
        addToCartButton.setForeground(Color.BLACK);
        addToCartButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        addToCartButton.setFocusPainted(false);


        panel.add(addToCartButton);

        return panel;
    }

    // Wrapper Method: centers a product card inside its grid cell
    private JPanel wrap(JPanel card) {
        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        wrapper.setOpaque(false);
        wrapper.add(card);
        return wrapper;
    }

}
