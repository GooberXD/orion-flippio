package View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.io.IOException;

public class QuizTakingView extends JFrame {

    // --- Main Layout Components ---
    private JPanel mainContainer;
    private CardLayout cardLayout;

    // --- Screen 1: Quiz View Components ---
    private JPanel quizPanel;
    private JLabel questionLabel;
    private CustomOptionButton[] options; // Custom rounded buttons
    private ButtonGroup optionsGroup;
    private RoundedButton btnPrev, btnNext, btnSubmit;

    // --- Screen 2: Confirmation View Components ---
    private JPanel confirmPanel;
    private RoundedButton btnConfirmYes, btnConfirmNo;

    // --- Screen 3: Result View Components ---
    private JPanel resultPanel;
    private RoundedButton btnExit;

    // --- Colors from Screenshots ---
    private final Color BG_DARK = new Color(28, 28, 36);      // Dark Background
    private final Color BG_GREEN = new Color(105, 240, 174);  // Success Background
    private final Color TEXT_WHITE = Color.WHITE;
    private final Color OPTION_BLUE = new Color(165, 180, 252);
    private final Color OPTION_GREEN = new Color(118, 255, 163); // Specific green card
    private final Color OPTION_RED = new Color(255, 138, 128);   // Specific red card
    private final Color BTN_PURPLE = new Color(209, 196, 233);   // Nav buttons
    private final Color BTN_YES_GREEN = new Color(156, 204, 165);
    private final Color BTN_NO_RED = new Color(255, 128, 128);
    private final Color BTN_EXIT_DARK = new Color(30, 30, 35);

    // Font
    private Font mainFont;

    public QuizTakingView() {
        // Window Setup
        setTitle("Flippio - Quiz");
        setSize(900, 500); // Widened to match landscape aspect ratio of screenshots
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Load Font (Montserrat or fallback)
        try {
            mainFont = new Font("Montserrat", Font.PLAIN, 18);
        } catch (Exception e) {
            mainFont = new Font("SansSerif", Font.PLAIN, 18);
        }

        // Setup CardLayout to swap screens
        cardLayout = new CardLayout();
        mainContainer = new JPanel(cardLayout);
        add(mainContainer);

        // Initialize Screens
        initQuizScreen();
        initConfirmationScreen();
        initResultScreen();

        // Show first screen
        cardLayout.show(mainContainer, "QUIZ");
    }

    // =========================================================================
    // SCREEN 1: THE QUIZ INTERFACE
    // =========================================================================
    private void initQuizScreen() {
        quizPanel = new JPanel(new BorderLayout());
        quizPanel.setBackground(BG_DARK);

        // 1. TOP: Question
        JPanel topPanel = new JPanel(new GridBagLayout());
        topPanel.setBackground(BG_DARK);
        topPanel.setPreferredSize(new Dimension(800, 150));

        questionLabel = new JLabel("What is the powerhouse of the cell?", SwingConstants.CENTER);
        questionLabel.setFont(mainFont.deriveFont(Font.PLAIN, 28f));
        questionLabel.setForeground(TEXT_WHITE);
        topPanel.add(questionLabel);
        quizPanel.add(topPanel, BorderLayout.NORTH);

        // 2. CENTER: Options (Horizontal Cards)
        JPanel centerWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        centerWrapper.setBackground(BG_DARK);

        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridLayout(1, 4, 20, 0)); // 1 Row, 4 Cols, 20px gap
        optionsPanel.setBackground(BG_DARK);
        optionsPanel.setBorder(new EmptyBorder(0, 20, 20, 20));

        options = new CustomOptionButton[4];
        optionsGroup = new ButtonGroup();

        // Colors to match the specific screenshot design (Blue, Blue, Green, Red)
        Color[] cardColors = {OPTION_BLUE, OPTION_BLUE, OPTION_GREEN, OPTION_RED};

        for (int i = 0; i < 4; i++) {
            options[i] = new CustomOptionButton("Option " + (i + 1), cardColors[i]);
            optionsGroup.add(options[i]);
            optionsPanel.add(options[i]);
        }

        centerWrapper.add(optionsPanel);
        quizPanel.add(centerWrapper, BorderLayout.CENTER);

