package com.group3;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class PreGameLobby extends JFrame {

    public PreGameLobby(boolean isHost) {
        initializeGUI(isHost);
    }

    private void initializeGUI(boolean isHost) {
        JButton start = new JButton("Start");
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isHost) {
                    new HostGame();
                }
                else {
                    new ClientGame();
                }
                dispose();
            }
        });

        JPanel content = new JPanel();
        content.add(start);

        add(BorderLayout.CENTER, content);
        setSize(500, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("Pre-Game Lobby");
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
