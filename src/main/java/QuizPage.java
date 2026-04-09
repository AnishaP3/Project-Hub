import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class QuizPage extends JPanel {
    private final RecommendationPage recPage;
    private final CardLayout cardLayout;
    private final String[] questions =
            {"Lifestyle?",
                    "Colour Preferences?",
                    "Tones?",
                    "Budget Range?",
                    "Preferred Style?",
                    "Fabric Preferences?"};
    private final String[][] options = {
            {"Sporty", "Business", "Artsy"},
            {"Red", "Pink", "Black", "Grey", "Blue", "Cream", "White", "Beige"},
            {"Bright", "Monotone", "Uniform"},
            {"0-20", "20-50", "50-80", "80+"},
            {"Minimalistic", "Streetwear", "Formal"},
            {"Cotton", "Wool", "Polyester", "Denim", "Chiffon"}
    };
    private final java.util.List<ButtonGroup> buttonGroups = new ArrayList<>();
    private final JPanel pages;
    private Map<String, String> quizAnswers;

    QuizPage(CardLayout cardLayout, JPanel pages, RecommendationPage recPage) {
        this.cardLayout = cardLayout;
        this.pages = pages;
        this.recPage = recPage;
        setUpQuiz();
    }

    //function writes into the quizResults.csv file (in resource/Quiz)
    public static void writeQuizResult(String[] dataRow) {
        try (FileWriter writer = new FileWriter("resources/Quiz/quizResults.csv", true)) { // append = true
            writer.append(String.join(",", dataRow));
            writer.append(System.lineSeparator());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Sets up the quiz UI
    public void setUpQuiz() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 241, 232));
        quizAnswers = new HashMap<>();

        // ── main scrollable content panel ────────────────────────────────────
        JPanel mainContent = new JPanel();
        mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.Y_AXIS));
        mainContent.setBackground(new Color(245, 241, 232));
        mainContent.setBorder(new EmptyBorder(40, 80, 40, 80));

        // ── Title ─────────────────────────────────────────────────────────────
        JLabel quizTitle = new JLabel("✦  Style Quiz  ✦");
        quizTitle.setFont(new Font("Georgia", Font.BOLD, 34));
        quizTitle.setForeground(new Color(218, 190, 90));
        quizTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        // ── Subtitle ─────────────────────────────────────────────────────────────
        JLabel quizSubtitle = new JLabel("Answer each question to get personalised recommendations");
        quizSubtitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        quizSubtitle.setForeground(new Color(160, 148, 120));
        quizSubtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainContent.add(quizTitle);
        mainContent.add(Box.createRigidArea(new Dimension(0, 6)));
        mainContent.add(quizSubtitle);
        mainContent.add(Box.createRigidArea(new Dimension(0, 30)));
        mainContent.add(buildGoldDivider());
        mainContent.add(Box.createRigidArea(new Dimension(0, 28)));

        // ── One card per question ─────────────────────────────────────────────
        for (int i = 0; i < questions.length; i++) {

            // White card container
            JPanel card = new JPanel();
            card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
            card.setBackground(Color.WHITE);
            card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(225, 215, 185), 1, true),
                    new EmptyBorder(20, 24, 20, 24)
            ));
            card.setAlignmentX(Component.CENTER_ALIGNMENT);
            card.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

            // Question number badge + question text
            JPanel questionHeader = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
            questionHeader.setOpaque(false);
            questionHeader.setAlignmentX(Component.LEFT_ALIGNMENT);

            JLabel badge = new JLabel(String.valueOf(i + 1)) {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(new Color(218, 190, 90));
                    g2.fillOval(0, 0, getWidth(), getHeight());
                    g2.dispose();
                    super.paintComponent(g);
                }
            };
            badge.setFont(new Font("SansSerif", Font.BOLD, 12));
            badge.setForeground(Color.WHITE);
            badge.setHorizontalAlignment(SwingConstants.CENTER);
            badge.setPreferredSize(new Dimension(26, 26));
            badge.setOpaque(false);

            JLabel nameLabel = new JLabel(questions[i]);
            nameLabel.setFont(new Font("Georgia", Font.BOLD, 20));
            nameLabel.setForeground(new Color(60, 50, 35));

            questionHeader.add(badge);
            questionHeader.add(nameLabel);
            card.add(questionHeader);
            card.add(Box.createRigidArea(new Dimension(0, 12)));

            // ── Separator ─────────────────────────────────────────────────────────────
            JSeparator innerSep = new JSeparator();
            innerSep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
            innerSep.setForeground(new Color(235, 225, 200));
            card.add(innerSep);
            card.add(Box.createRigidArea(new Dimension(0, 12)));

            // ── Radio buttons — exact original logic, only styled ─────────────
            ButtonGroup group = new ButtonGroup();
            buttonGroups.add(group);

            JPanel optionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 6));
            optionsPanel.setOpaque(false);
            optionsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

            for (int j = 0; j < options[i].length; j++) {
                String optionText = options[i][j];

                JRadioButton radioButton = new JRadioButton(optionText); //using radio button so user can only click 1
                radioButton.setOpaque(false);
                radioButton.setBackground(new Color(0, 0, 0, 0));
                radioButton.setFont(new Font("Calibri", Font.PLAIN, 16));
                radioButton.setForeground(new Color(55, 45, 30));
                radioButton.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(210, 195, 155), 1, true),
                        new EmptyBorder(6, 14, 6, 14)
                ));
                radioButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                radioButton.setFocusPainted(false);

                group.add(radioButton);

                int questionIndex = i;
                radioButton.addActionListener(e -> {
                    quizAnswers.put(questions[questionIndex], radioButton.getText()); //saving answers into Map<String,String>

                    // Highlight selected pill, reset others
                    java.util.Enumeration<AbstractButton> elements = group.getElements();
                    while (elements.hasMoreElements()) {
                        AbstractButton ab = elements.nextElement();
                        if (ab.isSelected()) {
                            ab.setOpaque(true);
                            ab.setBackground(new Color(253, 248, 225));
                            ab.setBorder(BorderFactory.createCompoundBorder(
                                    BorderFactory.createLineBorder(new Color(218, 190, 90), 2, true),
                                    new EmptyBorder(6, 14, 6, 14)
                            ));
                            ab.setFont(new Font("Calibri", Font.BOLD, 16));
                        } else {
                            ab.setOpaque(false);
                            ab.setBorder(BorderFactory.createCompoundBorder(
                                    BorderFactory.createLineBorder(new Color(210, 195, 155), 1, true),
                                    new EmptyBorder(6, 14, 6, 14)
                            ));
                            ab.setFont(new Font("Calibri", Font.PLAIN, 16));
                        }
                    }
                });

                optionsPanel.add(radioButton);
            }

            card.add(optionsPanel);
            mainContent.add(card);
            mainContent.add(Box.createRigidArea(new Dimension(0, 18)));
        }

        // ── Scroll pane ───────────────────────────────────────────────────────
        JScrollPane quizScroll = new JScrollPane(mainContent);
        quizScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        quizScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        quizScroll.getVerticalScrollBar().setUnitIncrement(12);
        quizScroll.setBorder(null);
        quizScroll.getViewport().setBackground(new Color(245, 241, 232));
        add(quizScroll, BorderLayout.CENTER);

        // ── Bottom button bar ─────────────────────────────────────────────────
        //Making the finished quiz button, adds information into csv file
        JButton finishedQuizButton = new JButton("Finish Quiz");
        finishedQuizButton.setBackground(new Color(218, 190, 90));
        finishedQuizButton.setForeground(Color.BLACK);
        finishedQuizButton.setFont(new Font("SansSerif", Font.BOLD, 13));
        finishedQuizButton.setOpaque(true);
        finishedQuizButton.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        finishedQuizButton.setFocusPainted(false);
        finishedQuizButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        finishedQuizButton.addActionListener(e -> {
            String[] answers = new String[questions.length];
            for (int i = 0; i < questions.length; i++) {
                answers[i] = quizAnswers.getOrDefault(questions[i], ""); //If no answer is clicked, empty
            }
            writeQuizResult(answers);
            resetQuiz();
            recPage.refresh();
            cardLayout.show(pages, "RECOMMENDATIONS");
        });

        // Making a reset button to clear the quiz
        JButton resetButton = new JButton("Reset Quiz");
        resetButton.setBackground(Color.WHITE);
        resetButton.setForeground(new Color(120, 105, 70));
        resetButton.setFont(new Font("SansSerif", Font.BOLD, 13));
        resetButton.setOpaque(true);
        resetButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 195, 155), 1, true),
                new EmptyBorder(9, 23, 9, 23)
        ));
        resetButton.setFocusPainted(false);
        resetButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        resetButton.addActionListener(e -> resetQuiz());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(235, 230, 215));
        buttonPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(210, 200, 175)),
                new EmptyBorder(18, 0, 18, 0)
        ));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        buttonPanel.add(finishedQuizButton);
        buttonPanel.add(resetButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Clears the quiz answers
    public void resetQuiz() {
        quizAnswers.clear();
        for (ButtonGroup group : buttonGroups) {
            group.clearSelection();
        }
        // Reset pill styles back to unselected appearance
        for (ButtonGroup group : buttonGroups) {
            java.util.Enumeration<AbstractButton> elements = group.getElements();
            while (elements.hasMoreElements()) {
                AbstractButton ab = elements.nextElement();
                ab.setOpaque(false);
                ab.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(210, 195, 155), 1, true),
                        new EmptyBorder(6, 14, 6, 14)
                ));
                ab.setFont(new Font("Calibri", Font.PLAIN, 16));
            }
        }
    }

    // Thin gold accent divider
    private JPanel buildGoldDivider() {
        JPanel div = new JPanel();
        div.setMaximumSize(new Dimension(Integer.MAX_VALUE, 2));
        div.setPreferredSize(new Dimension(0, 2));
        div.setBackground(new Color(218, 190, 90, 100));
        div.setAlignmentX(Component.CENTER_ALIGNMENT);
        return div;
    }
}