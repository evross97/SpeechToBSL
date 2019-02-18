package c.example.speechtobsl.outer_framework;

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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import c.example.speechtobsl.R;
import c.example.speechtobsl.models.StructureConverterModel;
import c.example.speechtobsl.views.SpeechView;


public class SpeechInputActivity extends AppCompatActivity implements SuccessListener{

    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private final String LOG_TAG = "BSL App";

    private Button mRecordButton = null;
    private TextView mRecordText = null;
    private TextView mTextConverted = null;
    private TextView mParsedSentence = null;
    private boolean mStartRecording = false;


    private SpeechView speech = null;
    private Intent intent;

    private boolean permissionToRecordAccepted = false;
    private String[] permissions = {Manifest.permission.RECORD_AUDIO};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent = new Intent(this,SignViewerActivity.class);

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

        speech = new SpeechView(getApplicationContext(), this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
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

    @Override
    public void onSuccess() {
        System.out.println("HHHHHHHHHHHHHEEEEEEEEEEEELLLLLLLLLLLLOOOOOOOOOOOOOOO");
        intent.putExtra("hi","please work");
        startActivity(intent);
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
