package c.example.speechtobsl;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;

import c.example.speechtobsl.services.ImageRetriever;
import c.example.speechtobsl.utils.Image;

public class SignViewer extends AppCompatActivity {

    private ImageView pic;
    private TextView desc;

    private ImageRetriever image = null;
    private String BSLSentence = "";
    private ArrayList<Image> BSLImages;
    private int currentImageIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_viewer);
        desc = findViewById(R.id.signDesc);
        pic = findViewById(R.id.signView);
        Intent intent = getIntent();
        this.BSLSentence = intent.getExtras().getString("bsl-sentence");
        this.image = new ImageRetriever(this);
        this.displayBSLSentence();
    }

    private void displayBSLSentence() {
        this.BSLImages = this.image.getImageSentence(new ArrayList<>(Arrays.asList(this.BSLSentence.split(","))));
        this.nextImage();
    }

    private void nextImage() {
        System.out.println(currentImageIndex);
        if(currentImageIndex < this.BSLImages.size()) {
            Image currentImage = this.BSLImages.get(currentImageIndex);
            System.out.println(currentImage.getImage().length + " " + currentImage.getDesc());
            Bitmap bitmap = BitmapFactory.decodeByteArray(currentImage.getImage(),0,currentImage.getImage().length);
            pic.setImageBitmap(bitmap);
            desc.setText(currentImage.getDesc());
            currentImageIndex++;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    nextImage();
                }
            }, 1000);
        }
    }
}
