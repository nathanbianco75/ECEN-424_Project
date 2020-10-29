package com.group3;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class EnterName extends JFrame {

    public EnterName(boolean isHost) {
        initializeGUI(isHost);
    }

    private void initializeGUI(boolean isHost) {
        JButton accept = new JButton("Accept");
        accept.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PreGameLobby(isHost);
                dispose();
            }
        });

        JPanel content = new JPanel();
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
