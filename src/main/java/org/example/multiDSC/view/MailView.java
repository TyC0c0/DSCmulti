package org.example.multiDSC.view;

import org.example.multiDSC.model.MainViewModel;

import javax.swing.*;

public class MailView {
    private JPanel panel1;
    private JButton createNewButton;
    private JScrollBar scrollBar1;
    private JPanel panelLeft;
    private JLabel jlabel1;
    private JLabel jlabel2;
    private JLabel jlabel3;
    private JLabel jlabel4;
    private JLabel jlabel5;
    private JLabel jlabel6;
    private JButton refreshButton;
    private JButton backButton;

    public MailView() {
        JFrame frame = new JFrame();
        frame.setTitle("Mail");
        frame.setSize(700, 500);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        MainViewModel model = new MainViewModel();
        model.MailView();

        createNewButton.setText(model.getMailText_en().get(0));
        jlabel1.setText(model.getMailText_en().get(1)); // Inbox
        jlabel2.setText(model.getMailText_en().get(2));
        jlabel3.setText(model.getMailText_en().get(3));
        jlabel4.setText(model.getMailText_en().get(4));
        jlabel5.setText(model.getMailText_en().get(5));
        jlabel6.setText(model.getMailText_en().get(6)); // Trash bin
        refreshButton.setText(model.getMailText_en().get(7));
        backButton.setText(model.getMailText_en().get(8));
    }

    public static void main(String[] args) {
        MailView mailView = new MailView();
    }
}