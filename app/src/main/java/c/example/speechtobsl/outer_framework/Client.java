package c.example.speechtobsl.outer_framework;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Client {

    private Context appCtx;
    private Intent localIntent = new Intent("client");

    public Client() {
    }

    /**
     *
     * @param params
     * 0. The request method
     * 1. IP address
     * 2. The port number
     * 3. Extra parameters for end of URL
     * 4. Message to send (only for POST requests)
     * @return The response from the server
     */
    public String sendRequest(String[] params) {
        String method = params[0];
        String IP = params[1];
        String port = params[2];
        String props = params[3];
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(IP+":"+port+props);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            if(method.equals("POST")) {
                //Request header
                connection.setRequestMethod("POST");
                connection.setRequestProperty("User-Agent", "Mozilla/5.0");

                //Send post request
                String msg = params[4];
                connection.setDoOutput(true);
                DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
                dos.writeBytes(msg);
                dos.flush();
                dos.close();
            } else {
                connection.setRequestProperty("Accept","application/json");
                connection.setRequestProperty("app_id","a7e2cc18");
                connection.setRequestProperty("app_key", "5836a8400e0fcbcc9922288a0479ca6d");
            }


            //Response - input
            BufferedReader bf = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line;

            while ((line=bf.readLine())!=null) {
                sb.append(line);
            }
            bf.close();

        } catch (Exception e) {
            return e.getMessage();
        }
        return sb.toString();
    }
}
