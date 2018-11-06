package c.example.speechtobsl;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecognitionListener{

    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private final String LOG_TAG = "BSL App";

    private Button mRecordButton = null;
    private TextView mRecordText = null;
    private boolean mStartRecording = false;

    private SpeechRecognizer speech = null;
    private Intent recogniserIntent;

    private boolean permissionToRecordAccepted = false;
    private String[] permissions = {Manifest.permission.RECORD_AUDIO};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);
        mRecordButton = findViewById(R.id.recordButton);
        mRecordText = findViewById(R.id.record_text);
        mRecordText.setText("Press the red button to start recording");
        mRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.getId() == R.id.recordButton) {
                    mStartRecording = !mStartRecording;
                    mRecordButton.setBackgroundResource(mStartRecording ? R.drawable.recording: R.drawable.not_recording);
                    onRecord(mStartRecording);
                }
            }
        });

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
        ArrayList<String> matches = data.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        String returnedText = matches.get(0);
        mRecordText.setText(returnedText);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode) {
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if(!permissionToRecordAccepted) {
            finish();
        }
    }

    public void onRecord(boolean start) {
        if(start) {
            mRecordText.setText("Press the button again to stop recording");
            speech.startListening(recogniserIntent);
        } else {
            mRecordText.setText("Press the red button to start recording");
            speech.stopListening();
        }
    }

}