        // 3. BOTTOM: Navigation
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 30));
        navPanel.setBackground(BG_DARK);

        btnPrev = new RoundedButton("Prev", BTN_PURPLE, Color.BLACK);
        btnNext = new RoundedButton("Next", BTN_PURPLE, Color.BLACK);
        btnSubmit = new RoundedButton("Submit", BTN_PURPLE, Color.BLACK);

        navPanel.add(btnPrev);
        navPanel.add(btnNext);
        navPanel.add(btnSubmit);
        quizPanel.add(navPanel, BorderLayout.SOUTH);

        // Logic for Submit Button (View Transition)
        btnSubmit.addActionListener(e -> {
            // "You can only click submit if all questions are answered"
            // For this specific view, we check if an option is selected.
            if (getSelectedOptionIndex() == -1) {
                JOptionPane.showMessageDialog(this, "Please select an answer before submitting.", "Answer Required", JOptionPane.WARNING_MESSAGE);
            } else {
                cardLayout.show(mainContainer, "CONFIRM");
            }
        });

        mainContainer.add(quizPanel, "QUIZ");
    }

    // =========================================================================
    // SCREEN 2: CONFIRMATION MODAL
    // =========================================================================
    private void initConfirmationScreen() {
        confirmPanel = new JPanel(new GridBagLayout());
        confirmPanel.setBackground(BG_DARK);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 40, 10);

        JLabel confirmLabel = new JLabel("Would you like to submit?");
        confirmLabel.setFont(mainFont.deriveFont(Font.PLAIN, 32f));
        confirmLabel.setForeground(TEXT_WHITE);
        confirmPanel.add(confirmLabel, gbc);

        // Button Container
        JPanel btnContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        btnContainer.setOpaque(false);

        btnConfirmYes = new RoundedButton("Yes", BTN_YES_GREEN, Color.BLACK);
        btnConfirmNo = new RoundedButton("No", BTN_NO_RED, Color.BLACK);

        btnContainer.add(btnConfirmYes);
        btnContainer.add(btnConfirmNo);

        gbc.insets = new Insets(0, 0, 0, 0);
        confirmPanel.add(btnContainer, gbc);

        // Logic
        btnConfirmNo.addActionListener(e -> cardLayout.show(mainContainer, "QUIZ"));
        btnConfirmYes.addActionListener(e -> cardLayout.show(mainContainer, "RESULT"));

        mainContainer.add(confirmPanel, "CONFIRM");
    }

    // =========================================================================
    // SCREEN 3: CONGRATULATIONS / RESULT
    // =========================================================================
    private void initResultScreen() {
        resultPanel = new JPanel(new GridBagLayout());
        resultPanel.setBackground(BG_GREEN);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 50, 10);

        JLabel congratsLabel = new JLabel("Congratulations! You've finished it");
        congratsLabel.setFont(mainFont.deriveFont(Font.PLAIN, 32f));
        congratsLabel.setForeground(Color.BLACK);
        resultPanel.add(congratsLabel, gbc);

        btnExit = new RoundedButton("Exit", BTN_EXIT_DARK, Color.WHITE);
        btnExit.addActionListener(e -> System.exit(0)); // Exit Application

        gbc.insets = new Insets(0, 0, 0, 0);
        resultPanel.add(btnExit, gbc);

        mainContainer.add(resultPanel, "RESULT");
    }

    // =========================================================================
    // PUBLIC METHODS (API FOR CONTROLLER)
    // =========================================================================
    public void setQuestionText(String text) {
        questionLabel.setText("<html><body style='text-align: center'>" + text + "</body></html>");
    }

    public void setOptions(String[] choices) {
        for (int i = 0; i < choices.length && i < options.length; i++) {
            options[i].setText(choices[i]);
            options[i].setSelected(false);
        }
    }

    public int getSelectedOptionIndex() {
        for (int i = 0; i < options.length; i++) {
            if (options[i].isSelected()) {
                return i;
            }
        }
        return -1;
    }

    public void setSelectedOption(int index) {
        if (index >= 0 && index < options.length) {
            options[index].setSelected(true);
        }
    }

    public JButton getBtnNext() {
        return btnNext;
    }

    public JButton getBtnPrev() {
        return btnPrev;
    }

    // Note: getBtnSubmit() listener is now handled internally for the view flow,
    // but the controller can still add logic to it if needed.
    public JButton getBtnSubmit() {
        return btnSubmit;
    }


    // =========================================================================
    // CUSTOM UI COMPONENTS (INNER CLASSES)
    // =========================================================================

    /**
     * Custom Toggle Button for Quiz Options (The Colorful Cards)
     */
    private class CustomOptionButton extends JToggleButton {
        private Color baseColor;
        private Color selectedBorderColor = new Color(145, 70, 255); // Purple border like image

        public CustomOptionButton(String text, Color bg) {
            super(text);
            this.baseColor = bg;
            setFocusPainted(false);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFont(mainFont.deriveFont(Font.PLAIN, 18f));
            setForeground(Color.BLACK);
            setPreferredSize(new Dimension(200, 150)); // Large cards
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Shape
            int arc = 20;
            RoundRectangle2D.Float shape = new RoundRectangle2D.Float(2, 2, getWidth() - 4, getHeight() - 4, arc, arc);

            // Background
            g2.setColor(baseColor);
            g2.fill(shape);

            // Selection Border
            if (isSelected()) {
                g2.setStroke(new BasicStroke(4f));
                g2.setColor(selectedBorderColor);
                g2.draw(shape);
            }

            // Text
            FontMetrics fm = g2.getFontMetrics();
            Rectangle stringBounds = fm.getStringBounds(getText(), g2).getBounds();
            int x = (getWidth() - stringBounds.width) / 2;
            int y = (getHeight() - stringBounds.height) / 2 + fm.getAscent();

            g2.setColor(getForeground());
            g2.drawString(getText(), x, y);

            g2.dispose();
        }
    }

    /**
     * Rounded Pill Button for Navigation
     */
    private class RoundedButton extends JButton {
        private Color bgColor;
        private Color textColor;

        public RoundedButton(String text, Color bg, Color txt) {
            super(text);
            this.bgColor = bg;
            this.textColor = txt;
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setFont(mainFont.deriveFont(Font.PLAIN, 16f));
            setPreferredSize(new Dimension(160, 50));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (getModel().isPressed()) {
                g2.setColor(bgColor.darker());
            } else {
                g2.setColor(bgColor);
            }

            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30); // 30px radius for pill shape

            g2.setColor(textColor);
            FontMetrics fm = g2.getFontMetrics();
            Rectangle stringBounds = fm.getStringBounds(getText(), g2).getBounds();
            int x = (getWidth() - stringBounds.width) / 2;
            int y = (getHeight() - stringBounds.height) / 2 + fm.getAscent();
            g2.drawString(getText(), x, y);

            g2.dispose();
        }
    }
}
