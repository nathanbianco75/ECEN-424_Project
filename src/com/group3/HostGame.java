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

public class HostGame extends GameFrame implements ActionListener{


   /* private Socket          socket   = null;
    private ServerSocket    server   = null;
    private DataInputStream in       = null;
*/


    public HostGame(String player1Name, String player2Name) {
        super(player1Name, player2Name);
        initializeGUI();
    }

//    @Override
    protected void initializeGUI() {
        super.initializeGUI();
        setTitle("Host Game");
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                    System.out.println("Host clicked next");
                    next.setEnabled(false);
                    hostCardFlipped = true;
                    NetworkUtility.writeSocket("Flipped");
            }
        });
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
//            NetworkUtility.writeSocket("clientCards[i]" + clientCards.add(cards.get(i)));
//            NetworkUtility.writeSocket("hostCards[i]" + hostCards.add(cards.get(i)));
        }
        NetworkUtility.writeSocket(clientCards.toString());
        NetworkUtility.writeSocket(hostCards.toString());
        System.out.println(clientCards);
        System.out.println(hostCards);
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
//            next.setEnabled(true);
            NetworkUtility.writeSocket("NextState");
        }
    }


    /*public void actionPerformed(ActionEvent e) {
        if(e.getSource()==next) {
            System.out.println("Host clicked next");
            next.setEnabled(false);
            hostCardFlipped = true;
            NetworkUtility.writeSocket("Flipped");
        }
    }*/

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
