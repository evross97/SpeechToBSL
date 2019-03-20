package c.example.speechtobsl.outer_framework;

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
import android.widget.Toast;

import c.example.speechtobsl.R;
import c.example.speechtobsl.views.SpeechView;


/**
 * Main screen - used for speech input and to access the settings page
 */
public class SpeechInputActivity extends AppCompatActivity implements EndListener{

    private Button mRecordButton = null;
    private TextView mRecordText = null;
    private TextView mLoadingText = null;
    private boolean mStartRecording = false;


    public SpeechView speech = null;
    private startLoading loading = null;

    public Integer speed = 1;
    public Boolean showText = true;

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

        this.mRecordButton = findViewById(R.id.record_button);
        this.mRecordText = findViewById(R.id.record_text);
        this.mLoadingText = findViewById(R.id.loading_text);

        this.mLoadingText.setVisibility(View.INVISIBLE);
        this.mRecordText.setText("Press the red button to start recording");
        this.mLoadingText.setText("Loading...");
        this.mRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.getId() == R.id.record_button) {
                    mStartRecording = !mStartRecording;
                    mRecordButton.setBackgroundResource(mStartRecording ? R.drawable.recording: R.drawable.not_recording);
                    onRecord(mStartRecording);
                }
            }
        });

        this.speech = new SpeechView(getApplicationContext(), this);
        this.loading = new startLoading();

        Intent received = getIntent();
        this.speed = received.getIntExtra("speed", 1);
        this.showText = received.getBooleanExtra("showText", true);
    }

    /**
     * Starts recording the users speech and gives instructions about stopping recording
     *
     * @param start the start
     */
    public void onRecord(boolean start) {
        if(start) {
            this.mRecordText.setText("Press the button again to stop recording");
            this.speech.startListening();
        } else {
            this.mRecordText.setText("Press the red button to start recording");
            this.speech.stopListening();
            this.loading.execute();
        }
    }

    /**
     * Called to start the sign activity and cancel the loading text
     */
    @Override
    public void onSuccess() {
        Intent intent = new Intent(this,SignViewerActivity.class);
        intent.putExtra("showText", this.showText);
        this.loading.cancel(true);
        startActivityForResult(intent, this.VIEWER);
    }

    /**
     * Called when the controller wasn't able to convert the sentence
     */
    @Override
    public void onFailure() {
        Intent restart = getIntent();
        restart.putExtra("speed", this.speed);
        restart.putExtra("showText", this.showText);
        finish();
        startActivity(restart);
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(this, R.string.toast_text, duration);
        toast.show();
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

    /**
     * Result has been sent back from activity - either settings page or sign viewer page
     * @param requestCode
     * @param resultCode
     * @param intent
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if(requestCode == this.SETTINGS) {
            this.speed = intent.getIntExtra("speed", 2);
            this.showText = intent.getBooleanExtra("showText", true);
            this.speech.updateSpeed(speed);

        }
        if(requestCode == this.VIEWER) {
            if(resultCode == RESULT_CANCELED) {
                Intent restart = getIntent();
                restart.putExtra("speed", this.speed);
                restart.putExtra("showText", this.showText);
                finish();
                startActivity(restart);
            } else {
                Boolean replay = intent.getBooleanExtra("replay", false);
                if(replay) {
                    this.speech.replaySequence();
                    this.onSuccess();
                }
            }
        }
    }

    /**
     * Add my customised menu to the toolbar of the app
     * @param menu
     * @return
     */
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
            cancel(true);
            return null;
        }
    }

}
