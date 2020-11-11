package com.group3;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;

public class GameFrame extends JFrame {

    private GraphicsPanel graphicsPanel;
    protected String player1Name;
    protected String player2Name;


    public GameFrame(String player1Name, String player2Name) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        initializeGUI();
    }

    protected void initializeGUI() {
        graphicsPanel = new GraphicsPanel();

        JButton next = new JButton("Next");

        JButton quit = new JButton("Quit");
        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainMenu();
                dispose();
            }
        });

        JPanel content = new JPanel();
        content.add(next);
        content.add(quit);

        add(BorderLayout.CENTER, graphicsPanel);
        add(BorderLayout.SOUTH, content);
        setSize(1600, 900);
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("Game Frame");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                new MainMenu();
            }
        });
        setVisible(true);
        graphicsPanel.repaint();
    }

    private class GraphicsPanel extends JPanel {

        private final Font SMALL_FONT = new Font("Helvetica", Font.PLAIN, 15);
        private final Font BIG_FONT = new Font("Helvetica", Font.PLAIN, 30);

        GraphicsPanel() {
            super();
        }

        public Image getImage(String imageName)
        {
            URL url = getClass().getResource("/images/" + imageName);
            System.out.println(url.getPath());
            ImageIcon ii = new ImageIcon(url);
            return ii.getImage();
        }

        public void paint(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            // Draw Background
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, this.getWidth(), this.getHeight());

            g2d.setColor(Color.BLACK);
            // Draw Player 1 Info
            int hPadding = 50, vPadding = 50;
            int x = 0, y = 0;


            // Draw Player 2 Info
            x = 0; y = 0;
            g2d.setFont(BIG_FONT);
            g2d.drawString(player2Name, x += hPadding, y += vPadding);
            g2d.drawImage(getImage("back.png"), x += hPadding*5, y += vPadding, this);
            g2d.setFont(SMALL_FONT);

        }

    }
}
