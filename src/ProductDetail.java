import javax.swing.*;
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
        backButton.setBackground(new Color(0, 0, 0, 0));
        backButton.setFocusPainted(false);
        backButton.setOpaque(false);
        backButton.addActionListener(e -> {
            cardLayout.show(pages, "SHOPPING");
        });

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(new Color(245, 241, 232));

        JLabel name = new JLabel(product.getName());
        name.setFont(new Font("SansSerif", Font.BOLD, 16));
        name.setAlignmentX(Component.CENTER_ALIGNMENT);
        name.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        //IMAGE
        int boxW = 225;
        int boxH = 150;

        ImageIcon icon = new ImageIcon(getClass().getResource(product.getImagePath()));
        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(boxW, boxH, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);


        //LABEL
        JLabel details = new JLabel(product.getDetails());
        details.setFont(new Font("SansSerif", Font.BOLD, 16));
        details.setAlignmentX(Component.LEFT_ALIGNMENT);

        //ADDING TO PANEL
        contentPanel.add(name);
        contentPanel.add(imageLabel);
        contentPanel.add(details);
        add(contentPanel, BorderLayout.CENTER);

    }
}