package c.example.speechtobsl.views;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;

import java.util.ArrayList;

import c.example.speechtobsl.entities.Image;

/**
 * The view that sends instructions to the Sign Activity
 */
public class SignView {

    private final Context appCtx;
    private ArrayList<Image> BSLImages;
    private Integer currentImageIndex;
    private Intent intent;

    /**
     * Instantiates a new Sign view.
     *
     * @param ctx the context - needed to send images to activity
     */
    public SignView(Context ctx) {
        this.appCtx = ctx;
        this.BSLImages = new ArrayList<>();
        this.intent = new Intent("signView");
    }

    /**
     * Show sequence of BSL images with 0.1 seconds inbetween each image
     *
     * @param images all BSL signs to be shown
     */
    public void showSequence(ArrayList<Image> images) {
        this.BSLImages = images;
        this.currentImageIndex = 0;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                nextImage();
            }
        }, 100);
    }

    /**
     * Works out what type of image is to be shown next and sends it to the activity
     * - BSL sign - sends image and description
     * - Blank - break inbetween signs
     * Type ofimage also determines how long wait should be before next sign is shown
     */
    private void nextImage() {
        Integer delayTime = 1000;
        if(currentImageIndex < this.BSLImages.size()) {
            Image currentImage = this.BSLImages.get(currentImageIndex);
            if(currentImage.getImage() != null) {
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
