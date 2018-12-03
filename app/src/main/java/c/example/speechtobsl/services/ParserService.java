package c.example.speechtobsl.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.loopj.android.http.*;

import org.json.JSONArray;
import org.json.JSONObject;

import c.example.parser.ParserServer;
import cz.msebera.android.httpclient.Header;


public class ParserService extends IntentService {

    private final String LOG_TAG = "BSL-services-parser";
    private String messageText;
    private ParserClient parser;
    private ParserServer server;

    public ParserService() {
        super("ParserService");
        parser = new ParserClient();
        //server = new ParserServer();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Intent localIntent = new Intent("parser");
        try {
            messageText = intent.getStringExtra("messageText");
            Log.i(LOG_TAG, messageText);
            localIntent.putExtra("parser-status", "going");
            parseSentence(messageText, localIntent);
            localIntent.putExtra("parser-status", "done");
            //localIntent.putExtra("parser-done", result);
        } catch (NullPointerException e) {
            localIntent.putExtra("parser-status", "failed-no message present");
        }
        LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
    }

    private void parseSentence(String text, Intent intent) {
        //server.run();
        RequestParams params = new RequestParams();
        params.put("properties","'annotators':'tokenize,ssplit,pos,depparse,udfeats'");
        params.put("data", text);
        parser.post(params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                System.out.println("DID IT: " + response.toString());
                intent.putExtra("parser-done", response.toString());
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                System.out.println("DID IT 2: ");
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                System.out.println("FAILED");
            }
        });
    }



}
