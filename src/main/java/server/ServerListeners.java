package server;

import model.Message;

public interface ServerListeners {
    void onError(String message);
    void onStart(String message);
    void onStop(String message);
    void onReceivedMessage(Message message);
}
