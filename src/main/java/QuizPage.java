import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

public class QuizPage extends JPanel {
    private final RecommendationPage recPage;
    private final CardLayout cardLayout;
    private JPanel pages;
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

            System.out.println("Quiz information saved!"); //debugging

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setUpQuiz() {
        setLayout(new BorderLayout());
        quizAnswers = new HashMap<>();

        //main panel
        JPanel mainContent = new JPanel();
        mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.Y_AXIS));
        mainContent.setBackground(new Color(245, 241, 232));
        mainContent.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainContent.setAlignmentX(Component.LEFT_ALIGNMENT);

        for (int i = 0; i < questions.length; i++) {
            //i is for the questions
            JLabel nameLabel = new JLabel(questions[i]);
            nameLabel.setFont(new Font("SansSerif", Font.BOLD, 30));
            nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            mainContent.add(nameLabel);

            ButtonGroup group = new ButtonGroup();

            for (int j = 0; j < options[i].length; j++) {
                //j is for the options
                String optionText = options[i][j];

                JRadioButton radioButton = new JRadioButton(optionText); //using radio button so user can only click 1
                radioButton.setAlignmentX(Component.LEFT_ALIGNMENT);

                group.add(radioButton);

                int questionIndex = i;
                radioButton.addActionListener(e -> {
                    quizAnswers.put(questions[questionIndex], radioButton.getText()); //saving answers into Map<String,String>
                });

                mainContent.add(radioButton);
            }

            mainContent.add(Box.createRigidArea(new Dimension(0, 20)));
        }

        //Making the finished quiz button, adds information into csv file
        JButton finishedQuizButton = new JButton("Finish Quiz");
        finishedQuizButton.setBackground(new Color(212, 175, 55));
        finishedQuizButton.setForeground(Color.BLACK);
        finishedQuizButton.setFont(new Font("SansSerif", Font.BOLD, 12));
        finishedQuizButton.setOpaque(true);
        finishedQuizButton.setPreferredSize(new Dimension(120, 25));
        finishedQuizButton.setFocusPainted(false);
        finishedQuizButton.addActionListener(e -> {
            String[] answers = new String[questions.length];
            for (int i = 0; i < questions.length; i++) {
                answers[i] = quizAnswers.getOrDefault(questions[i], ""); //If no answer is clicked, empty
            }
            writeQuizResult(answers);
            recPage.refresh();
            cardLayout.show(pages, "RECOMMENDATIONS");
        });

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
        buttonPanel.setBorder(new EmptyBorder(15, 0, 20, 0));
        JLabel captionLabel = new JLabel("Click the button to see your results!");
        captionLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        captionLabel.setForeground(new Color(212, 175, 55));
        buttonPanel.add(captionLabel);
        buttonPanel.add(finishedQuizButton);
        add(buttonPanel, BorderLayout.SOUTH);

    }
}


