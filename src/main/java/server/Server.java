package server;

import model.Message;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private int port;
    private int clientId = 0;
    private final List<Client> clientList = new ArrayList<>();
    private boolean stop = true;

    private ServerListeners serverListeners;

    public Server(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        ServerSocket server  = new ServerSocket(port);
        stop = false;
        while (!stop){
           Socket socket =  server.accept();
           DataInputStream dis = new DataInputStream(socket.getInputStream());
           DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
           Client client = new Client(clientId,this,socket,dos,dis);
           Thread clientThread = new Thread(client);
           clientThread.start();
           clientList.add(client);
           clientId++;
        }
    }

    public List<Client> getClientList() {
        return clientList;
    }

    public int getPort() {
        return port;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    public boolean isStop() {
        return stop;
    }

    public void broadcast(Message message) {
        if(serverListeners !=null){
            serverListeners.onReceivedMessage(message);
        }
        for( Client client :clientList){
            client.sendMessage(message);
        }
    }

    public ServerListeners getServerListeners() {
        return serverListeners;
    }

    public void setServerListeners(ServerListeners serverListeners) {
        this.serverListeners = serverListeners;
    }
}
