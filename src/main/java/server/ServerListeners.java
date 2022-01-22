package server;

import model.Data;

public interface ServerListeners {
    void onError(String message);
    void onStart(String message);
    void onStop(String message);
    void newUserJoin(String username);
    void onReceivedMessage(Data data);
}
