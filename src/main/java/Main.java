import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        // Reset cart every time the app launches
        Cart.clearCartCSV();

        // Creating main Window
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(new UI());
        frame.setVisible(true);
    }
}