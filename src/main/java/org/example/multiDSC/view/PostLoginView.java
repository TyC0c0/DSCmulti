package org.example.multiDSC.view;

import javax.swing.*;

public class PostLoginView {
    private JPanel panel1;
    private JButton FTPButton;
    private JButton MAILButton;
    private JButton CONFUSERButton;
    private JButton EXITButton;

    public static void main(String[] args) {

        JFrame frame = new JFrame("Section Window");
        PostLoginView sectionWindow = new PostLoginView();
        frame.setSize(700, 500);
        frame.setResizable(false);
        frame.setContentPane(sectionWindow.panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(frame);
        frame.setVisible(true);
    }
}
