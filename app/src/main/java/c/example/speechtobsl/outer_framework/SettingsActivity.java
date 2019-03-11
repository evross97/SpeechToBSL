package c.example.speechtobsl.outer_framework;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Switch;

import c.example.speechtobsl.R;

import static android.content.Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;

/**
 * Settings screen to allow the user to control the speed of the sign sequence and whether any text is shown underneath the signs
 */
public class SettingsActivity extends AppCompatActivity {

    private Integer speedValue = 2;
    private Boolean showText = true;

    private SeekBar speed;
    private Switch textSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        speed = findViewById(R.id.speed_bar);
        speed.setProgress(getIntent().getIntExtra("speed",2));
        speed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                speedValue = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        textSwitch = findViewById(R.id.written_switch);
        textSwitch.setChecked(getIntent().getBooleanExtra("showText", true));
    }

    /**
     * User has hit the save button - sends the updated settings to the speech input activity to update the app
     * @param view
     */
    public void onSave(View view) {
        Intent main = new Intent();
        this.showText = this.textSwitch.isChecked();
        main.putExtra("speed",this.speedValue);
        main.putExtra("showText", this.showText);
        setResult(RESULT_OK, main);
        finish();
    }
}
