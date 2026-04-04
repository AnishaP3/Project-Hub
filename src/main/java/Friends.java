import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Friends extends JPanel {
    String[] friendNames = {"Alexis", "Samantha", "Riley", "Adriana", "Jane"};
    String[] timeAgo = {"Just now", "1 hr ago", "Yesterday", "Long time ago", "5 min ago"};
    public Friends(List<Products> productsList, UI ui) {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 241, 232));
        JLabel title = new JLabel("Friends Activity");
        title.setFont(new Font("Serif", Font.BOLD, 28));
        title.setForeground(new Color(212, 175, 55));
        title.setBorder(new EmptyBorder(30, 30, 10, 30));
        add(title, BorderLayout.NORTH);

        JPanel friendsPanel = new JPanel();
        friendsPanel.setLayout(new BoxLayout(friendsPanel, BoxLayout.Y_AXIS));
        friendsPanel.setBackground(new Color(245, 241, 232));
        friendsPanel.setBorder(new EmptyBorder(10, 30, 10, 30));

        // Shuffle and pick 6 random products
        List<Products> shuffled = new ArrayList<>(productsList);
        Collections.shuffle(shuffled);
        List<Products> picks = shuffled.subList(0, 6);
        Random rand = new Random();
        for (int i = 0; i < picks.size(); i++) {
            Products p = picks.get(i);
            String friend = friendNames[rand.nextInt(friendNames.length)];
            String time = timeAgo[i % timeAgo.length];
            friendsPanel.add(buildPanel(p, friend, time, ui));
            friendsPanel.add(Box.createRigidArea(new Dimension(0, 12)));
        }

        // Scroll bar
        JScrollPane scrollPane = new JScrollPane(friendsPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(12);
        add(scrollPane, BorderLayout.CENTER);
    }

    public JPanel buildPanel(Products product, String friend, String time, UI ui) {
        JPanel panel = new JPanel(new BorderLayout(15, 0));
        panel.setBackground(Color.WHITE);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        panel.setBorder(BorderFactory.createCompoundBorder(new LineBorder(new Color(220, 220, 220), 1, true),
                new EmptyBorder(12, 15, 12, 15)));

        // Left panel for initials and product image
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        leftPanel.setOpaque(false);

        // Box with friends initials
        JLabel initials = new JLabel(String.valueOf(friend.charAt(0)), JLabel.CENTER);
        initials.setFont(new Font("SansSerif", Font.BOLD, 18));
        initials.setForeground(Color.WHITE);
        initials.setBackground(new Color(212, 175, 55));
        initials.setOpaque(true);
        initials.setPreferredSize(new Dimension(48, 60));
        leftPanel.add(initials);

        // Product image
        ImageIcon icon = new ImageIcon(getClass().getResource(product.getImagePath()));
        Image scaled = ui.scaleToFit(icon.getImage(), 70, 70);
        JLabel productImage = new JLabel(new ImageIcon(scaled));
        productImage.setBorder(new LineBorder(new Color(220, 220, 220), 1, true));
        leftPanel.add(productImage);
        panel.add(leftPanel, BorderLayout.WEST);

        // Panel to show the product info
        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setOpaque(false);

        JLabel name = new JLabel(friend + " bought " + product.getName());
        name.setFont(new Font("SansSerif", Font.BOLD, 14));

        JLabel details = new JLabel("$" + product.getPrice() + " • " + ui.buildStarString(product.getRating()) + " • " + time);
        details.setFont(new Font("SansSerif", Font.PLAIN, 14));
        details.setForeground(new Color(120, 120, 120));
        info.add(name);
        info.add(Box.createRigidArea(new Dimension(0, 4)));
        info.add(details);
        panel.add(info, BorderLayout.CENTER);

        // Add to cart button on the right
        JButton button = new JButton("Add To Cart");
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setBackground(new Color(212, 175, 55));
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.addActionListener(e -> {
            Cart.addProduct(product);
            Cart.addProductToCartCSV(product);
            int count = Cart.getTotalCountOfItems();
            ui.cartCountLabel.setText(String.valueOf(count));
            ui.cartCountLabel.setVisible(count > 0);
        });
        panel.add(button, BorderLayout.EAST);
        return panel;
    }
}
