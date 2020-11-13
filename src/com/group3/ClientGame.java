package com.group3;

import java.awt.BorderLayout;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;
import java.net.*;
import java.io.*;

public class ClientGame extends GameFrame implements ActionListener{
    public boolean brk = false;
  /*  private DataInputStream  input   = null;
    private DataOutputStream out     = null;
    private Socket socket            = null;
*/


    public ClientGame(String player1Name, String player2Name) {
        super(player1Name, player2Name);
        initializeGUI();
    }

    @Override
    protected void initializeGUI() {
        super.initializeGUI();
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.out.println("Client clicked next");
                next.setEnabled(false);
                clientCardFlipped = true;
                NetworkUtility.writeSocket("Flipped");
            }
        });
        setTitle("Client Game");
        Thread game = new Thread(new playGame());
        game.start();
//        while (!brk) {
//            String message = NetworkUtility.readSocket();
//            if (message.startsWith("WAR:")) {//WAR
//                war = true;
//            } else if (message.equals("Client won") || message.equals("Host won")) {
//                brk = true;
//            }
//            else if (message.equals("Flipped")) {
//
//            }
//            else if (message.startsWith("topHostCard")){
//                topHostCard = message;
//            }
//            else if (message.startsWith("topClientCard")){
//                topClientCard = message;
//            }
//            graphicsPanel.repaint();
//            next.setEnabled(true);
//        }

        /*if (clientCards.isEmpty() && clientWinPile.isEmpty()) {
            System.out.println("Host won");
        }
        else if (hostCards.isEmpty() && hostWinPile.isEmpty()) {
            System.out.println("Client won");
        }*/

    }

    public void playGame() {
        String s = NetworkUtility.readSocket();
        clientCards = new ArrayList<String> (Arrays.asList(s.substring(1, s.length() - 1).split(", ")));

        s = NetworkUtility.readSocket();
        hostCards = new ArrayList<String> (Arrays.asList(s.substring(1, s.length() - 1).split(", ")));

        System.out.println(clientCards);
        System.out.println(hostCards);

        while(true) {
            if (NetworkUtility.readSocket() != null) {

            }
            hostCardFlipped = true;
            while (!clientCardFlipped) ;
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
        }

        /*while (!brk) {
            String message = NetworkUtility.readSocket();
            if (message.startsWith("WAR:")) {//WAR
                war = true;
            } else if (message.equals("Client won") || message.equals("Host won")) {
                brk = true;
            }
            else if (message.equals("Flipped")) {

            }
            else if (message.startsWith("topHostCard")){
                topHostCard = message;
            }
            else if (message.startsWith("topClientCard")){
                topClientCard = message;
            }
            graphicsPanel.repaint();
            next.setEnabled(true);
        }*/
    }



    /*public void actionPerformed(ActionEvent e) {
        if(e.getSource()==next) {
            System.out.println("Client clicked next");
            next.setEnabled(false);
            NetworkUtility.writeSocket("Flipped");
            clientCardFlipped = true;
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

