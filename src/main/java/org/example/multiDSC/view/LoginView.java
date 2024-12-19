package org.example.multiDSC.view;

import lombok.Getter;
import lombok.Setter;
import org.example.multiDSC.controller.databaseConection.ConectionBD;
import org.example.multiDSC.model.controllModels.Manager;
import org.example.multiDSC.model.viewModels.LoginModel;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

/*

    VistaForm - This class is the backend from the Windows VistaForm.form
    who is the window of the login form.

    @author Ramón Reina González, Alvaro Garcia Lopez
    @version 1.5

*/

@Getter
@Setter
public class LoginView extends JDialog {

    private LoginModel LoginModel;
    private JTextField tfNickname;
    private JPasswordField pfPassword;
    private JButton btnOk;
    private JButton btnCancel;
    private JPanel loginPanel;
    private JLabel lblUsername;
    private JLabel lblPassword;
    private JLabel lblWelcome;
    private JLabel lblWelcome2;
    private JLabel lblDontHaveBeenRegistred;
    ConectionBD conectionBD;

    public LoginView(Manager manager, LoginModel loginModel) {

        this.LoginModel = loginModel;

        JFrame frame = new JFrame();
        // Instanciar el modelo para obtener textos
        setTitle("Login");
        setContentPane(loginPanel);
        //setMinimumSize(new Dimension(450, 500));
        //setMaximumSize(new Dimension(450, 500));
        setSize(700, 500);
        setResizable(false);
        setModal(true);
        setLocationRelativeTo(frame);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Asignar los textos desde el modelo a los componentes
        lblWelcome.setText(LoginModel.getText().get(1)); // "Welcome to the"
        lblWelcome2.setText(LoginModel.getText().get(6));
        lblUsername.setText(LoginModel.getText().get(2)); // "Username"
        lblPassword.setText(LoginModel.getText().get(3)); // "Password"
        btnOk.setText(LoginModel.getText().get(4)); // "Ok"
        btnCancel.setText(LoginModel.getText().get(5)); // "Cancel"
        lblDontHaveBeenRegistred.setText(LoginModel.getText().get(7));


      //  lblDontHaveBeenRegistred.addMouseListener(new LabelListenerLogin(this, conectionBD));
       // btnOk.addActionListener(new ButtonListenerLogin(btnOk, btnCancel, tfNickname, pfPassword, this));
       // btnCancel.addActionListener(new ButtonListenerLogin(btnOk, btnCancel, tfNickname, pfPassword, this));

    }


}
