/**
 * The ConfUserView class represents a graphical user interface for managing user configurations.
 * It includes functionalities such as modifying user details, applying changes, and canceling edits.
 */
package org.example.multiDSC.view;

import org.example.multiDSC.model.ConfUserModel_en;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ConfUserView extends JFrame {
    private ConfUserModel_en confUserModelEn;
    private ArrayList<JButton> buttons;
    private ArrayList<JLabel> labels;
    private ArrayList<JTextField> textFields;
    private ArrayList<JButton> modificationButtons;
    private JPanel rightPanel;
    private JPanel buttonsPanel;

    /**
     * Constructs the ConfUserView and initializes the GUI components and layout.
     * The interface includes buttons for modifying user details and applying or canceling changes.
     *
     * @author Ivan Guerrero Romero
     * @version 1.0
     */


    public ConfUserView() {
        confUserModelEn = new ConfUserModel_en();
        // Initializing lists
        buttons = new ArrayList<>();
        labels = new ArrayList<>();
        textFields = new ArrayList<>();
        modificationButtons = new ArrayList<>();

        // Configure the main panel
        setTitle(confUserModelEn.getConfUserText_en().get(0));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400); // Fixed size for all the views
        setLayout(new BorderLayout());

        // Establish margins and general colours
        getContentPane().setBackground(Color.LIGHT_GRAY);

        // Left panel
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(Color.DARK_GRAY);
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Márgenes

        // Create buttons dynamically
        String[] buttonNames = {confUserModelEn.getConfUserText_en().get(1), confUserModelEn.getConfUserText_en().get(2)};
        for (String name : buttonNames) {
            JButton button = new JButton(name);
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setBackground(Color.WHITE);
            button.setForeground(Color.BLACK);
            button.setFocusPainted(false);
            button.setFont(new Font("Arial", Font.BOLD, 12));
            buttons.add(button);
            leftPanel.add(button);
            leftPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Space
        }

        // Create separated the button "exit"
        JButton exitButton = new JButton(confUserModelEn.getConfUserText_en().get(3));
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setBackground(Color.WHITE);
        exitButton.setForeground(Color.BLACK);
        exitButton.setFocusPainted(false);
        exitButton.setFont(new Font("Arial", Font.BOLD, 12));
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Close the app
            }
        });
        leftPanel.add(Box.createVerticalGlue()); // Flexible separator
        leftPanel.add(exitButton);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Lower space

        // Add action to the button "Modify"
        buttons.get(1).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (JTextField textField : textFields) {
                    textField.setEnabled(true); // Activate the text fields
                    textField.setBackground(Color.WHITE);
                }
                createModificationButtons();
                buttons.get(1).setEnabled(false);
            }
        });

        // Right panel
        rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(7, 2, 5, 5)); // 7 rows, 2 columns
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Margins
        rightPanel.setBackground(Color.LIGHT_GRAY);

        // Create labels and text fields dynamically
        String[] labelsArray = {confUserModelEn.getConfUserText_en().get(6), confUserModelEn.getConfUserText_en().get(7), confUserModelEn.getConfUserText_en().get(8), confUserModelEn.getConfUserText_en().get(9), confUserModelEn.getConfUserText_en().get(10), confUserModelEn.getConfUserText_en().get(11), confUserModelEn.getConfUserText_en().get(12)};
        for (String labelName : labelsArray) {
            JLabel fieldLabel = new JLabel(labelName);
            fieldLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            JTextField textField = new JTextField();
            textField.setEnabled(false); // Deactivate in the beginning
            textField.setBackground(Color.GRAY);
            textField.setForeground(Color.BLACK);

            labels.add(fieldLabel);
            textFields.add(textField);

            rightPanel.add(fieldLabel);
            rightPanel.add(textField);
        }

        // Panel for the buttons "Apply" and "Cancel"
        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Center the buttons
        buttonsPanel.setBackground(Color.darkGray);
        buttonsPanel.setVisible(false); // Hide in the beginning

        // Add panels to the main panel
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
    }

    /**
     * Creates the "Apply" and "Cancel" buttons dynamically and configures their actions.
     * "Cancel" clears the text fields, disables them, and hides the modification buttons.
     */
    private void createModificationButtons() {
        if (modificationButtons.isEmpty()) {
            // Names of the buttons to create
            String[] buttonsNames = {confUserModelEn.getConfUserText_en().get(4), confUserModelEn.getConfUserText_en().get(5)};

            // Create and configure dynamically the buttons
            for (String name : buttonsNames) {
                JButton modButton = new JButton(name);
                modButton.setBackground(Color.WHITE);
                modButton.setForeground(Color.BLACK);
                modButton.setFont(new Font("Arial", Font.BOLD, 12));

                // Add functionality to the button "Cancel"
                if (name.equals(confUserModelEn.getConfUserText_en().get(5))) {
                    modButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            for (JButton button : modificationButtons) {
                                buttonsPanel.remove(button);
                                buttons.get(1).setEnabled(true);
                            }
                            modificationButtons.clear();
                            buttonsPanel.setVisible(false); // Hide the button panel
                            for (JTextField textField : textFields) {
                                textField.setEnabled(false); // Deactivate the text fields
                                textField.setBackground(Color.GRAY);
                                textField.setText(""); // Clean the text fields
                            }
                            buttonsPanel.revalidate();
                            buttonsPanel.repaint();
                        }
                    });
                }

                modificationButtons.add(modButton);
                buttonsPanel.add(modButton);
            }

            buttonsPanel.setVisible(true); // Show the buttons panel
            buttonsPanel.revalidate();
            buttonsPanel.repaint();
        }
    }


    public static void main(String[] args) {
        // Create and show the interface
        ConfUserView frame = new ConfUserView();
        frame.setVisible(true);
    }
}
