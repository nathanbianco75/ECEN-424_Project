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

    public ArrayList<String> cards = new ArrayList<String>();
    public ArrayList<String> clientCards = new ArrayList<String>();
    public ArrayList<String> hostCards = new ArrayList<String>();
    public ArrayList<String> clientWinPile = new ArrayList<String>();
    public ArrayList<String> hostWinPile = new ArrayList<String>();
    public ArrayList<String> warPile = new ArrayList<String>();
    public int clientRank;
    public int hostRank;
    public char clientFlipped;
    public char hostFlipped;
    public static String topClientCard, topHostCard;
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
        this.next.addActionListener(this);
        this.playGame();

        if (this.clientCards.isEmpty() && this.clientWinPile.isEmpty()) {
            System.out.println("Host won");
            NetworkUtility.writeSocket("Host won");
        }
        else if (this.hostCards.isEmpty() && this.hostWinPile.isEmpty()) {
            System.out.println("Client won");
            NetworkUtility.writeSocket("Client won");
        }
    }
    
    public void playGame() {
        this.cards = new ArrayList<>();
        for (int i = 2; i < 11; i++) {
            this.cards.add("clubs_" + i + ".png");
            this.cards.add("diamonds_" + i + ".png");
            this.cards.add("hearts_" + i + ".png");
            this.cards.add("spades_" + i + ".png");
        }
        this.cards.add("clubs_A.png");
        this.cards.add("clubs_J.png");
        this.cards.add("clubs_Q.png");
        this.cards.add("clubs_K.png");
        this.cards.add("diamonds_A.png");
        this.cards.add("diamonds_J.png");
        this.cards.add("diamonds_Q.png");
        this.cards.add("diamonds_K.png");
        this.cards.add("hearts_A.png");
        this.cards.add("hearts_J.png");
        this.cards.add("hearts_Q.png");
        this.cards.add("hearts_K.png");
        this.cards.add("spades_A.png");
        this.cards.add("spades_J.png");
        this.cards.add("spades_Q.png");
        this.cards.add("spades_K.png");
        Collections.shuffle(this.cards);
        System.out.println(this.cards);
        this.clientCards = new ArrayList<>();
        this.hostCards = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            this.clientCards.add(this.cards.get(i));
            this.hostCards.add(this.cards.get(i+26));
        }
        while(true) {
            while(NetworkUtility.readSocket()==null) {

            }
            this.clientCardFlipped = true;
            while(!this.hostCardFlipped);
            this.flipCards();
            this.clientCardFlipped = false;
            this.hostCardFlipped = false;
            if (this.clientCards.isEmpty() && this.clientWinPile.isEmpty()) { //Host won
                break;
            }
            if (this.hostCards.isEmpty() && this.hostWinPile.isEmpty()) { //Client won
                break;
            }
            NetworkUtility.writeSocket("NextState");
        }
    }

    public void flipCards() {
        if (!this.clientCards.isEmpty() && !this.hostCards.isEmpty() && !this.clientWinPile.isEmpty() && !this.hostWinPile.isEmpty()) {
            this.clientFlipped = this.clientCards.get(0).charAt(this.clientCards.get(0).length()-5);
            this.hostFlipped = this.hostCards.get(0).charAt(this.hostCards.get(0).length()-5);
            this.topClientCard = this.clientCards.get(0);
            NetworkUtility.writeSocket(this.topClientCard);
            this.topHostCard = this.hostCards.get(0);
            NetworkUtility.writeSocket(this.topHostCard);
            this.getRanks();
            if(this.clientRank > this.hostRank) {
                this.clientWinPile.add(this.clientCards.get(0));
                this.clientWinPile.add(this.hostCards.get(0));
                this.clientCards.remove(0);
                this.hostCards.remove(0);
            }
            else if(this.hostRank > this.clientRank){
                this.hostWinPile.add(this.clientCards.get(0));
                this.hostWinPile.add(this.hostCards.get(0));
                this.clientCards.remove(0);
                this.hostCards.remove(0);
            }
            while (this.hostRank == this.clientRank && !this.clientCards.isEmpty() && !this.hostCards.isEmpty() && !this.clientWinPile.isEmpty() && !this.hostWinPile.isEmpty()) { //war prob infinite
                if (this.clientCards.isEmpty()){
                    this.clientCards.addAll(this.clientWinPile);
                    this.clientWinPile = new ArrayList<>();
                }
                if (this.hostCards.isEmpty()){
                    this.hostCards.addAll(hostWinPile);
                    this.hostWinPile = new ArrayList<>();
                }
                for(int i = 0; i < 3; i++) {
                    if (this.clientCards.isEmpty() && this.clientWinPile.isEmpty()) { //Host won
                        break;
                    }
                    else if (this.clientCards.isEmpty()) {
                        this.clientCards.addAll(this.clientWinPile);
                        this.clientWinPile = new ArrayList<>();
                    }
                    if (this.hostCards.isEmpty() && this.hostWinPile.isEmpty()) { //Client won
                        break;
                    }
                    else if (this.hostCards.isEmpty()) {
                        this.hostCards.addAll(this.hostWinPile);
                        this.hostWinPile = new ArrayList<>();
                    }
                    this.warPile.add(this.clientCards.get(0));
                    this.warPile.add(this.hostCards.get(0));
                    this.clientCards.remove(0);
                    this.hostCards.remove(0);
                }
                if (this.clientCards.isEmpty() && this.clientWinPile.isEmpty()) { //Host won
                    break;
                }
                else if (this.clientCards.isEmpty()) {
                    this.clientCards.addAll(clientWinPile);
                    this.clientWinPile = new ArrayList<>();
                }
                if (this.hostCards.isEmpty() && this.hostWinPile.isEmpty()) { //Client won
                    break;
                }
                else if (this.hostCards.isEmpty()) {
                    this.hostCards.addAll(this.hostWinPile);
                    this.hostWinPile = new ArrayList<>();
                }
                this.clientFlipped = this.clientCards.get(0).charAt(this.clientCards.get(0).length()-5);
                this.hostFlipped = this.hostCards.get(0).charAt(this.hostCards.get(0).length()-5);
                this.getRanks();
                this.warPile.add(this.clientCards.get(0));
                this.warPile.add(this.hostCards.get(0));
                this.topClientCard = clientCards.get(0);
                NetworkUtility.writeSocket("WAR: " + this.topClientCard);
                this.topHostCard = this.hostCards.get(0);
                NetworkUtility.writeSocket("WAR: " + this.topHostCard);
                this.clientCards.remove(0);
                this.hostCards.remove(0);
            }
            if(this.hostRank > this.clientRank) {
                this.hostWinPile.addAll(this.warPile);
                this.warPile = new ArrayList<String>();
            }
            else if(this.clientRank > this.hostRank) {
                this.clientWinPile.addAll(this.warPile);
                this.warPile = new ArrayList<String>();
            }
        }
    }

    public void getRanks() {
        if(this.clientFlipped >= '2' && this.clientFlipped <= '9'){
            this.clientRank = Character.getNumericValue(clientFlipped);
        }
        else {
            switch (this.clientFlipped) {
                case 'A' -> this.clientRank = 14;
                case '0' -> this.clientRank = 10;//10
                case 'J' -> this.clientRank = 11;
                case 'Q' -> this.clientRank = 12;
                case 'K' -> this.clientRank = 13;
            }
        }
        if(this.hostFlipped >= '2' && this.hostFlipped <= '9'){
            this.hostRank = Character.getNumericValue(hostFlipped);
        }
        else {
            switch (this.hostFlipped) {
                case 'A' -> this.hostRank = 14;
                case '0' -> this.hostRank = 10;//10
                case 'J' -> this.hostRank = 11;
                case 'Q' -> this.hostRank = 12;
                case 'K' -> this.hostRank = 13;
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==this.next)
            this.hostCardFlipped = true;
    }

}
