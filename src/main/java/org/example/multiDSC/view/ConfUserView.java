/**
 * The ConfUserView class represents a graphical user interface for managing user configurations.
 * It includes functionalities such as modifying user details, applying changes, and canceling edits.
 */
package org.example.multiDSC.view;

import org.example.multiDSC.model.controllModels.Manager;
import org.example.multiDSC.model.viewModels.ConfUserModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ConfUserView extends JFrame {
    private ConfUserModel model;
    private ArrayList<JButton> buttons;
    private ArrayList<JLabel> labels;
    private ArrayList<JTextField> textFields;
    private ArrayList<JButton> modificationButtons;
    private JPanel rightPanel;
    private JPanel buttonsPanel;
    private JButton exitButton;
    private JButton modButton;

    /**
     * Constructs the ConfUserView and initializes the GUI components and layout.
     * The interface includes buttons for modifying user details and applying or canceling changes.
     *
     * @author Ivan Guerrero Romero, Isaac Requena Santiago, Alvaro Garcia
     * @version 2.1
     */


    public ConfUserView(Manager manager, ConfUserModel confUserModel) {
        //EN VEZ DE HACERLE AQUI UN NEW MODEL LE PONES EL THIS.
        this.model = confUserModel;
        //ELIMINA EL MAIN DE ABAJO Y AL FINAL DEL PUBLIC CONFUSER AÑADES SETVISIBLE(TRUE)
        // CAMBIAR TODOS LOS ...MODEL_ES.GETCONFUSERTEXT POR ...MODEL.GETTEXT

        // Initializing lists
        buttons = new ArrayList<>();
        labels = new ArrayList<>();
        textFields = new ArrayList<>();
        modificationButtons = new ArrayList<>();

        // Configure the main panel
        setTitle(confUserModel.getText().get(0));
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
        String[] buttonNames = {confUserModel.getText().get(1), confUserModel.getText().get(2)};
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
        exitButton = new JButton(confUserModel.getText().get(3));
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setBackground(Color.WHITE);
        exitButton.setForeground(Color.BLACK);
        exitButton.setFocusPainted(false);
        exitButton.setFont(new Font("Arial", Font.BOLD, 12));

        leftPanel.add(Box.createVerticalGlue()); // Flexible separator
        leftPanel.add(exitButton);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Lower space

        // Right panel
        rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(7, 2, 5, 5)); // 7 rows, 2 columns
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Margins
        rightPanel.setBackground(Color.LIGHT_GRAY);

        // Create labels and text fields dynamically
        String[] labelsArray = {confUserModel.getText().get(6), confUserModel.getText().get(7), confUserModel.getText().get(8), confUserModel.getText().get(9), confUserModel.getText().get(10), confUserModel.getText().get(11), confUserModel.getText().get(12)};
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
        //manager.getMainController().UserViewAddActionListener();
       // setVisible(true);
    }

    /**
     * Creates the "Apply" and "Cancel" buttons dynamically and configures their actions.
     * "Cancel" clears the text fields, disables them, and hides the modification buttons.
     */
    public void createModificationButtons() {
        if (modificationButtons.isEmpty()) {
            // Names of the buttons to create
            String[] buttonsNames = {model.getText().get(4), model.getText().get(5)};

            // Create and configure dynamically the buttons
            for (String name : buttonsNames) {
                modButton = new JButton(name);
                modButton.setBackground(Color.WHITE);
                modButton.setForeground(Color.BLACK);
                modButton.setFont(new Font("Arial", Font.BOLD, 12));

                modificationButtons.add(modButton);
                buttonsPanel.add(modButton);
            }

            buttonsPanel.setVisible(true); // Show the buttons panel
            buttonsPanel.revalidate();
            buttonsPanel.repaint();
        }
    }

    public ArrayList<JButton> getButtons() {
        return buttons;
    }

    public void setButtons(ArrayList<JButton> buttons) {
        this.buttons = buttons;
    }

    public ArrayList<JTextField> getTextFields() {
        return textFields;
    }

    public void setTextFields(ArrayList<JTextField> textFields) {
        this.textFields = textFields;
    }

    public JButton getExitButton() {
        return exitButton;
    }

    public void setExitButton(JButton exitButton) {
        this.exitButton = exitButton;
    }

    public ArrayList<JButton> getModificationButtons() {
        return modificationButtons;
    }

    public void setModificationButtons(ArrayList<JButton> modificationButtons) {
        this.modificationButtons = modificationButtons;
    }

    public JButton getModButton() {
        return modButton;
    }

    public void setModButton(JButton modButton) {
        this.modButton = modButton;
    }

    public JPanel getButtonsPanel() {
        return buttonsPanel;
    }

    public void setButtonsPanel(JPanel buttonsPanel) {
        this.buttonsPanel = buttonsPanel;
    }
}
