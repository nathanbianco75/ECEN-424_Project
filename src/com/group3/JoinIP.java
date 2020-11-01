package com.group3;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class JoinIP extends JFrame {

    public JoinIP() {
        initializeGUI();
    }

    private void initializeGUI() {
        JTextField ip = new JTextField("127.0.0.1");

        JLabel status = new JLabel("");

        JButton connect = new JButton("Connect");
        connect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ip_addr = ip.getText();
                if (ip_addr.equals(""))
                    ip_addr = "127.0.0.1";
                if (NetworkUtility.joinHost(ip_addr)) {
                    dispose();
                    new EnterName(false);
                }
                else
                    status.setText("Status: Error: Connection to host at " + ip_addr + " failed.");
            }
        });

        JPanel content = new JPanel();
        content.add(new JLabel("IP:"));
        content.add(ip);
        content.add(status);
        content.add(connect);

        add(BorderLayout.CENTER, content);
        setSize(500, 400);
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("Enter Host IP");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                new MainMenu();
            }
        });
        setVisible(true);
    }
}
