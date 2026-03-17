import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

public class QuizPage extends JPanel {
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
            {"Red", "Orange", "Yellow", "Green", "Blue", "Purple"},
            {"Bright", "Monotone", "Uniform"},
            {"0-20", "20-50", "50-80", "80+"},
            {"Grunge", "Minimalistic", "Streetwear", "Formal"},
            {"Cotton", "Satin", "Polyester"}
    };
    private Map<String, String> quizAnswers;

    QuizPage(CardLayout cardLayout) {
        this.cardLayout = cardLayout;
        quizAnswers = new HashMap<String, String>();
        setUpQuiz();
    }

    //function writes into the quisResults.csv file (in resource/Quiz)
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
        setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

        //main panel
        JPanel mainContent = new JPanel();
        mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.Y_AXIS));

        mainContent.setBackground(new Color(245, 241, 232));
        mainContent.setBorder(new EmptyBorder(0, 0, 0, 0));
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
        quizAnswers = new HashMap<>();
        JButton finishedQuizButton = new JButton("Finish Quiz");
        finishedQuizButton.addActionListener(e -> {
            String[] answers = new String[questions.length];
            for (int i = 0; i < questions.length; i++) {
                answers[i] = quizAnswers.getOrDefault(questions[i], null); //If no answer is clicked, empty
            }
            writeQuizResult(answers);
        });


        //adding to page:
        add(finishedQuizButton);
        add(mainContent);

    }
}


