package c.example.speechtobsl.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;

import java.util.ArrayList;

import c.example.speechtobsl.entities.Image;

public class SignView {

    private final Context appCtx;
    private ArrayList<Image> BSLImages;
    private Integer currentImageIndex;
    private Intent intent;

    public SignView(Context ctx) {
        this.appCtx = ctx;
        this.BSLImages = new ArrayList<>();
        this.intent = new Intent("signView");
    }

    public void showSequence(ArrayList<Image> images) {
        this.BSLImages = images;
        this.currentImageIndex = 0;
        this.nextImage();
    }

    private void nextImage() {
        System.out.println(currentImageIndex);
        Integer delayTime = 1000;
        if(currentImageIndex < this.BSLImages.size()) {
            Image currentImage = this.BSLImages.get(currentImageIndex);
            if(currentImage.getImage() != null) {
                System.out.println(currentImage.getImage() + " " + currentImage.getDesc());
                //send image
                this.intent.putExtra("command", "image");
                this.intent.putExtra("image", currentImage.getImage());
                this.intent.putExtra("desc", currentImage.getDesc());
                LocalBroadcastManager.getInstance(this.appCtx.getApplicationContext()).sendBroadcast(this.intent);
            } else {
                if(currentImage.getDesc().equals("endWord")) {
                    delayTime = 100;
                }
                else {
                    delayTime = 10;
                }
                this.intent.putExtra("command", "image_background");
                this.intent.putExtra("color", android.R.color.white);
                this.intent.putExtra("desc", " ");
                LocalBroadcastManager.getInstance(this.appCtx.getApplicationContext()).sendBroadcast(this.intent);
            }
            currentImageIndex++;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    nextImage();
                }
            }, delayTime);
        }
    }
}
