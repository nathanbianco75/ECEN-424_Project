package com.group3;

import com.sun.tools.javac.Main;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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

        // this thread will be used to listen for the other player's name and to ready/start
        new Thread(new Listener()).start();
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
                    // this structure will show up a lot - if the read/write succeeds without error,
                    //      then continue with the next part of the game. Else, assume failure and quit.
                    if (NetworkUtility.writeSocket("start"))
                        new HostGame(name, opponent_name.getText());
                    else
                        JOptionPane.showMessageDialog(new MainMenu(), "Lost connection to opponent.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    if (NetworkUtility.writeSocket("ready")) {
                        start.setEnabled(false);
                        status.setText("Waiting for the host to start the game...");
                    }
                    else {
                        dispose();
                        JOptionPane.showMessageDialog(new MainMenu(), "Lost connection to opponent.", "Error", JOptionPane.ERROR_MESSAGE);
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
    }


    // this will be used to listen for the other player's name and to ready/start
    public class Listener implements Runnable {

        public Listener() { }

        public void run() {
            // if is the host, wait for a client to connect
            if (isHost) {
                if (NetworkUtility.hostServer())
                    status.setText("Waiting for your opponent to Ready up...");
                else {
                    dispose();
                    JOptionPane.showMessageDialog(new MainMenu(), "Lost connection to opponent.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // for both client and host, send the other your name and then receive the other's name
            NetworkUtility.writeSocket(name);
            String response = NetworkUtility.readSocket();
            if (response != null)
                opponent_name.setText(response);
            else {
                dispose();
                JOptionPane.showMessageDialog(new MainMenu(), "Lost connection to opponent.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // if the host, wait to receive client's ready-up message
            if (isHost) {
                if (NetworkUtility.readSocket() != null) {
                    start.setEnabled(true);
                    status.setText("Waiting for you to start the game...");
                }
                else {
                    dispose();
                    JOptionPane.showMessageDialog(new MainMenu(), "Lost connection to opponent.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            // else the client, wait to receive the server's start message
            else {
                if (NetworkUtility.readSocket() != null)
                    new ClientGame(name, opponent_name.getText());
                else
                    JOptionPane.showMessageDialog(new MainMenu(), "Lost connection to opponent.", "Error", JOptionPane.ERROR_MESSAGE);
                dispose();
            }
        }
    }


}
