package server;

import java.io.IOException;

public class ServerThread extends Thread{

    private final Server server;

    public ServerThread(Server server) {
        super("server_thread");
        this.server = server;
    }
   public void startServer(){
        start();
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
                server.getServerListeners().onStart("Server is start");
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
}
