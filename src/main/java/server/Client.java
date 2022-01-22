package server;


import com.google.gson.Gson;
import model.Data;
import model.Type;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Client implements Runnable {

    private final Server server;
    private final Socket socket;
    private final int id;
    private  String name;

    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private  ServerListeners serverListeners;

    public Client(int id,Server server, Socket socket, DataOutputStream dataOutputStream, DataInputStream dataInputStream) {
        this.server = server;
        this.id = id;
        this.socket = socket;
        this.dataInputStream = dataInputStream;
        this.dataOutputStream = dataOutputStream;

    }

    @Override
    public void run() {

        System.out.println("Client "+id +" is join");
        String received;

        while (true)  {
            try {

                received = dataInputStream.readUTF();
                Gson gson = new Gson();
                Data data = gson.fromJson(received, Data.class);

                System.out.println(data.toString());

                if(data.getType() == Type.LOGOUT){
                    removeMe();
                    this.socket.close();
                    break;
                }else if(data.getType() == Type.JOIN_REQUEST){

                    name = data.getFromClient();
                    server.broadCastUserListUpdate();
                    if(serverListeners!=null){
                        serverListeners.newUserJoin(name);
                    }

                }else if(data.getType() == Type.MESSAGE) {
                    server.broadcast(data);
                }

            }
            catch (IOException e) {
                e.printStackTrace();
                removeMe();
                break;
            }

        }

        try {
            // closing resources
            this.dataInputStream.close();
            this.dataOutputStream.close();

        }catch(IOException e){
            removeMe();
            e.printStackTrace();
        }
    }
    private void removeMe(){
        for(Client client :server.getClientList()){
            if(client.name.equals(name)){
                server.getClientList().remove(client);
                break;
            }
        }

        server.broadCastUserListUpdate();
    }

    void sendMessage(Data data){
        try {
            dataOutputStream.writeUTF(data.getJsonString());
            dataOutputStream.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }



    public void setServerListeners(ServerListeners serverListeners) {
        this.serverListeners = serverListeners;
    }
}
