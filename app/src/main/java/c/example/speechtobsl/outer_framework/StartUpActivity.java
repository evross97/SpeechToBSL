package c.example.speechtobsl.outer_framework;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import c.example.speechtobsl.R;

/**
 * Page loaded on app start - a small introduction to the app
 */
public class StartUpActivity extends AppCompatActivity {

    private static final int PERMISSION = 200;
    private boolean permissionsAccepted = false;
    private String[] permissions = {Manifest.permission.RECORD_AUDIO, Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActivityCompat.requestPermissions(this, permissions, PERMISSION);
    }

    /**
     * Checks if app has permissions to record audio
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode) {
            case PERMISSION:
                permissionsAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if(!permissionsAccepted) {
            finish();
        }
    }

    /**
     * User has hit the button to proceed to the main recording screen
     * @param view
     */
    public void startMain(View view) {
        Intent main = new Intent(this, SpeechInputActivity.class);
        main.putExtra("speed", 1);
        main.putExtra("showText", true);
        startActivity(main);
    }
}
