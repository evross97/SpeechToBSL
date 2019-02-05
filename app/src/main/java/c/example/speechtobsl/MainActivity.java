package c.example.speechtobsl;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import c.example.speechtobsl.services.Client;
import c.example.speechtobsl.services.Converter;
import c.example.speechtobsl.services.SpeechRecognitionListener;


public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private final String LOG_TAG = "BSL App";

    private Button mRecordButton = null;
    private TextView mRecordText = null;
    private TextView mTextConverted = null;
    private TextView mParsedSentence = null;
    private boolean mStartRecording = false;

    private String convertedSpeech = null;
    private String BSLSentence = null;

    private SpeechRecognitionListener speech = null;
    private Client parser = null;
    private Converter converter = null;

    private BroadcastReceiver cReceiver = null;
    private BroadcastReceiver pbReceiver = null;
    private BroadcastReceiver scbReceiver = null;


    private boolean permissionToRecordAccepted = false;
    private String[] permissions = {Manifest.permission.RECORD_AUDIO};
    private Intent signViewer = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signViewer = new Intent(this, SignViewer.class);

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);
        mRecordButton = findViewById(R.id.recordButton);
        mRecordText = findViewById(R.id.record_text);
        mTextConverted = findViewById(R.id.text_converted);
        mParsedSentence = findViewById(R.id.parsed_sentence);

        mRecordText.setText("Press the red button to start recording");
        mParsedSentence.setText("");
        mTextConverted.setText("");
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

        speech = new SpeechRecognitionListener(this);
        parser = new Client(this);
        converter = new Converter(this);

        scbReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context ctx, Intent intent) {
                String result = intent.getStringExtra("speech-convert-done");
                convertedSpeech = result;
                mTextConverted.setText(result);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        parser.sendRequest(new String[]{
                                "POST",
                                "http://192.168.0.15",
                                "9000",
                                "/?properties=%7B%22annotators%22%3A%20%22tokenize%2Cssplit%2Cpos%2Cner%2Cdepparse%2Copenie%22%2C%20%22date%22%3A%20%222019-01-26T16%3A46%3A19%22%7D",
                                result}
                        );
                    }
                }).start();
            }
        };

        pbReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String status = intent.getStringExtra("client-status");
                if(status.equals("done")) {
                    String result = intent.getStringExtra("client-done");
                    try {
                        JSONObject jsonResult = new JSONObject(result);
                        converter.convertSentence(jsonResult, convertedSpeech);
                    } catch (JSONException e) {
                        System.err.println("Couldn't convert result to JSON");
                    }
                } else {
                    String error = intent.getStringExtra("client-fail");
                    mRecordText.setText(error);
                }
            }
        };

        cReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context ctx, Intent intent) {
                String result = intent.getStringExtra("text-convert-done");
                mRecordText.setText(result);
                BSLSentence = result;
                signViewer.putExtra("bsl-sentence", BSLSentence);
                startActivity(signViewer);
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(scbReceiver, new IntentFilter("speech-convert"));
        LocalBroadcastManager.getInstance(this).registerReceiver(pbReceiver, new IntentFilter("client"));
        LocalBroadcastManager.getInstance(this).registerReceiver(cReceiver, new IntentFilter("text-convert"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(scbReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(pbReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(cReceiver);
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
            speech.startListening();
        } else {
            mRecordText.setText("Press the red button to start recording");
            speech.stopListening();
        }
    }

    //For demo
    /**
    private void formatJSONResult(){

        String a = "Time frame: "+findParts("nmod:tmod", 2);
        String b = "Negation: "+findParts("neg", 1);
        String c = "Location: "+findParts("case", 1);
        String d = "Object: "+findParts("dobj", 2);
        String e = "Subject: "+findParts("nsubj", 2);
        String f = "Verb: "+findParts("nsubj", 1);
        String g = "Question: "+findParts("WRB", 3);
        String h = "Possession: "+findParts("nmod:poss", 2);
        String i = "Adjectives: "+findParts("amod", 2);
        mRecordText.setText("");
        mParsedSentence.setText(a + "\n" + b +"\n" + c + "\n" + d + "\n" + e + "\n" + f + "\n" + g + "\n" + h + "\n" + i);
    }

    private String findParts(String key, Integer parts) {
        String value = "";

        try {
                JSONArray sentences = (JSONArray) jsonResult.get("sentences");
                JSONObject sentence = (JSONObject) sentences.get(0);
                if(parts.equals(3)) {
                    String parse = (String) sentence.get("parse");
                    if(parse.contains("WRB")) {
                        value = "True";
                    } else {
                        value = "False";
                    }
                } else {
                    JSONArray deps = (JSONArray) sentence.get("basicDependencies");
                    for(int i=0; i < deps.length(); i++) {
                        JSONObject current = (JSONObject) deps.get(i);
                        if(current.getString("dep").equals(key)) {
                            switch (parts) {
                                case 1:
                                    value = current.getString("governorGloss");
                                    break;
                                case 2:
                                    value = current.getString("dependentGloss");
                                    break;
                                default:
                                    value = current.toString();
                                    break;
                            }
                        }
                    }
                }
            } catch(JSONException e) {
                System.err.println(e.getMessage());
        }

        return value;
    }
     **/

}
