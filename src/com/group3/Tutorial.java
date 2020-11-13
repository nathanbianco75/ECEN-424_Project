package com.group3;

import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Tutorial extends JFrame {

    public Tutorial(JButton tutorial) {
        super();
        initializeGUI(tutorial);
    }

    private void initializeGUI(JButton tutorial) {
        JLabel text = new JLabel(
        "<html><body>" +
                "Welcome to our game!" + "<br><br>" +
                "<ul>" +
                "<li>Each player is dealt half the deck<br></li>" +
                "<li>Each round both players flip top card by clicking Flip button on the bottom<br></li>" +
                "<li>Both players flipping the same card results in a war<br></li>" +
                "<li>War means both players place 4 cards and whoever has the highest card on top gets all cards<br></li>" +
                "<li>Player with higher card wins<br></li>" +
                "<li>Cards placed in winner’s win pile<br></li>" +
                "<li>Players shuffle their win pile into their hand when out of cards<br></li>" +
                "<li>A player wins when they have all 52 cards<br></li>" +
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
        content.setBorder(new EmptyBorder(5, 5, 5, 5));
        content.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(40, 0, 20, 0);
        content.add(text, gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JPanel buttons = new JPanel(new GridBagLayout());
        gbc.anchor = GridBagConstraints.SOUTH;
        buttons.add(close, gbc);
        gbc.weighty = 1;
        content.add(buttons, gbc);

        add(content);
        setSize(800, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("Tutorial");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                tutorial.setEnabled(true);
            }
        });
        setVisible(true);
    }
}
