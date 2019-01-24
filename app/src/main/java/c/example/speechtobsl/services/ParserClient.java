package c.example.speechtobsl.services;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class ParserClient {

    private final String LOG_TAG = "BSL-client-parser";
    private static final String BASE_URL = "http://corenlp.run";
    RequestQueue queue;
    Context appCtx;

    public ParserClient(Context ctx) {
        appCtx = ctx;
        queue = Volley.newRequestQueue(appCtx.getApplicationContext());
    }

    public void parseSentence(String text) {
        Intent localIntent = new Intent("parser");
        try {
            localIntent.putExtra("parser-status", "going");
            sendRequest(text);

        } catch (NullPointerException e) {
            localIntent.putExtra("parser-status", "failed-no message present");
        }
        LocalBroadcastManager.getInstance(appCtx.getApplicationContext()).sendBroadcast(localIntent);
    }



    private void sendRequest(String text) {
        JSONObject params = new JSONObject();
        try {
            params.put("",text);
        } catch (JSONException e) {
            System.err.println("Couldn't create params");
        }
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                BASE_URL,
                params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Intent localIntent = new Intent("parser");
                        localIntent.putExtra("parser-status", "done");
                        localIntent.putExtra("parser-done", response.toString());
                        LocalBroadcastManager.getInstance(appCtx.getApplicationContext()).sendBroadcast(localIntent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Intent localIntent = new Intent("parser");
                        localIntent.putExtra("parser-status", "fail");
                        //volley timeout error????
                        System.out.println(error);
                        localIntent.putExtra("parser-fail", "Error: Couldn't get parse of sentence - " + error.getCause() + " : " + error.getMessage());
                        LocalBroadcastManager.getInstance(appCtx.getApplicationContext()).sendBroadcast(localIntent);
                    }
                }
        );
        queue.add(request);
    }
}
