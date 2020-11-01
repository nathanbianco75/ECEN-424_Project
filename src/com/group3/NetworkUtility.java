package com.group3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.net.URL;

public class NetworkUtility {
    private static final int PORT = 25565; // or whatever we want it to be. this was just a joke.
    private static Socket clientSocket;
    private static PrintWriter writer;
    private static BufferedReader reader;

    //  Open connection and wait for a client to join
    //      Return true if client connected successfully, false if exception occurred
    public static boolean hostClient() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);
            clientSocket = serverSocket.accept();
            writer = new PrintWriter(clientSocket.getOutputStream());
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            System.out.println("Error connecting to a client:");
            e.printStackTrace();
            return false;
        }
        try {
            serverSocket.close();
        } catch (IOException e) {
            System.out.println("Error closing serverSocket:");
            e.printStackTrace();
        }
        return true;
    }

    // Allows host or client to read input from the other
    //      If return is null, there was a fatal error and the connection should be closed.
    public static String readSocket() {
        if (clientSocket == null || clientSocket.isClosed())
            return null;
        String message;
        try {
            message = reader.readLine();
            return message;
        } catch (IOException e) {
            System.out.println("Error reading from clientSocket:");
            e.printStackTrace();
            return null;
        }
    }

    // Allows host or client to send output to the other
    //      If return is false, there was a fatal error and the connection should be closed.
    public static boolean writeSocket(String message) {
        if (clientSocket == null || clientSocket.isClosed())
            return false;
        writer.println(message);
        writer.flush();
        return true;
    }

    public static void disconnect() {
        if (clientSocket != null && !clientSocket.isClosed()) {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.out.println("Error closing clientSocket:");
                e.printStackTrace();
            }
        }
    }

    public static String getLocalIP() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            System.out.println("Error retrieving local IP:");
            e.printStackTrace();
            return "Unknown";
        }
    }

    public static String getExternalIP() {
        try {
            return new BufferedReader(new InputStreamReader(new URL("http://checkip.amazonaws.com").openStream())).readLine();
        } catch (IOException e) {
            System.out.println("Error retrieving external IP:");
            e.printStackTrace();
            return "Unkown";
        }
    }
}
