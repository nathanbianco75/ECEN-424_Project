package com.group3;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Tutorial extends JFrame {

    public Tutorial(JButton tutorial) {
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
        content.add(text);
        content.add(close);

        add(BorderLayout.CENTER, content);
        setSize(500, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("Tutorial");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}
