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

            Log.i(LOG_TAG, text);
            localIntent.putExtra("parser-status", "going");
            sendRequest(text);

        } catch (NullPointerException e) {
            localIntent.putExtra("parser-status", "failed-no message present");
        }
        LocalBroadcastManager.getInstance(appCtx.getApplicationContext()).sendBroadcast(localIntent);
    }



    private void sendRequest(String text) {
        //String text2 = "Where the boy see the girl with the telescope?";
        JSONObject params = new JSONObject();
        try {
            params.put("",text);

            System.out.println("Added params");
        } catch (JSONException e) {
            System.err.println("Couldn't create params");
        }

        System.out.println("About to send request.");
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                BASE_URL,
                params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("I DID IT: " + response);
                        Intent localIntent = new Intent("parser");
                        localIntent.putExtra("parser-status", "done");
                        localIntent.putExtra("parser-done", response.toString());
                        LocalBroadcastManager.getInstance(appCtx.getApplicationContext()).sendBroadcast(localIntent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("IT DIDNT WORK" + error.getMessage());
                    }
                }
        );
        System.out.println("About to add to queue" + request.toString());

        queue.add(request);
    }
}
