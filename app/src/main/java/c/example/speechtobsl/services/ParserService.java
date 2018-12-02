package c.example.speechtobsl.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.loopj.android.http.*;

import c.example.parser.StanfordParser;


public class ParserService extends IntentService {

    private final String LOG_TAG = "BSL-services-parser";
    private String messageText;
    private StanfordParser parser;

    public ParserService() {
        super("ParserService");
        parser = new StanfordParser();
        AsyncHttpClient client = new AsyncHttpClient();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Intent localIntent = new Intent("parser");
        try {
            messageText = intent.getStringExtra("messageText");
            Log.i(LOG_TAG, messageText);
            localIntent.putExtra("parser-status", "going");
            String result = parser.parseString(messageText);
            localIntent.putExtra("parser-status", "done");
            localIntent.putExtra("parser-done", result);
        } catch (NullPointerException e) {
            localIntent.putExtra("parser-status", "failed-no message present");
        }
        LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
    }
}
