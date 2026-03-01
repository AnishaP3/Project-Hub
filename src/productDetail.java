import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

public class productDetail extends JPanel {

    private String name;
    private String imagePath;
    private CardLayout cardLayout;
    private JPanel pages;

    public productDetail(String name, String imagePath, CardLayout cardLayout, JPanel pages) {
        this.name = name;
        this.imagePath = imagePath;
        this.cardLayout = cardLayout;
        this.pages = pages;

        setupPanel();
    }

    private void setupPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 241, 232));

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

        JLabel productTitle = new JLabel("Product Details");
        productTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
        productTitle.setForeground(Color.WHITE);

        header.add(backButton, BorderLayout.WEST);
        header.add(productTitle, BorderLayout.CENTER);

    }
}