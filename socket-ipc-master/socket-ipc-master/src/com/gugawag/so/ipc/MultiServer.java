package com.gugawag.so.ipc;

import java.net.*;
import java.io.*;
import java.util.Date;

public class MultiServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(6016);
            System.out.println("=== Servidor multiClient iniciado ===");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado: " + clientSocket.getInetAddress() + "-" + clientSocket.getPort());

                // Cria uma nova thread para lidar com o cliente
                Thread clientThread = new ClientHandler(clientSocket);
                clientThread.start();
            }
        } catch (IOException ioe) {
            System.err.println(ioe);
        }
    }
}

class ClientHandler extends Thread {
    private Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            // Envia a data atual para o cliente
            out.println(new Date().toString() + "-Boa noite alunos!");

            // Lê mensagens do cliente e as imprime no console
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println("Cliente disse: " + line);
            }

            // Fecha a conexão com o cliente
            clientSocket.close();
        } catch (IOException e) {
            System.err.println("Erro ao lidar com o cliente: " + e.getMessage());
        }
    }
}
