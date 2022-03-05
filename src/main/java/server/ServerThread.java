package server;

import utils.IpUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

public class ServerThread extends Thread{

    private final Server server;

    public ServerThread(Server server) {
        super("server_thread");
        this.server = server;
    }
    public void stopServer(){
        server.setStop(true);
    }
    public boolean isRunning(){
      return this.isAlive();
    }

    @Override
    public void run() {
        try {
            if(server.getServerListeners() !=null){
                server.getServerListeners().onStart("Server is start \n"+ toString());
            }
            server.start();
            if(server.getServerListeners() !=null){
                server.getServerListeners().onStop("Server is stop");
            }

        } catch (IOException e) {
            e.printStackTrace();
            if(server.getServerListeners() !=null){
                server.getServerListeners().onStop("Server is stop");
            }
            if(server.getServerListeners() !=null){
                server.getServerListeners().onError(e.getMessage());
            }
        }
    }



    @Override
    public String toString() {
        return "Port: "+ server.getPort()+"\n" +
                IpUtils.localIps() +
                "Global IP:"+ IpUtils.remoteIP()+"\n";
    }
}
