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
        quizAnswers = new HashMap<>();

        //main panel
        JPanel mainContent = new JPanel();
        mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.Y_AXIS));
        mainContent.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainContent.setBackground(new Color(250, 248, 242)); // lighter cream
        mainContent.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(218, 190, 90), 1, true),
                new EmptyBorder(30, 40, 30, 40)
        ));

        JLabel quizTitle = new JLabel("Style Quiz");
        quizTitle.setFont(new Font("Georgia", Font.BOLD, 32));
        quizTitle.setForeground(new Color(218, 190, 90)); // gold
        quizTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainContent.add(quizTitle);
        mainContent.add(Box.createRigidArea(new Dimension(0, 20)));

        // For each question
        for (int i = 0; i < questions.length; i++) { //i is for the questions
            // Label for the question
            JLabel nameLabel = new JLabel(questions[i]);
            nameLabel.setFont(new Font("Georgia", Font.BOLD, 22));
            nameLabel.setForeground(new Color(90, 80, 70));
            nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            mainContent.add(nameLabel);

            ButtonGroup group = new ButtonGroup();
            buttonGroups.add(group);

            for (int j = 0; j < options[i].length; j++) { //j is for the options
                String optionText = options[i][j];

                // Creating options
                JRadioButton radioButton = new JRadioButton(optionText); //using radio button so user can only click 1
                radioButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                radioButton.setOpaque(false);
                radioButton.setBackground(new Color(0, 0, 0, 0));
                radioButton.setFont(new Font("Calibri", Font.PLAIN, 16));
                radioButton.setBorder(new EmptyBorder(4, 0, 4, 0));

                group.add(radioButton);

                int questionIndex = i;
                radioButton.addActionListener(e -> {
                    quizAnswers.put(questions[questionIndex], radioButton.getText()); //saving answers into Map<String,String>
                });

                mainContent.add(radioButton);
            }


            mainContent.add(Box.createRigidArea(new Dimension(0, 15)));
            JSeparator sep = new JSeparator();
            sep.setMaximumSize(new Dimension(400, 1));
            sep.setForeground(new Color(220, 210, 200));
            mainContent.add(sep);
        }

        //Making the finished quiz button, adds information into csv file
        JButton finishedQuizButton = new JButton("Finish Quiz");
        finishedQuizButton.setBackground(new Color(218, 190, 90));
        finishedQuizButton.setForeground(Color.BLACK);
        finishedQuizButton.setFont(new Font("SansSerif", Font.BOLD, 13));
        finishedQuizButton.setOpaque(true);
        finishedQuizButton.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        finishedQuizButton.setFocusPainted(false);

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
        resetButton.setBackground(new Color(218, 190, 90));
        resetButton.setForeground(Color.BLACK);
        resetButton.setFont(new Font("SansSerif", Font.BOLD, 13));
        resetButton.setOpaque(true);
        resetButton.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        resetButton.setFocusPainted(false);
        resetButton.addActionListener(e -> resetQuiz());

        // scroll pane
        JScrollPane quizScroll = new JScrollPane(mainContent);
        quizScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        quizScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        quizScroll.getVerticalScrollBar().setUnitIncrement(12);
        quizScroll.setBorder(null);
        add(quizScroll, BorderLayout.CENTER);

        // button at bottom
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(245, 241, 232));
        buttonPanel.setBorder(new EmptyBorder(20, 0, 20, 0));
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
    }
}