package org.example.multiDSC.view;

import org.example.multiDSC.model.VistaFormModel_en;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

/*

    VistaForm - This class is the backend from the Windows VistaForm.form
    who is the window of the login form.

    @author Ramón Reina González, Alvaro Garcia Lopez
    @version 1.3

*/

public class LoginView extends JDialog {
    private JTextField tfNickname;
    private JPasswordField pfPassword;
    private JButton btnOk;
    private JButton btnCancel;
    private JPanel loginPanel;
    private JLabel lblUsername;
    private JLabel lblPassword;
    private JLabel lblWelcome;
    private JLabel lblWelcome2;

    public LoginView() {
        JFrame frame = new JFrame();
        // Instanciar el modelo para obtener textos
        VistaFormModel_en vistaFormModelEn = new VistaFormModel_en();
        setTitle("Login");
        setContentPane(loginPanel);
        //setMinimumSize(new Dimension(450, 500));
        //setMaximumSize(new Dimension(450, 500));
        setSize(700, 500);
        setResizable(false);
        setModal(true);
        setLocationRelativeTo(frame);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);


        vistaFormModelEn.VistaForm(); // Inicializa el array con los textos

        // Asignar los textos desde el modelo a los componentes
        lblWelcome.setText(vistaFormModelEn.getLoginText().get(1)); // "Welcome to the"
        lblWelcome2.setText(vistaFormModelEn.getLoginText().get(6));
        lblUsername.setText(vistaFormModelEn.getLoginText().get(2)); // "Username"
        lblPassword.setText(vistaFormModelEn.getLoginText().get(3)); // "Password"
        btnOk.setText(vistaFormModelEn.getLoginText().get(4)); // "Ok"
        btnCancel.setText(vistaFormModelEn.getLoginText().get(5)); // "Cancel"

        // Listener ok button
        btnOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = tfNickname.getText();
                String password = String.valueOf(pfPassword.getPassword());
                System.out.println("Username: " + email);
                System.out.println("Password: " + password);
            }
        });

        // Listener cancel button
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setVisible(true);
    }

    public class User {
        String username;
        String password;
    }

    public User authentificateUser() {
        User user = null;

        // DB connection

        final String db_url = "jdbc:mysql://localhost";
        final String username = "root";
        final String password = "";

        try {
            Connection conn = DriverManager.getConnection(db_url, username, password);

            Statement statement = conn.createStatement();
            String sql = "";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    public static void main(String[] args) {
        LoginView vista = new LoginView();
//        User user = vista.authentificateUser();
//
//        if (user != null) {
//            System.out.println("");
//            System.out.println("");
//            System.out.println("");
//            System.out.println("");
//        }
//        else {
//            System.out.println("Authentification canceled...");
//        }
    }
}