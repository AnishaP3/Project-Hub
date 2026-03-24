import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * QuizPopup - Displays a stylish popup on app launch inviting users to take the style quiz.
 * Call QuizPopup.show(parentComponent, cardLayout, pages) from UI after building pages.
 */
public class QuizPopUp {

    /**
     * Shows the quiz invitation popup after a short delay.
     *
     * @param parent     The parent component (used to center the dialog)
     * @param cardLayout The app's CardLayout so the button can navigate to QUIZ
     * @param pages      The JPanel holding all pages
     */
    public static void show(Component parent, CardLayout cardLayout, JPanel pages) {
        // Slight delay so the main window appears first
        Timer timer = new Timer(800, e -> displayPopup(parent, cardLayout, pages));
        timer.setRepeats(false);
        timer.start();
    }

    private static void displayPopup(Component parent, CardLayout cardLayout, JPanel pages) {
        // ── Custom dialog ────────────────────────────────────────────────────────
        JDialog dialog = new JDialog(
                SwingUtilities.getWindowAncestor(parent),
                "Discover Your Style",
                Dialog.ModalityType.APPLICATION_MODAL
        );
        dialog.setUndecorated(true);
        dialog.setSize(480, 320);
        dialog.setLocationRelativeTo(parent);
        dialog.setBackground(new Color(0, 0, 0, 0));

        // ── Root panel with rounded look ────────────────────────────────────────
        JPanel root = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Dark semi-transparent background
                g2.setColor(new Color(20, 18, 14));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 24, 24);
                // Gold accent stripe at top
                g2.setColor(new Color(218, 190, 90));
                g2.fillRoundRect(0, 0, getWidth(), 6, 6, 6);
                g2.fillRect(0, 3, getWidth(), 3); // square off the bottom of stripe
                g2.dispose();
            }
        };
        root.setOpaque(false);
        root.setBorder(new EmptyBorder(28, 36, 28, 36));

        // ── Sparkle / star icon label ────────────────────────────────────────────
        JLabel icon = new JLabel("✦", SwingConstants.CENTER);
        icon.setFont(new Font("SansSerif", Font.PLAIN, 36));
        icon.setForeground(new Color(218, 190, 90));
        icon.setBorder(new EmptyBorder(0, 0, 6, 0));

        // ── Headline ─────────────────────────────────────────────────────────────
        JLabel headline = new JLabel("Discover Your Perfect Style", SwingConstants.CENTER);
        headline.setFont(new Font("Georgia", Font.BOLD, 22));
        headline.setForeground(new Color(245, 238, 210));

        // ── Sub-text ──────────────────────────────────────────────────────────────
        JLabel sub = new JLabel(
                "<html><div style='text-align:center;'>Answer 6 quick questions and we'll recommend<br>"
                        + "outfits curated just for <i>you</i>.</div></html>",
                SwingConstants.CENTER
        );
        sub.setFont(new Font("SansSerif", Font.PLAIN, 14));
        sub.setForeground(new Color(180, 170, 150));
        sub.setBorder(new EmptyBorder(8, 0, 22, 0));

        // ── Buttons ───────────────────────────────────────────────────────────────
        JButton takeQuizBtn = new JButton("Take the Style Quiz  →");
        takeQuizBtn.setFont(new Font("SansSerif", Font.BOLD, 15));
        takeQuizBtn.setBackground(new Color(218, 190, 90));
        takeQuizBtn.setForeground(new Color(20, 18, 14));
        takeQuizBtn.setFocusPainted(false);
        takeQuizBtn.setBorder(new EmptyBorder(12, 28, 12, 28));
        takeQuizBtn.setOpaque(true);
        takeQuizBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        // Hover effect
        takeQuizBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                takeQuizBtn.setBackground(new Color(240, 210, 100));
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                takeQuizBtn.setBackground(new Color(218, 190, 90));
            }
        });
        takeQuizBtn.addActionListener(e -> {
            dialog.dispose();
            cardLayout.show(pages, "QUIZ");
        });

        JButton skipBtn = new JButton("Maybe later");
        skipBtn.setFont(new Font("SansSerif", Font.PLAIN, 13));
        skipBtn.setForeground(new Color(140, 130, 110));
        skipBtn.setBackground(new Color(20, 18, 14));
        skipBtn.setBorderPainted(false);
        skipBtn.setFocusPainted(false);
        skipBtn.setOpaque(false);
        skipBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        skipBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                skipBtn.setForeground(new Color(218, 190, 90));
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                skipBtn.setForeground(new Color(140, 130, 110));
            }
        });
        skipBtn.addActionListener(e -> dialog.dispose());

        // ── Button row ────────────────────────────────────────────────────────────
        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 0));
        btnRow.setOpaque(false);
        btnRow.add(takeQuizBtn);
        btnRow.add(skipBtn);

        // ── Center text block ─────────────────────────────────────────────────────
        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setOpaque(false);
        icon.setAlignmentX(Component.CENTER_ALIGNMENT);
        headline.setAlignmentX(Component.CENTER_ALIGNMENT);
        sub.setAlignmentX(Component.CENTER_ALIGNMENT);
        center.add(icon);
        center.add(headline);
        center.add(sub);
        center.add(btnRow);

        root.add(center, BorderLayout.CENTER);
        dialog.setContentPane(root);
        dialog.setVisible(true);
    }
}