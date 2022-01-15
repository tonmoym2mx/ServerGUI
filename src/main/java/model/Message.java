package model;

import com.google.gson.Gson;

public class Message {
    private String message;
    private int toClient;
    private int fromClient;

    public Message(String message, int toClient, int fromClient) {
        this.message = message;
        this.toClient = toClient;
        this.fromClient = fromClient;
    }

    public Message() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getToClient() {
        return toClient;
    }

    public void setToClient(int toClient) {
        this.toClient = toClient;
    }

    public int getFromClient() {
        return fromClient;
    }

    public void setFromClient(int fromClient) {
        this.fromClient = fromClient;
    }
    public String getJsonString(){
        Gson gson = new Gson();
        String jsonString = gson.toJson(this);
        return jsonString.replace("\n","");
    }

    @Override
    public String toString() {
        return "message:" + message  + ", from:" + fromClient +", to:" + toClient ;
    }
}
