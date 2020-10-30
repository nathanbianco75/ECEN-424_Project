package com.group3;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class HostGame extends GameFrame {

    public HostGame() {
        initializeGUI();
    }

    @Override
    protected void initializeGUI() {
        super.initializeGUI();
        setTitle("Host Game");
    }
}