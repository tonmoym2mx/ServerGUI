package server;


import com.google.gson.Gson;
import model.Message;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client implements Runnable {

    private final Server server;
    private final Socket socket;
    private final int id;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

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
                // receive the string
                received = dataInputStream.readUTF();
                Gson gson = new Gson();
                Message message = gson.fromJson(received, Message.class);
                System.out.println(message.toString());
                if(received.equals("logout")){
                    this.socket.close();
                    break;
                }
                server.broadcast(message);
            }
            catch (IOException e) {

                e.printStackTrace();
            }

        }

        try {
            // closing resources
            this.dataInputStream.close();
            this.dataOutputStream.close();

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    void sendMessage(Message message){
        try {
            dataOutputStream.writeUTF("From Server: "+message.getJsonString());
            dataOutputStream.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
