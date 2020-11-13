package com.group3;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;


public class GameFrame extends JFrame {

    protected GraphicsPanel graphicsPanel;
    protected String player1Name;
    protected String player2Name;
    protected final int FRAME_WIDTH = 1850;
    protected final int FRAME_HEIGHT = 1000;
    protected JButton next;
    public ArrayList<String> cards = new ArrayList<String>();
    public ArrayList<String> clientCards = new ArrayList<String>();
    public ArrayList<String> hostCards = new ArrayList<String>();
    public ArrayList<String> clientWinPile = new ArrayList<String>();
    public ArrayList<String> hostWinPile = new ArrayList<String>();
    public ArrayList<String> warPile = new ArrayList<String>();
    public ArrayList<String> clientwarPile = new ArrayList<String>();
    public ArrayList<String> hostwarPile = new ArrayList<String>();
    public int clientRank;
    public int hostRank;
    public char clientFlipped;
    public char hostFlipped;
    public String topClientCard, topHostCard;
    public boolean war = false;
    protected boolean clientCardFlipped;
    protected boolean hostCardFlipped;
    protected String clientWonWar = "";
    protected String hostWonWar = "";
    protected boolean drawWarCards = false;
    protected boolean playGame = true;



    public GameFrame(String player1Name, String player2Name) {
        super();
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        //initializeGUI();
    }

