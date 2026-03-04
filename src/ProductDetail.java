import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class ProductDetail extends JPanel {
    private Products product;
    private CardLayout cardLayout;
    private JPanel pages;

    public ProductDetail(Products product, CardLayout cardLayout, JPanel pages) {
        this.product = product;
        this.cardLayout = cardLayout;
        this.pages = pages;
        setupPanel();
    }

    private String parseField(String details, String fieldName) {
        String[] lines = details.split("\\. ");
        for (String line : lines) {
            String trimmed = line.trim();
            if (trimmed.toLowerCase().startsWith(fieldName.toLowerCase() + ":")) {
                return trimmed.substring(fieldName.length() + 1).trim();
            }
        }
        return "—";
    }

    private String parseOutfitDescription(String details) {
        int idx = details.toLowerCase().indexOf("material:");
        if (idx > 0) return details.substring(0, idx).trim().replaceAll("\\.$", "");
        return details;
    }

    // Builds a star string supporting half stars
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

    private void setupPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 241, 232));

        String rawDetails = product.getDetails();
        String outfitDesc = parseOutfitDescription(rawDetails);
        String material   = parseField(rawDetails, "Material");
        String color      = parseField(rawDetails, "Color");
        String sizes      = parseField(rawDetails, "Sizes Available");

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(13, 13, 13));
        header.setPreferredSize(new Dimension(0, 60));

        JButton backButton = new JButton("← Back");
        backButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        backButton.setForeground(Color.WHITE);
        backButton.setBorder(new EmptyBorder(0, 20, 0, 0));
        backButton.setContentAreaFilled(false);
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> cardLayout.show(pages, "SHOPPING"));
        header.add(backButton, BorderLayout.WEST);
        add(header, BorderLayout.NORTH);

        // Main content
        JPanel mainContent = new JPanel(new GridLayout(1, 2, 60, 0));
        mainContent.setBackground(new Color(245, 241, 232));
        mainContent.setBorder(new EmptyBorder(40, 40, 40, 40));

        // LEFT: Image
        int bigImgW = 480, bigImgH = 580;
        ImageIcon icon = new ImageIcon(getClass().getResource(product.getImagePath()));
        Image image = icon.getImage();
        double scale = Math.min((double) bigImgW / image.getWidth(null), (double) bigImgH / image.getHeight(null));
        int newW = (int) (image.getWidth(null) * scale);
        int newH = (int) (image.getHeight(null) * scale);
        Image scaledImage = image.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setVerticalAlignment(JLabel.CENTER);

        JPanel imageContainer = new JPanel(new GridBagLayout());
        imageContainer.setBackground(Color.WHITE);
        imageContainer.setBorder(new LineBorder(new Color(220, 220, 220), 1, false));
        imageContainer.add(imageLabel);

        // RIGHT: Info
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(new Color(245, 241, 232));

        // Name
        JLabel nameLabel = new JLabel(product.getName().toUpperCase());
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 26));
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JSeparator sep1 = new JSeparator();
        sep1.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sep1.setForeground(new Color(200, 200, 200));

        // Price
        JLabel priceLabel = new JLabel("$" + product.getPrice());
        priceLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        priceLabel.setForeground(new Color(138, 28, 28));
        priceLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Star rating
        JLabel ratingLabel = new JLabel(buildStarString(product.getRating()) + "   " + product.getRating() + " / 5");
        ratingLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        ratingLabel.setForeground(new Color(200, 150, 0));
        ratingLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Outfit description
        JTextArea descText = new JTextArea(outfitDesc);
        descText.setFont(new Font("SansSerif", Font.ITALIC, 14));
        descText.setLineWrap(true);
        descText.setWrapStyleWord(true);
        descText.setEditable(false);
        descText.setBackground(new Color(245, 241, 232));
        descText.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        descText.setAlignmentX(Component.LEFT_ALIGNMENT);
        descText.setForeground(new Color(100, 100, 100));

        JSeparator sep2 = new JSeparator();
        sep2.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sep2.setForeground(new Color(200, 200, 200));

        // Details grid
        JPanel detailsGrid = new JPanel(new GridLayout(0, 2, 10, 10));
        detailsGrid.setBackground(new Color(245, 241, 232));
        detailsGrid.setAlignmentX(Component.LEFT_ALIGNMENT);
        detailsGrid.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

        detailsGrid.add(makeDetailLabel("Material", true));
        detailsGrid.add(makeDetailLabel(material, false));
        detailsGrid.add(makeDetailLabel("Color", true));
        detailsGrid.add(makeDetailLabel(color, false));
        detailsGrid.add(makeDetailLabel("Sizes Available", true));
        detailsGrid.add(makeDetailLabel(sizes, false));

        JSeparator sep3 = new JSeparator();
        sep3.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sep3.setForeground(new Color(200, 200, 200));

        // Quantity
        JLabel qtyLabel = new JLabel("Quantity");
        qtyLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        qtyLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1, 1, 99, 1);
        JSpinner quantitySpinner = new JSpinner(spinnerModel);
        quantitySpinner.setFont(new Font("SansSerif", Font.PLAIN, 16));
        quantitySpinner.setMaximumSize(new Dimension(100, 40));
        quantitySpinner.setAlignmentX(Component.LEFT_ALIGNMENT);
        ((JSpinner.DefaultEditor) quantitySpinner.getEditor()).getTextField().setHorizontalAlignment(JTextField.CENTER);

        // ADD TO BAG button
        JButton buyButton = new JButton("ADD TO BAG");
        buyButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55));
        buyButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        buyButton.setBackground(Color.BLACK);
        buyButton.setForeground(Color.WHITE);
        buyButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        buyButton.setFocusPainted(false);
        buyButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        buyButton.setOpaque(true);

        // Assemble
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(nameLabel);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(sep1);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(priceLabel);
        infoPanel.add(Box.createVerticalStrut(6));
        infoPanel.add(ratingLabel);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(descText);
        infoPanel.add(Box.createVerticalStrut(14));
        infoPanel.add(sep2);
        infoPanel.add(Box.createVerticalStrut(14));
        infoPanel.add(detailsGrid);
        infoPanel.add(Box.createVerticalStrut(14));
        infoPanel.add(sep3);
        infoPanel.add(Box.createVerticalStrut(16));
        infoPanel.add(qtyLabel);
        infoPanel.add(Box.createVerticalStrut(8));
        infoPanel.add(quantitySpinner);
        infoPanel.add(Box.createVerticalStrut(20));
        infoPanel.add(buyButton);

        mainContent.add(imageContainer);
        mainContent.add(infoPanel);
        add(mainContent, BorderLayout.CENTER);
    }

    private JLabel makeDetailLabel(String text, boolean isKey) {
        JLabel label = new JLabel(text);
        if (isKey) {
            label.setFont(new Font("SansSerif", Font.BOLD, 13));
            label.setForeground(new Color(80, 80, 80));
        } else {
            label.setFont(new Font("SansSerif", Font.PLAIN, 13));
            label.setForeground(new Color(30, 30, 30));
        }
        return label;
    }
}