package c.example.speechtobsl.outer_framework;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import c.example.speechtobsl.R;

import static android.content.Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;

/**
 * Activity that shows the sequence of BSL signs to the user
 */
public class SignViewerActivity extends AppCompatActivity{

    private ImageView pic;
    private TextView desc;
    private ActionBar actionBar;
    private Toolbar toolbar;
    private Button replayButton;

    private Boolean showText = true;

    private BroadcastReceiver receiver;

    /**
     * Creates the activity screen and sets up the broadcast receiver to receive signs
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_viewer);
        this.desc = findViewById(R.id.signDesc);
        this.pic = findViewById(R.id.signView);

        Intent intent = getIntent();
        this.showText = intent.getBooleanExtra("showText", true);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.hide();

        replayButton = findViewById(R.id.replay_button);
        replayButton.setVisibility(View.GONE);

        this.receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String command = intent.getStringExtra("command");
                if(command.equals("image")) {
                    byte[] currentImage = intent.getByteArrayExtra("image");
                    setImage(currentImage);
                }
                if(command.equals("image_background")) {
                    int color = intent.getIntExtra("data", 0);
                    setImageBackground(color);
                }
                String desc = intent.getStringExtra("desc");
                if(desc.equals("end")) {
                    desc = "";
                    signsFinished();
                }
                if(showText) {
                    setDesc(desc);
                } else {
                    setDesc("");
                }
            }
        };
    }

    /**
     * Registers the receiver
     */
    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(this.receiver, new IntentFilter("signView"));
    }

    /**
     * Unregisters the receiver
     */
    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(this.receiver);
    }

    /**
     * Sets BSL image of current sign.
     *
     * @param image the image
     */
    public void setImage(byte[] image) {
        Glide
                .with(this)
                .load(image)
                .into(this.pic);
    }

    /**
     * Sets name of current sign
     *
     * @param desc the name
     */
    public void setDesc(String desc) {
        this.desc.setText(desc);
    }

    /**
     * Sets image background - used to show the end of one sign and beginning of next
     * Often helpful when fingerspelling words - blank screen will be shown for longer to indicate end of word
     *
     * @param color the background color
     */
    public void setImageBackground(int color) {
        this.pic.setImageResource(color);
    }

    /**
     * Restarts the activity to help with window allocation
     * Shows the action bar and the replay button to give user options now that the sign sequence has finished
     */
    public void signsFinished() {
        this.actionBar.show();
        this.replayButton.setVisibility(View.VISIBLE);
    }

    /**
     * User has hit the replay button - starts of sequence telling the sign view to repeat the last set of signs
     * @param view
     */
    public void replay(View view) {
        Intent main = new Intent();
        main.putExtra("replay", true);
        setResult(RESULT_OK, main);
        finish();
    }

    /**
     * User has hit something in the action bar
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult(RESULT_CANCELED);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}