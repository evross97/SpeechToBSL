package c.example.speechtobsl.services;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ParserClient {

    private Context appCtx;
    private Intent localIntent = new Intent("parser");

    public ParserClient(Context ctx) {
        appCtx = ctx;
    }

    public void parse(String[] params) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                runParse(params);
            }
        }).start();
    }

    private void runParse(String[] params) {
        String IP = params[0];
        String port = params[1];
        String msg = params[2];
        String props = "/?properties=%7B%22annotators%22%3A%20%22tokenize%2Cssplit%2Cpos%2Cner%2Cdepparse%2Copenie%22%2C%20%22date%22%3A%20%222019-01-26T16%3A46%3A19%22%7D";
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(IP+":"+port+props);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            //Request header
            connection.setRequestMethod("POST");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");

            //Send post request
            connection.setDoOutput(true);
            DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
            dos.writeBytes(msg);
            dos.flush();
            dos.close();

            //Response - input
            BufferedReader bf = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line;

            while ((line=bf.readLine())!=null) {
                sb.append(line);
            }
            bf.close();

        } catch (Exception e) {
            localIntent.putExtra("parser-status", "fail");
            localIntent.putExtra("parser-fail", "Error: Couldn't get parse of sentence - " + e.getMessage());
            LocalBroadcastManager.getInstance(appCtx.getApplicationContext()).sendBroadcast(localIntent);
        }
        localIntent.putExtra("parser-status", "done");
        localIntent.putExtra("parser-done", sb.toString());
        LocalBroadcastManager.getInstance(appCtx.getApplicationContext()).sendBroadcast(localIntent);
    }
}
