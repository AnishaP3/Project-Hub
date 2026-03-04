import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.List;

public class UI extends JPanel {

    CardLayout cardLayout = new CardLayout();
    JPanel pages = new JPanel(cardLayout);

    public UI() {
        setLayout(new BorderLayout());

        JPanel shoppingPage = new JPanel();
        shoppingPage.setLayout(new BorderLayout());
        shoppingPage.setBackground(new Color(245, 241, 232));

        JPanel ProductDisplay = new JPanel();
        ProductDisplay.setLayout(new GridLayout(4, 3, 20, 20));
        ProductDisplay.setBackground(new Color(245, 241, 232));
        ProductDisplay.setBorder(new EmptyBorder(20, 0, 20, 0));

        List<Products> productsList = Products.readProductCSV(getClass().getClassLoader());

        // =========================
        // LANDING PAGE
        // =========================
        JPanel title = new JPanel();
        title.setLayout(new BorderLayout());

        JPanel bottomImages = new JPanel(new FlowLayout(FlowLayout.CENTER, 60, 40));
        bottomImages.setOpaque(false);
        bottomImages.setBorder(new EmptyBorder(30, 0, 60, 0));

        ImageIcon iconA = new ImageIcon(getClass().getResource("/clothes/image1_title.jpg"));
        ImageIcon iconB = new ImageIcon(getClass().getResource("/clothes/image2_title.jpg"));
        ImageIcon iconC = new ImageIcon(getClass().getResource("/clothes/image3_title.jpg"));

        int cardImgSize = 240;
        JLabel img1 = new JLabel(new ImageIcon(scaleToFit(iconA.getImage(), cardImgSize, cardImgSize)));
        JLabel img2 = new JLabel(new ImageIcon(scaleToFit(iconB.getImage(), cardImgSize, cardImgSize)));
        JLabel img3 = new JLabel(new ImageIcon(scaleToFit(iconC.getImage(), cardImgSize, cardImgSize)));
        img1.setHorizontalAlignment(JLabel.CENTER);
        img2.setHorizontalAlignment(JLabel.CENTER);
        img3.setHorizontalAlignment(JLabel.CENTER);

        JPanel card1 = new JPanel(new GridBagLayout());
        card1.setBackground(Color.WHITE);
        card1.setPreferredSize(new Dimension(260, 260));
        card1.setBorder(new LineBorder(new Color(220, 220, 220), 1, true));
        card1.add(img1);

        JPanel card2 = new JPanel(new GridBagLayout());
        card2.setBackground(Color.WHITE);
        card2.setPreferredSize(new Dimension(260, 260));
        card2.setBorder(new LineBorder(new Color(220, 220, 220), 1, true));
        card2.add(img2);

        JPanel card3 = new JPanel(new GridBagLayout());
        card3.setBackground(Color.WHITE);
        card3.setPreferredSize(new Dimension(260, 260));
        card3.setBorder(new LineBorder(new Color(220, 220, 220), 1, true));
        card3.add(img3);

        bottomImages.add(card1);
        bottomImages.add(card2);
        bottomImages.add(card3);
        title.add(bottomImages, BorderLayout.SOUTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(new EmptyBorder(80, 0, 20, 0));

        JLabel titleLabel = new JLabel("Glamify Shopping");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 70));
        titleLabel.setForeground(new Color(212, 175, 55));
        titleLabel.setOpaque(false);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBackground(new Color(0, 0, 0, 120));
        titleLabel.setBorder(new EmptyBorder(10, 20, 10, 20));

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
        JPanel header = new JPanel();
        header.setLayout(new BorderLayout());
        header.setBackground(new Color(13, 13, 13));
        header.setPreferredSize(new Dimension(0, 60));

        JLabel cart = new JLabel("🛒");
        JLabel companyName = new JLabel("\uD835\uDCD6\uD835\uDCF5\uD835\uDCEA\uD835\uDCF6\uD835\uDCF2\uD835\uDCEF\uD835\uDD02");
        cart.setFont(new Font("SansSerif", Font.PLAIN, 20));
        cart.setForeground(Color.WHITE);
        cart.setBorder(new EmptyBorder(10, 20, 10, 20));
        companyName.setFont(new Font("SansSerif", Font.PLAIN, 25));
        companyName.setForeground(Color.WHITE);
        companyName.setBorder(new EmptyBorder(10, 20, 10, 20));
        header.add(cart, BorderLayout.EAST);

        JButton homeButton = new JButton("ʜᴏᴍᴇ");
        homeButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        homeButton.setForeground(Color.WHITE);
        homeButton.setBackground(new Color(0, 0, 0, 0));
        homeButton.setBorder(new EmptyBorder(5, 15, 5, 15));
        homeButton.setFocusPainted(false);
        homeButton.setOpaque(false);
        homeButton.addActionListener(e -> cardLayout.show(pages, "TITLE"));

        centerPanel.add(titleLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        centerPanel.add(shoppingButton);
        title.add(centerPanel, BorderLayout.CENTER);

        // =========================
        // SHOPPING PAGE
        // =========================
        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.Y_AXIS));
        filterPanel.setPreferredSize(new Dimension(250, 0));
        filterPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        filterPanel.setOpaque(false);

        for (Products product : productsList) {
            JPanel productPanel = createProductPanel(product);
            ProductDisplay.add(wrap(productPanel));
        }

        ProductDisplay.setPreferredSize(new Dimension(700, 1200));
        JScrollPane scrollPane = new JScrollPane(ProductDisplay);
        shoppingPage.add(scrollPane, BorderLayout.CENTER);
        shoppingPage.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setOpaque(false);

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
    // =========================
    public JPanel createProductPanel(Products products) {
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

        // Image
        int boxW = 225, boxH = 150;
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

        // Product name
        JLabel productName = new JLabel(products.getName());
        productName.setFont(new Font("Segoe UI", Font.BOLD, 16));
        productName.setCursor(new Cursor(Cursor.HAND_CURSOR));
        productName.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        productName.setBorder(new EmptyBorder(5, 0, 2, 0));
        productName.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ProductDetail detail = new ProductDetail(products, cardLayout, pages);
                pages.add(detail, "DETAIL");
                cardLayout.show(pages, "DETAIL");
            }
        });
        panel.add(productName);

        // Price
        JLabel price = new JLabel("$" + products.getPrice());
        price.setFont(new Font("Segoe UI", Font.BOLD, 20));
        price.setForeground(new Color(138, 28, 28));
        price.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        price.setBorder(new EmptyBorder(2, 0, 6, 0));
        panel.add(price);

        // Star rating
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
        panel.add(addToCartButton);

        return panel;
    }

    // Builds a 5-star string supporting half stars (e.g. 4.5 → "★★★★½")
    private String buildStarString(double rating) {
        StringBuilder sb = new StringBuilder();
        int full = (int) rating;
        boolean half = (rating - full) >= 0.5;
        int empty = 5 - full - (half ? 1 : 0);
        for (int i = 0; i < full; i++)  sb.append("★");
        if (half)                        sb.append("½");
        for (int i = 0; i < empty; i++) sb.append("☆");
        return sb.toString();
    }

    private JPanel wrap(JPanel card) {
        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        wrapper.setOpaque(false);
        wrapper.add(card);
        return wrapper;
    }

    private Image scaleToFit(Image img, int maxW, int maxH) {
        int w = img.getWidth(null);
        int h = img.getHeight(null);
        double scale = Math.min((double) maxW / w, (double) maxH / h);
        return img.getScaledInstance((int) (w * scale), (int) (h * scale), Image.SCALE_SMOOTH);
    }
}