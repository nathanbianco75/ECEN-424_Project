package com.group3;

import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Tutorial extends JFrame {

    public Tutorial(JButton tutorial) {
        super();
        initializeGUI(tutorial);
    }

    private void initializeGUI(JButton tutorial) {
        JLabel text = new JLabel(
        "<html><body>" +
                "Welcome to our game!" + "<br><br>" +
                "TODO" +
            "</body></html>"
        );

        JButton close = new JButton("Close");
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                tutorial.setEnabled(true);
            }
        });

        JPanel content = new JPanel();
        content.setBorder(new EmptyBorder(10, 10, 10, 10));
        content.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(40, 0, 20, 0);
        content.add(text, gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JPanel buttons = new JPanel(new GridBagLayout());
        gbc.anchor = GridBagConstraints.SOUTH;
        buttons.add(close, gbc);
        gbc.weighty = 1;
        content.add(buttons, gbc);

        add(content);
        setSize(500, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("Tutorial");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}
