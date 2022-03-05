package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

public  class ServerUtils {
    public static String getLocalIP(){
        InetAddress ip = null;
        try {
            ip = InetAddress.getLocalHost();
            String hostname = ip.getHostName();
            System.out.println("Your current IP address : " + ip);
            System.out.println("Your current Hostname : " + hostname);
            return  hostname;
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        }

    }
    public static String getRemoteIP(){

        URL url = null;
        try {
            url = new URL("http://checkip.amazonaws.com");
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    url.openStream()));
            return in.readLine();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
