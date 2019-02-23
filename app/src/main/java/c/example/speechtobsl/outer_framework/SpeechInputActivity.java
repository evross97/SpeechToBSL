package c.example.speechtobsl.outer_framework;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import c.example.speechtobsl.R;
import c.example.speechtobsl.views.SpeechView;


public class SpeechInputActivity extends AppCompatActivity implements SuccessListener{

    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private final String LOG_TAG = "BSL App";

    private Button mRecordButton = null;
    private TextView mRecordText = null;
    private TextView mLoadingText = null;
    private boolean mStartRecording = false;


    private SpeechView speech = null;
    private Intent intent;
    private startLoading loading = null;

    private boolean permissionToRecordAccepted = false;
    private String[] permissions = {Manifest.permission.RECORD_AUDIO};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent = new Intent(this,SignViewerActivity.class);

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);
        mRecordButton = findViewById(R.id.record_button);
        mRecordText = findViewById(R.id.record_text);
        mLoadingText = findViewById(R.id.loading_text);

        mLoadingText.setVisibility(View.INVISIBLE);
        mRecordText.setText("Press the red button to start recording");
        mLoadingText.setText("Loading...");
        mRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.getId() == R.id.record_button) {
                    mStartRecording = !mStartRecording;
                    mRecordButton.setBackgroundResource(mStartRecording ? R.drawable.recording: R.drawable.not_recording);
                    onRecord(mStartRecording);
                }
            }
        });

        speech = new SpeechView(getApplicationContext(), this);
        loading = new startLoading();
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
            loading.execute();
        }
    }

    @Override
    public void onSuccess() {
        startActivity(intent);
        loading.cancel(true);
    }

    private class startLoading extends AsyncTask<Void, String, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mRecordButton.setVisibility(View.INVISIBLE);
            mRecordText.setVisibility(View.INVISIBLE);
            mLoadingText.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mRecordButton.setVisibility(View.VISIBLE);
            mRecordText.setVisibility(View.VISIBLE);
            mLoadingText.setVisibility(View.INVISIBLE);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected void onProgressUpdate(String... loading) {
            mLoadingText.setText(loading[0]);
        }

        @Override
        protected Void doInBackground(Void... params) {
            int count = 0;
            while(!isCancelled()) {
                switch(count) {
                    case 0:
                        publishProgress("Loading");
                        count++;
                        break;
                    case 1:
                        publishProgress("Loading.");
                        count++;
                        break;
                    case 2:
                        publishProgress("Loading..");
                        count++;
                        break;
                    case 3:
                        publishProgress("Loading...");
                        count = 0;
                        break;
                }
                SystemClock.sleep(100);
            }
            return null;
        }
    }

}
