package org.example.multiDSC.view;

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
    @version 1.6

*/

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

    }

    public void showWarning(String message) {
        JOptionPane.showMessageDialog(this, message, "Warning", JOptionPane.WARNING_MESSAGE);
    }

    public JTextField getTfNickname() {
        return tfNickname;
    }

    public void setTfNickname(JTextField tfNickname) {
        this.tfNickname = tfNickname;
    }

    public JPasswordField getPfPassword() {
        return pfPassword;
    }

    public void setPfPassword(JPasswordField pfPassword) {
        this.pfPassword = pfPassword;
    }

    public JButton getBtnOk() {
        return btnOk;
    }

    public void setBtnOk(JButton btnOk) {
        this.btnOk = btnOk;
    }

    public JButton getBtnCancel() {
        return btnCancel;
    }

    public void setBtnCancel(JButton btnCancel) {
        this.btnCancel = btnCancel;
    }

    public JPanel getLoginPanel() {
        return loginPanel;
    }

    public void setLoginPanel(JPanel loginPanel) {
        this.loginPanel = loginPanel;
    }

    public JLabel getLblUsername() {
        return lblUsername;
    }

    public void setLblUsername(JLabel lblUsername) {
        this.lblUsername = lblUsername;
    }

    public JLabel getLblPassword() {
        return lblPassword;
    }

    public void setLblPassword(JLabel lblPassword) {
        this.lblPassword = lblPassword;
    }

    public JLabel getLblWelcome() {
        return lblWelcome;
    }

    public void setLblWelcome(JLabel lblWelcome) {
        this.lblWelcome = lblWelcome;
    }

    public JLabel getLblWelcome2() {
        return lblWelcome2;
    }

    public void setLblWelcome2(JLabel lblWelcome2) {
        this.lblWelcome2 = lblWelcome2;
    }

    public JLabel getLblDontHaveBeenRegistred() {
        return lblDontHaveBeenRegistred;
    }

    public void setLblDontHaveBeenRegistred(JLabel lblDontHaveBeenRegistred) {
        this.lblDontHaveBeenRegistred = lblDontHaveBeenRegistred;
    }
}
