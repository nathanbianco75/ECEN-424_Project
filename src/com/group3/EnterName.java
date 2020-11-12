package com.group3;

import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class EnterName extends JFrame {

    private boolean isHost;

    public EnterName(boolean isHost) {
        super();
        this.isHost = isHost;
        initializeGUI();
    }

    private void initializeGUI() {
        JTextField name = new JTextField("Enter Your Name Here");

        JButton accept = new JButton("Accept");
        accept.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new PreGameLobby(isHost, name.getText());
            }
        });

        JPanel content = new JPanel();
        content.setBorder(new EmptyBorder(10, 10, 10, 10));
        content.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(40, 0, 40, 0);
        JPanel buttons = new JPanel(new GridBagLayout());
        JPanel names = new JPanel();
        names.add(new JLabel("Player Name:"));
        names.add(name);
        buttons.add(names, gbc);
        buttons.add(accept, gbc);
        gbc.weighty = 1;
        content.add(buttons, gbc);

        add(content);
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
                NetworkUtility.disconnect();
            }
        });
        setVisible(true);
    }
}
