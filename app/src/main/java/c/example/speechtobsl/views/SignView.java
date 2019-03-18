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
    public ArrayList<Image> BSLImages;
    public Intent intent;
    public Integer currentImageIndex;


    private Integer speed;
    public Integer delayTime = 1500;

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
     * Show sequence of BSL images with 1.5 seconds in-between each image
     *
     * @param images all BSL signs to be shown
     */
    public void showSequence(ArrayList<Image> images) {
        this.BSLImages.clear();
        this.BSLImages = images;
        this.currentImageIndex = 0;
        Image end = new Image(null, "endSentence");
        this.BSLImages.add(end);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                nextImage();
            }
        }, this.delayTime);
    }

    /**
     * Works out what type of image is to be shown next and sends it to the activity
     * - BSL sign - sends image and description
     * - Blank - break inbetween signs
     * Type of image also determines how long wait should be before next sign is shown
     */
    private void nextImage() {
        System.out.println("IN NEXT IMAGE");
        Integer currentDelay = this.delayTime;
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
                    currentDelay = this.delayTime/10;
                }
                else {
                    currentDelay = this.delayTime/100;
                }
                this.intent.putExtra("command", "image_background");
                this.intent.putExtra("color", android.R.color.white);
                if(currentImage.getDesc().equals("endSentence")) {
                    this.intent.putExtra("desc", "end");
                } else {
                    this.intent.putExtra("desc", " ");
                }
                LocalBroadcastManager.getInstance(this.appCtx.getApplicationContext()).sendBroadcast(this.intent);
            }
            System.out.println("IN Index: " + currentImageIndex);
            currentImageIndex++;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    nextImage();
                }
            }, currentDelay);
        }
    }

    /**
     * Replay button has been pressed, call nextImage after resetting the current image index so that all the signs are displayed again
     */
    public void replaySequence() {
        this.currentImageIndex = 0;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                nextImage();
            }
        }, delayTime);
    }

    /**
     * Update the time between sign picture changes
     * 2 - fast speed - max 1sec between images
     * 1 - medium speed - max 1.5sec between images
     * 0 - slow speed - max 2sec between images
     * @param speed
     */
    public void speedUpdate(Integer speed) {
        this.speed = speed;
        switch (this.speed) {
            case 2:
                this.delayTime = 1000;
                break;
            case 0:
                this.delayTime = 2000;
                break;
            default:
                this.delayTime = 1500;
                break;
        }
    }
}
