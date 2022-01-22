package model;

import com.google.gson.Gson;

import java.util.List;

public class Data {
    private String message;
    private String toClient;
    private String fromClient;
    private List<String> clientList;
    private Type type = Type.MESSAGE;

    public Data(String message, String toClient, String fromClient) {
        this.message = message;
        this.toClient = toClient;
        this.fromClient = fromClient;
    }

    public Data(Type type,String fromClient) {
        this.fromClient = fromClient;
        this.type = type;
    }

    public Data() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToClient() {
        return toClient;
    }

    public void setToClient(String toClient) {
        this.toClient = toClient;
    }

    public String getFromClient() {
        return fromClient;
    }

    public void setFromClient(String fromClient) {
        this.fromClient = fromClient;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<String> getClientList() {
        return clientList;
    }

    public void setClientList(List<String> clientList) {
        this.clientList = clientList;
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
