package c.example.speechtobsl;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

public class SpeechRecognitionListener extends AppCompatActivity implements RecognitionListener{

    private SpeechRecognizer speech;
    private Intent recogniserIntent;
    private String decodedSpeech;

    private TextView mResultText;

    private final String LOG_TAG = "BSL App";

    public SpeechRecognitionListener() {
        setContentView(R.layout.activity_written_speech);
        mResultText = findViewById(R.id.record_text);
        speech = SpeechRecognizer.createSpeechRecognizer(this);
        speech.setRecognitionListener(this);
        recogniserIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recogniserIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "en");
        recogniserIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
    }

    @Override
    public void onBeginningOfSpeech() {
        Log.i(LOG_TAG, "onBeginningOfSpeech");
    }

    @Override
    public void onRmsChanged(float v) {
        Log.i(LOG_TAG, "onRmsChanged");

    }

    @Override
    public void onBufferReceived(byte[] bytes) {
        Log.i(LOG_TAG, "onBufferReceived: " + bytes);

    }

    @Override
    public void onEndOfSpeech() {
        Log.i(LOG_TAG, "onEndOfSpeech");
    }

    @Override
    public void onError(int code) {
        Log.i(LOG_TAG, "FAILED with error code " + String.valueOf(code));

    }

    @Override
    public void onReadyForSpeech(Bundle arg) {
        Log.i(LOG_TAG, "onReadyForSpeech");
    }

    @Override
    public void onPartialResults(Bundle arg) {
        Log.i(LOG_TAG, "onPartialResults");
    }

    @Override
    public void onEvent(int i, Bundle bundle) {
        Log.i(LOG_TAG, "onEvent");

    }

    @Override
    public void onResults(Bundle data) {
        Log.i(LOG_TAG,"onResults");
        ArrayList<String> matches = data.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        decodedSpeech = matches.get(0);
        mResultText.setText(decodedSpeech);
    }

    public void startListening() {
        speech.startListening(recogniserIntent);
    }

    public void stopListening() {
        speech.stopListening();
    }
}
