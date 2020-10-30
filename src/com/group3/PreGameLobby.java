package com.group3;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class PreGameLobby extends JFrame {

    private boolean isHost;
    private String name;

    public PreGameLobby(boolean isHost, String name) {
        this.isHost = isHost;
        this.name = name;
        initializeGUI();
    }

    private void initializeGUI() {
        int numPlayers = 2;
        JLabel[] playerNames = new JLabel[numPlayers];
        playerNames[0] = new JLabel(name);
        // TODO: Get player names through network connections
        playerNames[1] = new JLabel("Default player name");

        // TODO: make start disabled until client(s) join the lobby
        JButton start = new JButton("Start");
        if (isHost)
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
        else
            start.setEnabled(false);

        JPanel content = new JPanel();
        for (int i = 0; i < numPlayers; i++) {
            content.add(playerNames[i]);
        }
        if (isHost)
            content.add(new JLabel("Waiting for you to start the game..."));
        else
            content.add(new JLabel("Waiting for host to start the game..."));
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
