package org.example.multiDSC.view;

import lombok.Getter;
import lombok.Setter;
import org.example.multiDSC.controller.databaseConection.ConectionBD;
import org.example.multiDSC.model.controllModels.Manager;
import org.example.multiDSC.model.viewModels.LoginModel;

import javax.swing.*;

/*

    VistaForm - This class is the backend from the Windows VistaForm.form
    who is the window of the login form.

    @author Ramón Reina González, Alvaro Garcia Lopez, Isaac Requena Santiago
    @version 2.0

*/

@Getter
@Setter
public class LoginView extends JDialog {

    private LoginModel model;
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

        this.model = loginModel;

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
        lblWelcome.setText(model.getText().get(1)); // "Welcome to the"
        lblWelcome2.setText(model.getText().get(6));
        lblUsername.setText(model.getText().get(2)); // "Username"
        lblPassword.setText(model.getText().get(3)); // "Password"
        btnOk.setText(model.getText().get(4)); // "Ok"
        btnCancel.setText(model.getText().get(5)); // "Cancel"
        lblDontHaveBeenRegistred.setText(model.getText().get(7));


      //  lblDontHaveBeenRegistred.addMouseListener(new LabelListenerLogin(this, conectionBD));
       // btnOk.addActionListener(new ButtonListenerLogin(btnOk, btnCancel, tfNickname, pfPassword, this));
       // btnCancel.addActionListener(new ButtonListenerLogin(btnOk, btnCancel, tfNickname, pfPassword, this));

    }


}
