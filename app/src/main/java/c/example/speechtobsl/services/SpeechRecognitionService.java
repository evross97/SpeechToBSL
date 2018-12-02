package c.example.speechtobsl.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import c.example.parser.StanfordParser;

public class SpeechRecognitionService extends IntentService {

    private final String LOG_TAG = "BSL-services-parser";
    private String messageText;
    private StanfordParser parser;

    public SpeechRecognitionService() {
        super("SpeechRecognitionService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        parser = new StanfordParser();
        Intent localIntent = new Intent("parser");
        try {
            messageText = intent.getStringExtra("messageText");
            Log.i(LOG_TAG, messageText);
            localIntent.putExtra("parser-status", "going");
            parser.parseString(messageText);
        } catch (NullPointerException e) {
            localIntent.putExtra("parser-status", "failed-no message present");
        }
        LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
    }
}
