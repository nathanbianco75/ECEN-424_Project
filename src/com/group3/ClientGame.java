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
    public String topClientCard, topHostCard;
    public boolean war = false;
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
        this.next.addActionListener(this);
        setTitle("Client Game");
        while (!brk) {
            if (NetworkUtility.readSocket().startsWith("WAR:")) {//WAR
                war = true;
            } else if (NetworkUtility.readSocket().equals("Client won") || NetworkUtility.readSocket().equals("Host won")) {
                brk = true;
            } else {
                this.topClientCard = NetworkUtility.readSocket();
                this.topHostCard = NetworkUtility.readSocket();
            }
        }

        if (clientCards.isEmpty() && clientWinPile.isEmpty()) {
            System.out.println("Host won");
        }
        else if (hostCards.isEmpty() && hostWinPile.isEmpty()) {
            System.out.println("Client won");
        }

    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==super.next)
            NetworkUtility.writeSocket("Flipped");
    }


}

