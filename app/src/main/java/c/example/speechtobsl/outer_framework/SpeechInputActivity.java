package c.example.speechtobsl.outer_framework;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import c.example.speechtobsl.R;
import c.example.speechtobsl.views.SpeechView;


/**
 * The first activity show to user, from here
 */
public class SpeechInputActivity extends AppCompatActivity implements SuccessListener{

    private final String LOG_TAG = "BSL App";

    private Button mRecordButton = null;
    private TextView mRecordText = null;
    private TextView mLoadingText = null;
    private boolean mStartRecording = false;


    private SpeechView speech = null;
    private Intent intent;
    private startLoading loading = null;

    private Integer speed = 2;
    private Boolean showText = true;
    private BroadcastReceiver receiver;

    private final int SETTINGS = 1;
    private final int VIEWER = 2;


    /**
     * Creates the activity screen, gets permissions, initiates the text views and buttons
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

    /**
     * Starts recording the users speech and gives instructions about stopping recording
     *
     * @param start the start
     */
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

    /**
     * Called to start the sign activity and cancel the loading text
     */
    @Override
    public void onSuccess() {
        intent = new Intent(this,SignViewerActivity.class);
        intent.putExtra("showText", this.showText);
        intent.putExtra("speed", this.speed);
        loading.cancel(true);
        startActivityForResult(intent, this.VIEWER);
    }

    /**
     * User has hit something in the action bar
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent settingsIntent = new Intent(this,SettingsActivity.class);
                settingsIntent.putExtra("showText", this.showText);
                settingsIntent.putExtra("speed", this.speed);
                startActivityForResult(settingsIntent, this.SETTINGS);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if(requestCode == this.SETTINGS) {
            this.speed = intent.getIntExtra("speed", 2);
            this.showText = intent.getBooleanExtra("showText", true);
            speech.updateSpeed(speed);

        }
        if(requestCode == this.VIEWER) {
            System.out.println("IN VIEWER: " + resultCode);
            if(resultCode == RESULT_CANCELED) {
                mRecordButton.setVisibility(View.VISIBLE);
                mRecordText.setVisibility(View.VISIBLE);
                mLoadingText.setVisibility(View.INVISIBLE);
                loading.cancel(true);
            } else {
                Boolean replay = intent.getBooleanExtra("replay", false);
                if(replay) {
                    speech.replaySequence();
                    this.onSuccess();
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Seperate thread to show loading to user
     */
    private class startLoading extends AsyncTask<Void, String, Void> {

        /**
         * Shows loading text and hides other text and recording button
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mRecordButton.setVisibility(View.INVISIBLE);
            mRecordText.setVisibility(View.INVISIBLE);
            mLoadingText.setVisibility(View.VISIBLE);
        }

        /**
         * Resets screen ready for next input
         * @param aVoid
         */
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        /**
         * Cancels task
         */
        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        /**
         * Updates text view to show app is progressing to user
         * @param loading
         */
        @Override
        protected void onProgressUpdate(String... loading) {
            mLoadingText.setText(loading[0]);
        }

        /**
         * Updates text shown to user every 0.1 seconds
         * @param params
         * @return
         */
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
