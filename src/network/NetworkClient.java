package network;

import gameelement.*;
import main.Controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.BatchUpdateException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by arons on 2017. 05. 03..
 */
public class NetworkClient extends Network {

    private String serverIp;
    private Socket socket = null;

    public NetworkClient(Controller controller, String serverIp) {
        super(controller);
        this.serverIp = serverIp;
    }

    private class ReceiverThread implements Runnable {

        public void run() {
            System.out.println("Waiting for points...");
            try {
                while (true) {
                    List<Craft> crafts = (List<Craft>) in.readObject();
                    List<Bullet> bullets = (List<Bullet>) in.readObject();
                    List<Asteroid> asteroids = (List<Asteroid>) in.readObject();
                    List<Ufo> ufos = (List<Ufo>) in.readObject();
                    List<Gift> gifts = (List<Gift>) in.readObject();
                    controller.setCrafts(crafts);
                    controller.setBullets(bullets);
                    controller.setAsteroids(asteroids);
                    controller.setUfos(ufos);
                    controller.setGifts(gifts);
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                System.err.println("Server disconnected!");
            } finally {
                disconnect();
            }
        }
    }


    @Override
    public void connect() {
        disconnect();
        try {
            //socket = new Socket(serverIp, PORT);
            socket = new Socket(InetAddress.getLocalHost(), PORT);

            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            out.flush();

            Thread rec = new Thread(new ReceiverThread());
            rec.start();
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host");
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection. ");
        }
    }

    @Override
    public void disconnect() {
        try {
            if (out != null)
                out.close();
            if (in != null)
                in.close();
            if (socket != null)
                socket.close();
        } catch (IOException ex) {
            System.err.println("Error while closing conn.");
        }
    }

    @Override
    public void send() {
        if (out == null)
            return;
        System.out.println("Sending ClientState to Server");

        try {
            ClientState clientState = new ClientState(controller.isRightPressed(), controller.isLeftPressed(), controller.isSpacePressed());
            out.writeObject(clientState);
            out.flush();
            out.reset(); // ez a sor sokat ér! 4 órámba került mire rájöttem, hogy a java cache-eli az objektumokat és ezért küldte mindig ugyanazt
        } catch (IOException ex) {
            System.err.println("Send error.");
        }
    }


}
