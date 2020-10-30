package com.group3;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame {

    public MainMenu() {
        initializeGUI();
    }

    private void initializeGUI() {
        JButton joinGame = new JButton("Join Game");
        joinGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new JoinIP();
                dispose();
            }
        });

        JButton hostGame = new JButton("Host Game");
        hostGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new EnterName(true);
                dispose();
            }
        });

        JButton tutorial = new JButton("Tutorial");
        tutorial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tutorial.setEnabled(false);
                new Tutorial(tutorial);
            }
        });

        JPanel content = new JPanel();
        content.add(joinGame);
        content.add(hostGame);
        content.add(tutorial);

        add(BorderLayout.CENTER, content);
        setSize(500, 700);
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("War Game Main Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
