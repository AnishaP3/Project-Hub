import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RecommendationPage extends JPanel {
    private JPanel contentPanel;
    private UI ui;

    public RecommendationPage(UI ui) {
        this.ui = ui;
        // Use BorderLayout for title + scrollable content
        setLayout(new BorderLayout());

        // TITLE PANEL
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(245, 241, 232));

        JLabel title = new JLabel("Your Personalized Recommendations");
        title.setFont(new Font("Serif", Font.BOLD, 36));
        title.setForeground(new Color(212, 175, 55));
        title.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        titlePanel.add(title);
        add(titlePanel, BorderLayout.NORTH);

        // CONTENT PANEL (holds product cards)
        contentPanel = new JPanel();
        contentPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 25, 25));
        contentPanel.setBackground(new Color(245, 241, 232));

        // SCROLL PANE
        JScrollPane recScroll = new JScrollPane(contentPanel);
        recScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        recScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        recScroll.getVerticalScrollBar().setUnitIncrement(12);
        recScroll.setBorder(null);
        add(recScroll, BorderLayout.CENTER);
    }
    public void refresh() {
        contentPanel.removeAll();
        RecommendationSystem rs = new RecommendationSystem();
        List<Products> recommended = rs.recommendProducts(getClass().getClassLoader());

        if (recommended.isEmpty()) {
            JLabel noRecommendations = new JLabel("No recommendations found. Try taking the quiz again!");
            noRecommendations.setFont(new Font("Serif", Font.BOLD, 20));
            noRecommendations.setForeground(new Color(150, 150, 150));
            contentPanel.add(noRecommendations);
        }

        for (Products p : recommended) {
            JPanel card = ui.createProductPanel(p);
            contentPanel.add(ui.wrap(card));
        }

        contentPanel.revalidate();
        contentPanel.repaint();
    }
}

