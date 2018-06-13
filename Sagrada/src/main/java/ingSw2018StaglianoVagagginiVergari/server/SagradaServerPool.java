package ingSw2018StaglianoVagagginiVergari.server;

import ingSw2018StaglianoVagagginiVergari.server.Controller.ClientHandler;
import ingSw2018StaglianoVagagginiVergari.server.Controller.Controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SagradaServerPool {
    private final ServerSocket serverSocket;
    private final ExecutorService pool;
    private Controller controller;

    public SagradaServerPool(int port, Controller controller) throws IOException {
        serverSocket = new ServerSocket(port);
        pool = Executors.newCachedThreadPool();
        System.out.println("Server socket started! Listening on " + port);
        this.controller=controller;
    }

    public void run() throws IOException {
        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println(">>> New connection " + clientSocket.getRemoteSocketAddress());
            pool.submit(new ClientHandler(clientSocket,controller));
        }
    }

    public void close() throws IOException {
        serverSocket.close();
        pool.shutdown();
    }
}
