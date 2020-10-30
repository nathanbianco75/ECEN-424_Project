package com.group3;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class EnterName extends JFrame {

    private boolean isHost;

    public EnterName(boolean isHost) {
        this.isHost = isHost;
        initializeGUI();
    }

    private void initializeGUI() {
        JTextField name = new JTextField("Enter Your Name Here");

        JButton accept = new JButton("Accept");
        accept.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PreGameLobby(isHost, name.getText());
                dispose();
            }
        });

        JPanel content = new JPanel();
        content.add(new JLabel("Player Name:"));
        content.add(name);
        content.add(accept);

        add(BorderLayout.CENTER, content);
        setSize(500, 400);
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("Enter Your Name");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                new MainMenu();
            }
        });
        setVisible(true);
    }
}
