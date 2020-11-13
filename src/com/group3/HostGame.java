package com.group3;

import java.awt.BorderLayout;
import javax.swing.*;
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

    public HostGame(String player1Name, String player2Name) {
        super(player1Name, player2Name);
        initializeGUI();
    }

    @Override
    protected void initializeGUI() {
        super.initializeGUI();
        setTitle("Host Game");
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Host clicked next");
                hostCardFlipped = true;
                next.setEnabled(false);
            }
        });
        Thread game = new Thread(new playGame());
        game.start();
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
        NetworkUtility.writeSocket(clientCards.toString());
        NetworkUtility.writeSocket(hostCards.toString());

        System.out.println(clientCards);
        System.out.println(hostCards);

        while(playGame) {
            while(hostCardFlipped == false) {
                System.out.print("");
            }
            System.out.println("Past While Loop!");
            NetworkUtility.writeSocket("Flipped");
            System.out.println("Wrote Flipped!");
            if (NetworkUtility.readSocket() == null)
            {
                dispose();
                NetworkUtility.disconnect();
                JOptionPane.showMessageDialog(new MainMenu(), "Lost connection to opponent.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            System.out.println("Read Socket!");
            clientCardFlipped = true;
            flipCards();
            System.out.println("Cards Flipped!");
            clientCardFlipped = false;
            hostCardFlipped = false;
            if (clientCards.isEmpty() && clientWinPile.isEmpty()) { //Host won
                playGame = false;
                break;
            }
            if (hostCards.isEmpty() && hostWinPile.isEmpty()) { //Client won
                playGame = false;
                break;
            }
            graphicsPanel.repaint();
            try
            {
                Thread.sleep(2000);
            }
            catch(InterruptedException ex)
            {
                Thread.currentThread().interrupt();
            }
            System.out.println("Graphics repainted!");
            next.setEnabled(true);
            //NetworkUtility.writeSocket("NextState");
        }
        next.setEnabled(false);

        if (clientCards.isEmpty() && clientWinPile.isEmpty()) {
            System.out.println("Host won");
            dispose();
            NetworkUtility.disconnect();
            JOptionPane.showMessageDialog(new MainMenu(), "Congratulations, you won!", "You Win!", JOptionPane.PLAIN_MESSAGE);
        }
        else if (hostCards.isEmpty() && hostWinPile.isEmpty()) {
            System.out.println("Client won");
            dispose();
            NetworkUtility.disconnect();
            JOptionPane.showMessageDialog(new MainMenu(), "Sorry, better luck next time!", "You Lose!", JOptionPane.PLAIN_MESSAGE);
        }
        else {
            System.out.println("Error: Game quit but nobody won!");
            dispose();
            NetworkUtility.disconnect();
            JOptionPane.showMessageDialog(new MainMenu(), "Oopsie Oopsie :(", "Uh Oh!", JOptionPane.WARNING_MESSAGE);
        }
    }

    public class playGame implements Runnable {

        public playGame() { }

        public void run() {
            playGame();
        }
    }

}
