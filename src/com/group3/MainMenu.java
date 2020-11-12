package com.group3;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame {

    public MainMenu() {
        super();
        NetworkUtility.disconnect();
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
        content.setBorder(new EmptyBorder(10, 10, 10, 10));
        content.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;
        content.add(new JLabel("<html><h1><strong><i>War Card Game</i></strong></h1><hr></html>"), gbc);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(40, 0, 40, 0);
        JPanel buttons = new JPanel(new GridBagLayout());
        buttons.add(joinGame, gbc);
        buttons.add(hostGame, gbc);
        buttons.add(tutorial, gbc);
        gbc.weighty = 1;
        content.add(buttons, gbc);

        add(content);
        setSize(500, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("Main Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
