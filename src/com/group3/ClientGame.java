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

public class ClientGame extends GameFrame {
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
                clientCardFlipped = true;
                next.setEnabled(false);
            }
        });
        setTitle("Client Game");
        Thread game = new Thread(new playGame());
        game.start();
    }

    public void playGame() {
        String s = NetworkUtility.readSocket();
        clientCards = new ArrayList<String> (Arrays.asList(s.substring(1, s.length() - 1).split(", ")));
        s = NetworkUtility.readSocket();
        hostCards = new ArrayList<String> (Arrays.asList(s.substring(1, s.length() - 1).split(", ")));

        System.out.println(clientCards);
        System.out.println(hostCards);

        while(playGame) {
            while (clientCardFlipped == false) {
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
            hostCardFlipped = true;
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
            System.out.println("Graphics repainted!");
            try
            {
                Thread.sleep(2000);
            }
            catch(InterruptedException ex)
            {
                Thread.currentThread().interrupt();
            }
            next.setEnabled(true);
        }
        next.setEnabled(false);

        if (clientCards.isEmpty() && clientWinPile.isEmpty()) {
            System.out.println("Host won");
            dispose();
            NetworkUtility.disconnect();
            JOptionPane.showMessageDialog(new MainMenu(), "Sorry, better luck next time!", "You Lose!", JOptionPane.PLAIN_MESSAGE);
        }
        else if (hostCards.isEmpty() && hostWinPile.isEmpty()) {
            System.out.println("Client won");
            dispose();
            NetworkUtility.disconnect();
            JOptionPane.showMessageDialog(new MainMenu(), "Congratulations, you won!", "You Win!", JOptionPane.PLAIN_MESSAGE);
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

