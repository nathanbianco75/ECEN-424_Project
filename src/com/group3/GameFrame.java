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
    protected final int FRAME_WIDTH = 1920;
    protected final int FRAME_HEIGHT = 1080;


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
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
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

        private final Font SMALL_FONT = new Font("Helvetica", Font.PLAIN, 18);
        private final Font BIG_FONT = new Font("Helvetica", Font.PLAIN, 30);
        private final int CARD_WIDTH = 243;
        private final int CARD_HEIGHT = 340;
        private final int X_PAD = (int)(FRAME_WIDTH/64);
        private final int Y_PAD = (int)(FRAME_HEIGHT/36);

        GraphicsPanel() {
            super();
        }

        public Image getImage(String imageName)
        {
            URL url = getClass().getResource("/images/" + imageName);
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
            int x = 0, y = 0;


            // Draw Player 2 Info
            x = 0; y = 0;
            g2d.setFont(BIG_FONT);
            g2d.drawString(player2Name, x += X_PAD, y += (int)(Y_PAD*3/2));
            g2d.setFont(SMALL_FONT);
            // if (hand.length() > 0) {
                g2d.drawImage(getImage("back.png"), x += X_PAD*6, y, this);
                g2d.drawRect(x, y, CARD_WIDTH, CARD_HEIGHT);
                g2d.drawString("Hand", x + CARD_WIDTH - (int)(X_PAD*4.5), y - (int)(Y_PAD/2));
            // }
            // if (hand.length() > 1) {
                g2d.drawImage(getImage("back.png"), x + X_PAD, y, this);
                g2d.drawRect(x + X_PAD, y, CARD_WIDTH, CARD_HEIGHT);
            // }
            // if (hand.length() > 2) {
                g2d.drawImage(getImage("back.png"), x + X_PAD*2, y, this);
                g2d.drawRect(x + X_PAD*2, y, CARD_WIDTH, CARD_HEIGHT);
            // }
            // if (flipCard != null) {
                g2d.drawImage(getImage("hearts_7.png"), x += CARD_WIDTH + X_PAD*4, y, this);
                g2d.drawRect(x, y, CARD_WIDTH, CARD_HEIGHT);
                g2d.drawString("Flip Card", x + X_PAD*3, y - (int)(Y_PAD/2));
            // }
            // if (warTopCard != null) {
                g2d.drawImage(getImage("clubs_K.png"), x += CARD_WIDTH + X_PAD*2, y, this);
                g2d.drawRect(x, y, CARD_WIDTH, CARD_HEIGHT);
                g2d.drawString("War", x + X_PAD*3, y - (int)(Y_PAD/2));
            // }
            // if (hand.length() > 0) {
                g2d.drawImage(getImage("diamonds_A.png"), x += CARD_WIDTH + X_PAD*2, y, this);
                g2d.drawRect(x, y, CARD_WIDTH, CARD_HEIGHT);
                g2d.drawString("Win Pile", x + CARD_WIDTH - X_PAD*5, y - (int)(Y_PAD/2));
            // }
            // if (hand.length() > 1) {
                g2d.drawImage(getImage("clubs_2.png"), x + X_PAD, y, this);
                g2d.drawRect(x + X_PAD, y, CARD_WIDTH, CARD_HEIGHT);
            // }
            // if (hand.length() > 2) {
                g2d.drawImage(getImage("hearts_4.png"), x + X_PAD*2, y, this);
                g2d.drawRect(x + X_PAD*2, y, CARD_WIDTH, CARD_HEIGHT);
            // }
        }

    }
}
