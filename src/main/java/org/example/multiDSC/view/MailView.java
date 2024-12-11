package org.example.multiDSC.view;

import javax.swing.*;

public class MailView {
    private JPanel panel1;
    private JButton createNewButton;
    private JScrollBar scrollBar1;
    private JButton refreshButton;
    private JButton backButton;

    public MailView() {
        JFrame frame = new JFrame();
        frame.setTitle("Mail");
        frame.setSize(700, 500);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }
}
