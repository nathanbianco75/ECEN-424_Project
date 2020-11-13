package com.group3;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.prefs.BackingStoreException;
import java.net.*;
import java.io.*;

public class HostGame extends GameFrame {

    public int clientRank;
    public int hostRank;
    protected boolean clientCardFlipped;
    protected boolean hostCardFlipped;

   /* private Socket          socket   = null;
    private ServerSocket    server   = null;
    private DataInputStream in       = null;
*/


    public HostGame(String player1Name, String player2Name) {
        super(player1Name, player2Name);
        initializeGUI();
    }

    @Override
    protected void initializeGUI() {
        super.initializeGUI();
        setTitle("Host Game");
        next.addActionListener(this);
        Thread game = new Thread(new playGame());
        game.start();/*
        try {
            game.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }
    
    public void playGame() {
        cards = new ArrayList<>();
        for (int i = 2; i < 11; i++) {
            cards.add("clubs_" + i + ".png");
            cards.add("diamonds_" + i + ".png");
            cards.add("hearts_" + i + ".png");
            cards.add("spades_" + i + ".png");
        }
        cards.add("clubs_A.png");
        cards.add("clubs_J.png");
        cards.add("clubs_Q.png");
        cards.add("clubs_K.png");
        cards.add("diamonds_A.png");
        cards.add("diamonds_J.png");
        cards.add("diamonds_Q.png");
        cards.add("diamonds_K.png");
        cards.add("hearts_A.png");
        cards.add("hearts_J.png");
        cards.add("hearts_Q.png");
        cards.add("hearts_K.png");
        cards.add("spades_A.png");
        cards.add("spades_J.png");
        cards.add("spades_Q.png");
        cards.add("spades_K.png");
        Collections.shuffle(cards);
        System.out.println(cards);
        clientCards = new ArrayList<>();
        hostCards = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            clientCards.add(cards.get(i));
            hostCards.add(cards.get(i+26));
        }
        while(true) {
            if(NetworkUtility.readSocket()!=null) {

            }
            clientCardFlipped = true;
            while(!hostCardFlipped);
            flipCards();
            clientCardFlipped = false;
            hostCardFlipped = false;
            if (clientCards.isEmpty() && clientWinPile.isEmpty()) { //Host won
                break;
            }
            if (hostCards.isEmpty() && hostWinPile.isEmpty()) { //Client won
                break;
            }
            graphicsPanel.repaint();
            NetworkUtility.writeSocket("NextState");
        }
    }

    public void flipCards() {
        if (!clientCards.isEmpty() && !hostCards.isEmpty() && !clientWinPile.isEmpty() && !hostWinPile.isEmpty()) {
            clientFlipped = clientCards.get(0).charAt(clientCards.get(0).length()-5);
            hostFlipped = hostCards.get(0).charAt(hostCards.get(0).length()-5);
            topClientCard = clientCards.get(0);
            NetworkUtility.writeSocket(topClientCard);
            topHostCard = hostCards.get(0);
            NetworkUtility.writeSocket(topHostCard);
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
            while (hostRank == clientRank && !clientCards.isEmpty() && !hostCards.isEmpty() && !clientWinPile.isEmpty() && !hostWinPile.isEmpty()) { //war prob infinite
                war = true;
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
                        break;
                    }
                    else if (clientCards.isEmpty()) {
                        clientCards.addAll(clientWinPile);
                        clientWinPile = new ArrayList<>();
                    }
                    if (hostCards.isEmpty() && hostWinPile.isEmpty()) { //Client won
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
                    break;
                }
                else if (clientCards.isEmpty()) {
                    clientCards.addAll(clientWinPile);
                    clientWinPile = new ArrayList<>();
                }
                if (hostCards.isEmpty() && hostWinPile.isEmpty()) { //Client won
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
                NetworkUtility.writeSocket("WAR: " + topClientCard);
                topHostCard = hostCards.get(0);
                NetworkUtility.writeSocket("WAR: " + topHostCard);
                clientCards.remove(0);
                hostCards.remove(0);
            }
            if(hostRank > clientRank) {
                hostWinPile.addAll(warPile);
                warPile = new ArrayList<String>();
            }
            else if(clientRank > hostRank) {
                clientWinPile.addAll(warPile);
                warPile = new ArrayList<String>();
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

    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==next) {
            hostCardFlipped = true;
            NetworkUtility.writeSocket("Flipped");
        }
    }

    public class playGame implements Runnable {

        public playGame() { }

        public void run() {
            playGame();
            if (clientCards.isEmpty() && clientWinPile.isEmpty()) {
                System.out.println("Host won");
                NetworkUtility.writeSocket("Host won");
            }
            else if (hostCards.isEmpty() && hostWinPile.isEmpty()) {
                System.out.println("Client won");
                NetworkUtility.writeSocket("Client won");
            }
        }
    }

}
