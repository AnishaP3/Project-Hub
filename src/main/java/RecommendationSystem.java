import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RecommendationSystem {
    public List<Products> recommendProducts(ClassLoader classLoader) {
        List<String> answers = readQuizResultsCSV(classLoader);
        List<Products> recommendations = new ArrayList<>();
        List<Products> allProducts = Products.readProductCSV(classLoader);
        String lifestyle = answers.get(0);
        String colour = answers.get(1);
        String tone = answers.get(2);
        String budget = answers.get(3);
        String style = answers.get(4);
        String fabric =  answers.get(5);

        for (Products p : allProducts) {
            int points = 0;
            int answeredQuestions = 0;

            // Recommendations based on lifestyle
            if (!lifestyle.isEmpty()) {
                if(lifestyle.equalsIgnoreCase("Sporty") && (p.getCategory().equalsIgnoreCase("Track Suit") ||
                        p.getCategory().equalsIgnoreCase("Hoodie") || p.getCategory().equalsIgnoreCase("T-Shirt"))){
                    points += 3;
                }
                if (lifestyle.equalsIgnoreCase("Business") && ((p.getCategory().equalsIgnoreCase("Blazer")) ||
                        (p.getCategory().equalsIgnoreCase("Sweater") && p.getName().equalsIgnoreCase("Preppy Sweater")))) {
                    points += 3;
                }

                if (lifestyle.equalsIgnoreCase("Artsy") && (p.getCategory().equalsIgnoreCase("Sweater") ||
                        p.getCategory().equalsIgnoreCase("T-Shirt") || p.getCategory().equalsIgnoreCase("Dress") || p.getCategory().equalsIgnoreCase("Jacket"))) {
                    points += 3;
                }
                answeredQuestions ++;
            }

            // Recommendations based on colour
            if (!colour.isEmpty()) {
                for (String c : p.getColor().split(",")) {
                    if (c.trim().equalsIgnoreCase(colour)) {
                        points += 2;
                        break;
                    }
                }
                answeredQuestions ++;
            }

            // Recommendations based on Tone
            if (!tone.isEmpty()) {
                if (tone.equalsIgnoreCase("Bright") && (p.getCategory().equalsIgnoreCase("Sweater") ||
                        p.getCategory().equalsIgnoreCase("Dress") || p.getCategory().equalsIgnoreCase("T-Shirt") || p.getCategory().equalsIgnoreCase("Shirt"))) {
                    points += 2;
                }

                if (tone.equalsIgnoreCase("Monotone") && ((p.getCategory().equalsIgnoreCase("Jacket") && p.getName().equalsIgnoreCase("Jacket")) ||
                        p.getCategory().equalsIgnoreCase("Coat") ||
                        p.getCategory().equalsIgnoreCase("Blazer"))) {
                    points += 2;
                }

                if (tone.equalsIgnoreCase("Uniform") && (p.getCategory().equalsIgnoreCase("Hoodie") ||
                        p.getCategory().equalsIgnoreCase("Shirt"))) {
                    points += 2;
                }
                answeredQuestions ++;
            }

            // Recommendations based on budget
            double[] br = budgetRange(budget);
            if (!budget.isEmpty()) {
                if (p.getPrice() >= br[0] && p.getPrice() <= br[1]) {
                    points += 2;
                }
                answeredQuestions ++;
            }

            // Recommendations based on style
            if (!style.isEmpty()) {
                if (style.equalsIgnoreCase("Minimalistic") && (p.getCategory().equalsIgnoreCase("T-Shirt") ||
                        p.getCategory().equalsIgnoreCase("Hoodie"))) {
                    points += 2;
                }

                if (style.equalsIgnoreCase("Streetwear") && (p.getCategory().equalsIgnoreCase("Shirt") ||
                        p.getCategory().equalsIgnoreCase("Jacket") || p.getCategory().equalsIgnoreCase("Hoodie") || p.getCategory().equalsIgnoreCase("T-Shirt"))) {
                    points += 2;
                }

                if (style.equalsIgnoreCase("Formal") && (p.getCategory().equalsIgnoreCase("Coat") ||
                        p.getCategory().equalsIgnoreCase("Blazer"))) {
                    points += 2;
                }
                answeredQuestions ++;
            }

            // Recommendations based on fabric
            if (!fabric.isEmpty()) {
                for (String m : p.getMaterial().split(",")) {
                    if (m.trim().equalsIgnoreCase(fabric)) {
                        points += 3;
                        break;
                    }
                }
                answeredQuestions ++;
            }

            int threshold;
            if (answeredQuestions <= 3) {
                threshold = 2;
            } else {
                threshold = 4;
            }

            if (points >= threshold) recommendations.add(p);
        }

        // Remove duplicates
        List<Products> uniqueRecommendations = new ArrayList<>();
        for (Products p : recommendations) {
            if (!uniqueRecommendations.contains(p)) {
                uniqueRecommendations.add(p);
            }
        }
        return uniqueRecommendations;

    }

    public List<String> readQuizResultsCSV(ClassLoader classLoader) {
        List<String> quizResults = new ArrayList<>();
        File file = new File("resources/Quiz/quizResults.csv");

        // Reading each line until the last line
        try (Scanner scanner = new Scanner(file)) {
            String lastLine = "";
            while (scanner.hasNextLine()) {
                lastLine = scanner.nextLine();
            }

            // Splitting the last line to get each value
            String[] parts = lastLine.split(",", -1);
            quizResults.clear();

            // Assigning each value to a variable
            String lifestyle = "";
            String colourPreference = "";
            String tones = "";
            String budget = "";
            String style = "";
            String fabricPreference = "";

            lifestyle = parts.length > 0 ? parts[0].trim() : "";
            colourPreference = parts.length > 1 ? parts[1].trim() : "";
            tones = parts.length > 2 ? parts[2].trim() : "";
            budget = parts.length > 3 ? parts[3].trim() : "";
            style = parts.length > 4 ? parts[4].trim() : "";
            fabricPreference = parts.length > 5 ? parts[5].trim() : "";

            // Adding the values to a list
            quizResults.add(lifestyle);
            quizResults.add(colourPreference);
            quizResults.add(tones);
            quizResults.add(budget);
            quizResults.add(style);
            quizResults.add(fabricPreference);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error reading products from CSV file", e);
        }
        return quizResults;
    }

    public double[] budgetRange(String budget) {
        switch (budget) {
            case "0-20" : return new double[] {0, 20};
            case "20-50" : return new double[] {20, 50};
            case "50-80" : return new double[] {50, 80};
            case "80+" : return new double[] {80, Double.MAX_VALUE};
            default : return new double[] {0, Double.MAX_VALUE};
        }
    }
}

