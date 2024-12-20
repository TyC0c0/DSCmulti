package org.example.multiDSC.view;

import lombok.Getter;
import lombok.Setter;
import org.example.multiDSC.model.controllModels.Manager;
import org.example.multiDSC.model.viewModels.SendMailModel;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

/*
Class to configure the view which allows to the users sending emails
@author Alvaro Garcia Lopez
@version 1.1
*/
@Getter
@Setter
public class SendMailView {
    private SendMailModel model;
    private JTextField textField1;
    private JTextField textField2;
    private JButton sendButton;
    private JButton addFileButton;
    private JButton cancelButton;
    private JTextArea textArea1;
    private JLabel label;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JScrollPane filesScrollPane;
    private JScrollPane scrollPane;
    private JPanel mainPanel;
    private JPanel filesPanel;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JPanel buttonPanel;
    private JPanel row1;
    private JPanel row2;
    private JPanel row3;
    JFrame frame;

    public SendMailView(Manager manager, SendMailModel sendMailModel) {

        this.model = sendMailModel;

        frame = new JFrame("Send Mail");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

        row1 = new JPanel(new BorderLayout());
        label1 = new JLabel(model.getText().get(0));
        label1.setFont(label1.getFont().deriveFont(Font.BOLD));
        textField1 = new JTextField();
        row1.add(label1, BorderLayout.WEST);
        row1.add(textField1, BorderLayout.CENTER);

        row2 = new JPanel(new BorderLayout());
        label2 = new JLabel(model.getText().get(1));
        label2.setFont(label2.getFont().deriveFont(Font.BOLD));
        textField2 = new JTextField();
        row2.add(label2, BorderLayout.WEST);
        row2.add(textField2, BorderLayout.CENTER);

        row3 = new JPanel(new BorderLayout());
        label3 = new JLabel(model.getText().get(2));
        label3.setFont(label3.getFont().deriveFont(Font.BOLD));
        row3.add(label3, BorderLayout.WEST);
        row3.add(new JLabel(), BorderLayout.CENTER);

        topPanel.add(row1);
        topPanel.add(row2);
        topPanel.add(row3);

        textArea1 = new JTextArea();
        textArea1.setLineWrap(true);
        textArea1.setWrapStyleWord(true);
        textArea1.setMargin(new Insets(10, 10, 10, 10));
        scrollPane = new JScrollPane(textArea1);

        bottomPanel = new JPanel(new BorderLayout());

        buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        sendButton = new JButton(model.getText().get(3));
        addFileButton = new JButton(model.getText().get(4));
        cancelButton = new JButton(model.getText().get(5));

        sendButton.setBackground(Color.BLACK);
        sendButton.setForeground(Color.WHITE);
        addFileButton.setBackground(Color.BLACK);
        addFileButton.setForeground(Color.WHITE);
        cancelButton.setBackground(Color.BLACK);
        cancelButton.setForeground(Color.WHITE);
        buttonPanel.add(addFileButton);
        buttonPanel.add(sendButton);
        buttonPanel.add(cancelButton);

        filesPanel = new JPanel();
        //filesPanel.setLayout(new WrapLayout(FlowLayout.LEFT)); // Layout personalizado
        filesScrollPane = new JScrollPane(filesPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        filesScrollPane.setPreferredSize(new Dimension(450, 150)); // Ajusta tamaño del panel con scroll

        addFileButton.addActionListener(e -> {
            label = new JLabel("file.txt");
            label.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            filesPanel.add(label);
            filesPanel.revalidate();
            filesPanel.repaint();
        });

        bottomPanel.add(buttonPanel, BorderLayout.NORTH);
        bottomPanel.add(filesScrollPane, BorderLayout.CENTER);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        frame.add(mainPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }
}

   /*
// Review and finish when the logic is completed
/**
 * Personalized layout that allows to the components to adjust automatically, and do a line skip when the available width is passed.
 */

/*
class WrapLayout extends FlowLayout {
    public WrapLayout(int align) {
        super(align);
    }

    @Override
    public Dimension preferredLayoutSize(Container target) {
        return layoutSize(target, true);
    }

    @Override
    public Dimension minimumLayoutSize(Container target) {
        return layoutSize(target, false);
    }

    private Dimension layoutSize(Container target, boolean preferred) {
        synchronized (target.getTreeLock()) {
            int targetWidth = target.getSize().width;
            if (targetWidth == 0) targetWidth = Integer.MAX_VALUE;

            int hgap = getHgap();
            int vgap = getVgap();
            Insets insets = target.getInsets();
            int horizontalInsetsAndGap = insets.left + insets.right + (hgap * 2);
            int maxWidth = targetWidth - horizontalInsetsAndGap;

            Dimension dim = new Dimension(0, 0);
            int rowWidth = 0;
            int rowHeight = 0;

            int nmembers = target.getComponentCount();

            for (int i = 0; i < nmembers; i++) {
                Component m = target.getComponent(i);

                if (m.isVisible()) {
                    Dimension d = preferred ? m.getPreferredSize() : m.getMinimumSize();
                    if (rowWidth + d.width > maxWidth) {
                        addRow(dim, rowWidth, rowHeight);
                        rowWidth = 0;
                        rowHeight = 0;
                    }

                    if (rowWidth > 0) {
                        rowWidth += hgap;
                    }

                    rowWidth += d.width;
                    rowHeight = Math.max(rowHeight, d.height);
                }
            }

            addRow(dim, rowWidth, rowHeight);

            dim.width += horizontalInsetsAndGap;
            dim.height += insets.top + insets.bottom + vgap * 2;

            return dim;
        }
    }

    private void addRow(Dimension dim, int rowWidth, int rowHeight) {
        dim.width = Math.max(dim.width, rowWidth);
        if (dim.height > 0) {
            dim.height += getVgap();
        }
        dim.height += rowHeight;
    }
}
*/
