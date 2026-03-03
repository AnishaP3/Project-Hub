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

    private void setupPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 241, 232));

        // Header Panel
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

        // Main content split into two sides
        JPanel mainContent = new JPanel(new GridLayout(1, 2, 60, 0));
        mainContent.setBackground(new Color(245, 241, 232));
        mainContent.setBorder(new EmptyBorder(40, 40, 40, 40));

        // LEFT SIDE: Large Image — fills its half naturally
        int bigImgW = 480;
        int bigImgH = 580;
        ImageIcon icon = new ImageIcon(getClass().getResource(product.getImagePath()));
        Image image = icon.getImage();

        // scale to fit the image size relative to screen
        double scale = Math.min((double)bigImgW / image.getWidth(null), (double)bigImgH / image.getHeight(null));
        int newW = (int)(image.getWidth(null) * scale);
        int newH = (int)(image.getHeight(null) * scale);
        Image scaledImage = image.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setVerticalAlignment(JLabel.CENTER);

        // Image Border
        JPanel imageContainer = new JPanel(new GridBagLayout());
        imageContainer.setBackground(Color.WHITE);
        imageContainer.setBorder(new LineBorder(new Color(220, 220, 220), 1, false));
        imageContainer.add(imageLabel);

        // RIGHT SIDE: Details
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(new Color(245, 241, 232));

        // Product name
        JLabel nameLabel = new JLabel(product.getName().toUpperCase());
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 26));
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Thin separator
        JSeparator sep1 = new JSeparator();
        sep1.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sep1.setForeground(new Color(200, 200, 200));

        // Price
        JLabel priceLabel = new JLabel("$" + product.getPrice());
        priceLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        priceLabel.setForeground(new Color(138, 28, 28));
        priceLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Details text
        JTextArea detailText = new JTextArea(product.getDetails());
        detailText.setFont(new Font("SansSerif", Font.PLAIN, 15));
        detailText.setLineWrap(true);
        detailText.setWrapStyleWord(true);
        detailText.setEditable(false);
        detailText.setBackground(new Color(245, 241, 232));
        detailText.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        detailText.setAlignmentX(Component.LEFT_ALIGNMENT);
        detailText.setForeground(new Color(80, 80, 80));

        // Quantity label
        JLabel qtyLabel = new JLabel("Quantity");
        qtyLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        qtyLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Quantity spinner
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

        // Adding components to right side with spacing
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(nameLabel);
        infoPanel.add(Box.createVerticalStrut(12));
        infoPanel.add(sep1);
        infoPanel.add(Box.createVerticalStrut(12));
        infoPanel.add(priceLabel);
        infoPanel.add(Box.createVerticalStrut(20));
        infoPanel.add(detailText);
        infoPanel.add(Box.createVerticalStrut(24));
        infoPanel.add(qtyLabel);
        infoPanel.add(Box.createVerticalStrut(8));
        infoPanel.add(quantitySpinner);
        infoPanel.add(Box.createVerticalStrut(20));
        infoPanel.add(buyButton);

        mainContent.add(imageContainer);
        mainContent.add(infoPanel);

        add(mainContent, BorderLayout.CENTER);
    }
}