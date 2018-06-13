package ingSw2018StaglianoVagagginiVergari.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ClientSocket {


    private final String host;
    private final int port;
    private Socket connection;
    private ObjectInputStream in;
    private ObjectOutputStream out;



    public ClientSocket(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void init() throws IOException {
        connection = new Socket(host, port);
        out = new ObjectOutputStream(connection.getOutputStream());
        in = new ObjectInputStream(connection.getInputStream());
    }

    public void close() throws IOException {
        in.close();
        out.close();
        connection.close();
    }








    public ArrayList<Object> nextResponse() {
        try {

            //ArrayList<Object> tmp =  ((ArrayList<Object>)in.readObject());

            return ((ArrayList<Object>)in.readObject());

            //return  tmp;


        } catch (IOException e) {
            System.err.println("Exception on network - : " + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Wrong deserialization: " + e.getMessage());
        }

        return null;
    }





    public void request(ArrayList<Object> request) {
        try {
            out.writeObject(request);
            out.flush();
        } catch (IOException e) {
            System.err.println("Exception on network: " + e.getMessage());
        }
    }



}

