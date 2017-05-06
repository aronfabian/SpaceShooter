package network;

import main.Controller;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by arons on 2017. 05. 03..
 */
public abstract class Network {
    public Controller controller;
    protected ObjectOutputStream out = null;
    protected ObjectInputStream in = null;
    protected static final int PORT = 8768;


    public Network(Controller controller) {
        this.controller = controller;
    }

    public abstract void connect();

    public abstract void disconnect();

    public abstract void send();
}
