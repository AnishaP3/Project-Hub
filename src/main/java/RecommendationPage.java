import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class RecommendationPage extends JPanel {
    private JPanel contentPanel;
    private UI ui;

    public RecommendationPage(UI ui) {
        this.ui = ui;
        // Use BorderLayout for title + scrollable content
        setLayout(new BorderLayout());

        // TOP BAR FOR BACK BUTTON
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(245, 241, 232));

        JButton backButton = new JButton("\uD835\uDE3D\uD835\uDE3C\uD835\uDE3E\uD835\uDE46");
        backButton.setFont(new Font("SansSerif", Font.PLAIN, 16));
        backButton.setForeground(Color.BLACK);
        backButton.setBackground(new Color(245, 241, 232));
        backButton.setBorder(new EmptyBorder(15, 15, 15, 15));
        backButton.setFocusPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.addActionListener(e -> ui.cardLayout.show(ui.pages, "QUIZ"));

        topBar.add(backButton, BorderLayout.WEST);
        add(topBar, BorderLayout.NORTH);


        // TITLE PANEL
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BorderLayout());
        titlePanel.setBackground(new Color(245, 241, 232));

        JLabel title = new JLabel("Your Personalized Recommendations");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Georgia", Font.BOLD, 36));
        title.setForeground(new Color(212, 175, 55));
        title.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Separator after title
        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(220, 210, 200));

        titlePanel.add(title, BorderLayout.CENTER);
        titlePanel.add(sep, BorderLayout.SOUTH);
        add(titlePanel, BorderLayout.CENTER);


        // CONTENT PANEL (holds product cards)
        contentPanel = new JPanel();
        contentPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 25));
        contentPanel.setBackground(new Color(245, 241, 232));

        // SCROLL PANE
        JScrollPane recScroll = new JScrollPane(contentPanel);
        recScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        recScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        recScroll.getVerticalScrollBar().setUnitIncrement(12);
        recScroll.getViewport().setBackground(new Color(245, 241, 232));
        recScroll.setBorder(null);
        add(recScroll, BorderLayout.SOUTH);
    }

    // REFRESHES PRODUCTS ON PAGE
    public void refresh() {
        contentPanel.removeAll();
        RecommendationSystem rs = new RecommendationSystem();
        List<Products> recommended = rs.recommendProducts(getClass().getClassLoader());

        // If no products to recommend
        if (recommended.isEmpty()) {
            JLabel noRecommendations = new JLabel("No recommendations found. Try taking the quiz again!");
            noRecommendations.setFont(new Font("Georgia", Font.PLAIN, 22));
            noRecommendations.setForeground(new Color(150, 140, 130));
            contentPanel.add(noRecommendations);
        }

        // Adds products to the page
        for (Products p : recommended) {
            JPanel card = ui.createProductPanel(p);
            contentPanel.add(ui.wrap(card));
        }

        contentPanel.revalidate();
        contentPanel.repaint();
    }
}