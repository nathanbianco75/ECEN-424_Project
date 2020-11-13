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
        next.addActionListener(this);
        setTitle("Client Game");
        while (!brk) {
            String message = NetworkUtility.readSocket();
            if (message.startsWith("WAR:")) {//WAR
                war = true;
            } else if (message.equals("Client won") || message.equals("Host won")) {
                brk = true;
            }
            else if (message.equals("Flipped")) {

            }
            else {
                this.topClientCard = NetworkUtility.readSocket();
                this.topHostCard = NetworkUtility.readSocket();
            }
            graphicsPanel.repaint();
            next.setEnabled(true);
        }

        if (clientCards.isEmpty() && clientWinPile.isEmpty()) {
            System.out.println("Host won");
        }
        else if (hostCards.isEmpty() && hostWinPile.isEmpty()) {
            System.out.println("Client won");
        }

    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==next) {
            System.out.println("Client clicked next");
            next.setEnabled(false);
            NetworkUtility.writeSocket("Flipped");
        }
    }


}

