package c.example.speechtobsl.outer_framework;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import c.example.speechtobsl.R;

public class SignViewerActivity extends AppCompatActivity{

    private ImageView pic;
    private TextView desc;

    private BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_viewer);
        this.desc = findViewById(R.id.signDesc);
        this.pic = findViewById(R.id.signView);
        this.receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String command = intent.getStringExtra("command");
                if(command.equals("image")) {
                    byte[] currentImage = intent.getByteArrayExtra("image");
                    Bitmap bitmap = BitmapFactory.decodeByteArray(currentImage,0,currentImage.length);
                    setImage(bitmap);
                }
                if(command.equals("image_background")) {
                    int color = intent.getIntExtra("data", 0);
                    setImageBackground(color);
                }
                String desc = intent.getStringExtra("desc");
                setDesc(desc);
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(this.receiver, new IntentFilter("signView"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(this.receiver);
    }

    public void setImage(Bitmap image) {
        this.pic.setImageBitmap(image);
    }

    public void setDesc(String desc) {
        this.desc.setText(desc);
    }

    public void setImageBackground(int color) {
        this.pic.setImageResource(color);
    }
}
