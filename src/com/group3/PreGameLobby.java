package com.group3;

import com.sun.tools.javac.Main;

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

    protected boolean isHost;
    protected String name;
    protected JButton start;
    protected JLabel opponent_name;
    protected JLabel status;

    public PreGameLobby(boolean isHost, String name) {
        this.isHost = isHost;
        this.name = name;
        initializeGUI();
    }

    private void initializeGUI() {
        int numPlayers = 2;
        JLabel[] playerNames = new JLabel[numPlayers];
        playerNames[0] = new JLabel(name);
        playerNames[1] = new JLabel("<Empty>");
        opponent_name = playerNames[1];

        if (isHost)
            status = new JLabel("Waiting on an opponent...");
        else
            status = new JLabel("Waiting for you to Ready up...");

        start = new JButton("");
        if (isHost) {
            start.setEnabled(false);
            start.setText("Start");
        }
        else
            start.setText("Ready");
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isHost) {
                    dispose();
                    if (NetworkUtility.writeSocket("start"))
                        new HostGame();
                    else
                        new MainMenu();
                }
                else {
                    if (NetworkUtility.writeSocket("ready")) {
                        start.setEnabled(false);
                        status.setText("Waiting for the host to start the game...");
                    }
                    else {
                        dispose();
                        new MainMenu();
                    }
                }
            }
        });

        JPanel content = new JPanel();
        for (int i = 0; i < numPlayers; i++) {
            content.add(playerNames[i]);
        }
        content.add(status);
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

        new Thread(new Listener()).start();
    }

    public class Listener implements Runnable {

        public Listener() { }

        public void run() {
            if (isHost) {
                if (NetworkUtility.hostServer())
                    status.setText("Waiting for your opponent to Ready up...");
                else {
                    dispose();
                    new MainMenu();
                    return;
                }
            }

            NetworkUtility.writeSocket(name);
            String response = NetworkUtility.readSocket();
            if (response != null)
                opponent_name.setText(response);
            else {
                dispose();
                new MainMenu();
                return;
            }

            if (isHost) {
                if (NetworkUtility.readSocket() != null) {
                    start.setEnabled(true);
                    status.setText("Waiting for you to start the game...");
                }
                else {
                    dispose();
                    new MainMenu();
                }
            }
            else {
                if (NetworkUtility.readSocket() != null)
                    new ClientGame();
                else
                    new MainMenu();
                dispose();
            }
        }
    }


}