    protected void initializeGUI() {
        graphicsPanel = new GraphicsPanel();

        next = new JButton("Flip");

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
                NetworkUtility.disconnect();
                new MainMenu();
            }
        });
        setVisible(true);
        graphicsPanel.repaint();
    }

    public void flipCards() {
        drawWarCards = false;
        if ((!clientCards.isEmpty() || !clientWinPile.isEmpty()) && (!hostCards.isEmpty() || !hostWinPile.isEmpty())) {
            if (clientCards.isEmpty()){
                clientCards.addAll(clientWinPile);
                clientWinPile = new ArrayList<>();
            }
            if (hostCards.isEmpty()){
                hostCards.addAll(hostWinPile);
                hostWinPile = new ArrayList<>();
            }
            clientFlipped = clientCards.get(0).charAt(clientCards.get(0).length()-5);
            hostFlipped = hostCards.get(0).charAt(hostCards.get(0).length()-5);
            topClientCard = clientCards.get(0);
            //NetworkUtility.writeSocket("topClientCard: " + topClientCard);
            topHostCard = hostCards.get(0);
            //NetworkUtility.writeSocket("topHostCard" + topHostCard);
            getRanks();
            if(clientRank > hostRank) {
                clientWinPile.add(clientCards.get(0));
                clientWinPile.add(hostCards.get(0));
                clientCards.remove(0);
                hostCards.remove(0);
            }
            else if(hostRank > clientRank){
                hostWinPile.add(clientCards.get(0));
                hostWinPile.add(hostCards.get(0));
                clientCards.remove(0);
                hostCards.remove(0);
            }
            while (playGame == true && hostRank == clientRank && !clientCards.isEmpty() && !hostCards.isEmpty() && !clientWinPile.isEmpty() && !hostWinPile.isEmpty()) { //war prob infinite
                war = true;
                drawWarCards = true;
                if (clientCards.isEmpty()){
                    clientCards.addAll(clientWinPile);
                    clientWinPile = new ArrayList<>();
                }
                if (hostCards.isEmpty()){
                    hostCards.addAll(hostWinPile);
                    hostWinPile = new ArrayList<>();
                }
                for(int i = 0; i < 3; i++) {
                    if (clientCards.isEmpty() && clientWinPile.isEmpty()) { //Host won
                        playGame = false;
                        break;
                    }
                    else if (clientCards.isEmpty()) {
                        clientCards.addAll(clientWinPile);
                        clientWinPile = new ArrayList<>();
                    }
                    if (hostCards.isEmpty() && hostWinPile.isEmpty()) { //Client won
                        playGame = false;
                        break;
                    }
                    else if (hostCards.isEmpty()) {
                        hostCards.addAll(hostWinPile);
                        hostWinPile = new ArrayList<>();
                    }
                    warPile.add(clientCards.get(0));
                    warPile.add(hostCards.get(0));
                    clientwarPile.add(clientCards.get(0));
                    hostwarPile.add(hostCards.get(0));
                    clientCards.remove(0);
                    hostCards.remove(0);
                }
                if (clientCards.isEmpty() && clientWinPile.isEmpty()) { //Host won
                    playGame = false;
                    break;
                }
                else if (clientCards.isEmpty()) {

                    clientCards.addAll(clientWinPile);
                    clientWinPile = new ArrayList<>();
                }
                if (hostCards.isEmpty() && hostWinPile.isEmpty()) { //Client won
                    playGame = false;
                    break;
                }
                else if (hostCards.isEmpty()) {
                    hostCards.addAll(hostWinPile);
                    hostWinPile = new ArrayList<>();
                }
                clientFlipped = clientCards.get(0).charAt(clientCards.get(0).length()-5);
                hostFlipped = hostCards.get(0).charAt(hostCards.get(0).length()-5);
                getRanks();
                warPile.add(clientCards.get(0));
                warPile.add(hostCards.get(0));
                clientwarPile.add(clientCards.get(0));
                hostwarPile.add(hostCards.get(0));
                topClientCard = clientCards.get(0);
                //NetworkUtility.writeSocket("WAR: " + topClientCard);
                topHostCard = hostCards.get(0);
                //NetworkUtility.writeSocket("WAR: " + topHostCard);
                clientCards.remove(0);
                hostCards.remove(0);
            }
            if(hostRank > clientRank) {
                hostWinPile.addAll(warPile);
                warPile = new ArrayList<String>();
                hostWonWar = "You won the war!";
                clientWonWar = "You lost the war!";
            }
            else if(clientRank > hostRank) {
                clientWinPile.addAll(warPile);
                warPile = new ArrayList<String>();
                hostWonWar = "You lost the war!";
                clientWonWar = "You won the war!";
            }
        }
    }

    public void getRanks() {
        if(clientFlipped >= '2' && clientFlipped <= '9'){
            clientRank = Character.getNumericValue(clientFlipped);
        }
        else {
            switch (clientFlipped) {
                case 'A' -> clientRank = 14;
                case '0' -> clientRank = 10;//10
                case 'J' -> clientRank = 11;
                case 'Q' -> clientRank = 12;
                case 'K' -> clientRank = 13;
            }
        }
        if(hostFlipped >= '2' && hostFlipped <= '9'){
            hostRank = Character.getNumericValue(hostFlipped);
        }
        else {
            switch (hostFlipped) {
                case 'A' -> hostRank = 14;
                case '0' -> hostRank = 10;//10
                case 'J' -> hostRank = 11;
                case 'Q' -> hostRank = 12;
                case 'K' -> hostRank = 13;
            }
        }
    }

    protected class GraphicsPanel extends JPanel {

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
            int x = 0, y = FRAME_HEIGHT - Y_PAD*4;
            g2d.setFont(BIG_FONT);
            g2d.drawString(player1Name, x += X_PAD, y - (int)(Y_PAD*2.0));
            y -= CARD_HEIGHT + (int)(Y_PAD*1.1);
            g2d.setFont(SMALL_FONT);
            x += X_PAD*12;
            if (hostCards.size() > 0) {
                g2d.drawImage(getImage("back.png"), x, y, this);
                g2d.drawRect(x, y, CARD_WIDTH, CARD_HEIGHT);
                g2d.drawString("Hand (" + hostCards.size() + ")", x + CARD_WIDTH - (int)(X_PAD*4.5), y + Y_PAD + CARD_HEIGHT);
            }
            if (hostCards.size() > 1) {
                g2d.drawImage(getImage("back.png"), x + X_PAD, y, this);
                g2d.drawRect(x + X_PAD, y, CARD_WIDTH, CARD_HEIGHT);
            }
            if (hostCards.size() > 2) {
                g2d.drawImage(getImage("back.png"), x + X_PAD*2, y, this);
                g2d.drawRect(x + X_PAD*2, y, CARD_WIDTH, CARD_HEIGHT);
            }
            x += CARD_WIDTH + X_PAD*3;
            if (topHostCard != null) {
                g2d.drawImage(getImage(topHostCard), x, y, this);
                g2d.drawRect(x, y, CARD_WIDTH, CARD_HEIGHT);
                g2d.drawString("Flip Card", x + X_PAD*3, y + Y_PAD + CARD_HEIGHT);
            }
            x += CARD_WIDTH + X_PAD*4;
            if (drawWarCards) {
                if (hostwarPile.size() > 2) {
                    g2d.drawImage(getImage("back.png"), x, y - Y_PAD*2, this);
                    g2d.drawRect(x, y - Y_PAD*2, CARD_WIDTH, CARD_HEIGHT);
                }
                if (hostwarPile.size() > 1) {
                    g2d.drawImage(getImage("back.png"), x, y - Y_PAD, this);
                    g2d.drawRect(x, y - Y_PAD, CARD_WIDTH, CARD_HEIGHT);
                }
                if (hostwarPile.size() > 0) {
                    g2d.drawImage(getImage(hostwarPile.get(hostwarPile.size()-1)), x, y, this);
                    g2d.drawRect(x, y, CARD_WIDTH, CARD_HEIGHT);
                    g2d.drawString("War (" + hostwarPile.size() + ")", x + X_PAD*3, y + Y_PAD + CARD_HEIGHT);
                    g2d.drawString(hostWonWar, x + X_PAD*2, y + Y_PAD*2 + CARD_HEIGHT);
                }
            }
            x += CARD_WIDTH + X_PAD*4;
            if (hostWinPile.size() > 2) {
                g2d.drawImage(getImage(hostWinPile.get(hostWinPile.size()-3)), x, y, this);
                g2d.drawRect(x, y, CARD_WIDTH, CARD_HEIGHT);
            }
            if (hostWinPile.size() > 1) {
                g2d.drawImage(getImage(hostWinPile.get(hostWinPile.size()-2)), x + X_PAD*2, y, this);
                g2d.drawRect(x + X_PAD*2, y, CARD_WIDTH, CARD_HEIGHT);
            }
            if (hostWinPile.size() > 0) {
                g2d.drawImage(getImage(hostWinPile.get(hostWinPile.size()-1)), x + X_PAD*4, y, this);
                g2d.drawRect(x + X_PAD*4, y, CARD_WIDTH, CARD_HEIGHT);
                g2d.drawString("Win Pile (" + hostWinPile.size() + ")", x + CARD_WIDTH - X_PAD*5, y + Y_PAD + CARD_HEIGHT);
            }

            // Draw Player 2 Info
            x = 0; y = Y_PAD;
            g2d.setFont(BIG_FONT);
            g2d.drawString(player2Name, x += X_PAD, (y += (int)(Y_PAD*1.5)) + (int)(Y_PAD*1.8));
            g2d.setFont(SMALL_FONT);
            x += X_PAD*12;
            if (clientCards.size() > 0) {
                g2d.drawImage(getImage("back.png"), x, y, this);
                g2d.drawRect(x, y, CARD_WIDTH, CARD_HEIGHT);
                g2d.drawString("Hand (" + clientCards.size() + ")", x + CARD_WIDTH - (int)(X_PAD*4.5), y - (int)(Y_PAD*0.5));
            }
            if (clientCards.size() > 1) {
                g2d.drawImage(getImage("back.png"), x + X_PAD, y, this);
                g2d.drawRect(x + X_PAD, y, CARD_WIDTH, CARD_HEIGHT);
            }
            if (clientCards.size() > 2) {
                g2d.drawImage(getImage("back.png"), x + X_PAD*2, y, this);
                g2d.drawRect(x + X_PAD*2, y, CARD_WIDTH, CARD_HEIGHT);
            }
            x += CARD_WIDTH + X_PAD*3;
            if (topClientCard != null) {
                g2d.drawImage(getImage(topClientCard), x, y, this);
                g2d.drawRect(x, y, CARD_WIDTH, CARD_HEIGHT);
                g2d.drawString("Flip Card", x + X_PAD*3, y - (int)(Y_PAD*0.5));
            }
            x += CARD_WIDTH + X_PAD*4;
            if (drawWarCards) {
                if (clientwarPile.size() > 2) {
                    g2d.drawImage(getImage("back.png"), x, y + Y_PAD*2, this);
                    g2d.drawRect(x, y + Y_PAD*2, CARD_WIDTH, CARD_HEIGHT);
                }
                if (clientwarPile.size() > 1) {
                    g2d.drawImage(getImage("back.png"), x, y + Y_PAD, this);
                    g2d.drawRect(x, y + Y_PAD, CARD_WIDTH, CARD_HEIGHT);
                }
                if (clientwarPile.size() > 0) {
                    g2d.drawImage(getImage(clientwarPile.get(clientwarPile.size()-1)), x, y, this);
                    g2d.drawRect(x, y, CARD_WIDTH, CARD_HEIGHT);
                    g2d.drawString("War (" + clientwarPile.size() + ")", x + X_PAD*3, y - (int)(Y_PAD*0.5));
                    g2d.drawString(clientWonWar, x + X_PAD*2, y - Y_PAD*2);
                }
            }
            x += CARD_WIDTH + X_PAD*4;
            if (clientWinPile.size() > 2) {
                g2d.drawImage(getImage(clientWinPile.get(clientWinPile.size()-3)), x, y, this);
                g2d.drawRect(x, y, CARD_WIDTH, CARD_HEIGHT);
            }
            if (clientWinPile.size() > 1) {
                g2d.drawImage(getImage(clientWinPile.get(clientWinPile.size()-2)), x + X_PAD*2, y, this);
                g2d.drawRect(x + X_PAD*2, y, CARD_WIDTH, CARD_HEIGHT);
            }
            if (clientWinPile.size() > 0) {
                g2d.drawImage(getImage(clientWinPile.get(clientWinPile.size()-1)), x + X_PAD*4, y, this);
                g2d.drawRect(x + X_PAD*4, y, CARD_WIDTH, CARD_HEIGHT);
                g2d.drawString("Win Pile (" + clientWinPile.size() + ")", x + CARD_WIDTH - X_PAD*5, y - (int)(Y_PAD*0.5));
            }
        }

    }

}
