package network;

import gameelement.Bullet;
import gameelement.Craft;
import main.Controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by arons on 2017. 05. 03..
 */
public class NetworkServer extends Network {

    private ServerSocket serverSocket = null;
    private Socket clientSocket = null;

    public NetworkServer(Controller controller) {
        super(controller);
    }

    private class ReceiverThread implements Runnable {

        public void run() {
            try {
                System.out.println("Waiting for Client");
                clientSocket = serverSocket.accept();
                controller.startTimers();
                System.out.println("Client connected.");
            } catch (IOException e) {
                System.err.println("Accept failed.");
                disconnect();
                return;
            }

            try {
                out = new ObjectOutputStream(clientSocket.getOutputStream());
                in = new ObjectInputStream(clientSocket.getInputStream());
                out.flush();
            } catch (IOException e) {
                System.err.println("Error while getting streams.");
                disconnect();
                return;
            }

            try {

                while (true) {
                    ClientState clientState = (ClientState) in.readObject();
                    controller.setClientState(clientState);
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                System.err.println("Client disconnected!");
                controller.stopTimers();
            } finally {
                disconnect();
            }
        }
    }

    @Override
    public void connect() {
        disconnect();
        try {
            serverSocket = new ServerSocket(PORT);

            Thread rec = new Thread(new ReceiverThread());
            rec.start();
        } catch (IOException e) {
            System.err.println("Could not listen on port: 7868.");
        }
    }

    @Override
    public void disconnect() {
        try {
            controller.stopTimers();
            if (out != null)
                out.close();
            if (in != null)
                in.close();
            if (clientSocket != null)
                clientSocket.close();
            if (serverSocket != null)
                serverSocket.close();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void send() {
        if (out == null)
            return;
        System.out.println("Sending data to Client");
        try {
            //TODO write controller's lists (crafts, asteroids ...)
            out.writeObject(controller.getCrafts());
            out.writeObject(controller.getBullets());
            out.writeObject(controller.getAsteroids());
            out.writeObject(controller.getUfos());
            out.writeObject(controller.getGifts());
            out.flush();
            out.reset();
        } catch (IOException ex) {
            System.err.println("Send error.");
            System.err.println(ex.getMessage());
        }
    }


}
