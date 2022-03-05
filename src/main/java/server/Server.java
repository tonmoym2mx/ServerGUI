package server;

import model.Data;
import model.Type;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
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
        System.out.println("Server Info :"+ server.getLocalSocketAddress().toString());
        stop = false;
        while (!stop){
           Socket socket =  server.accept();

           DataInputStream dis = new DataInputStream(socket.getInputStream());
           DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
           Client client = new Client(clientId,this,socket,dos,dis);
           client.setServerListeners(serverListeners);
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

    public void broadCastUserListUpdate(){
        List<String> clientNamesList = new ArrayList<String>();
        for(Client client : clientList){
            clientNamesList.add(client.getName());
        }
        Data userData = new Data();

        userData.setType(Type.USER_LIST_REQUEST);
        userData.setClientList(clientNamesList);
        for( Client client :clientList){
            System.out.println(client.getName()+ ": "+getClientList().toString());
            client.sendMessage(userData);
        }

    }
    public void broadcast(Data data) {
        if(serverListeners !=null){
            serverListeners.onReceivedMessage(data);
        }
        for( Client client :clientList){
            if(data.getToClient().equals("All")){
                client.sendMessage(data);
            }else if(data.getToClient().equals(client.getName())) {
                client.sendMessage(data);
            }
        }
    }

    public ServerListeners getServerListeners() {
        return serverListeners;
    }

    public void setServerListeners(ServerListeners serverListeners) {
        this.serverListeners = serverListeners;
    }
}
