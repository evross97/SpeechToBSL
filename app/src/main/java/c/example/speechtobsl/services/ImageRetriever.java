package c.example.speechtobsl.services;

import android.content.Context;

import java.util.ArrayList;

import c.example.speechtobsl.utils.Image;

public class ImageRetriever {

    Context appCtx;
    SignDatabase db;

    public ImageRetriever(Context ctx) {
        this.appCtx = ctx;
        this.db = new SignDatabase(this.appCtx);
    }

    public ArrayList<Image> getImageSentence(ArrayList<String> sentence) {
        ArrayList<Image> images = new ArrayList<>();
        for(String word : sentence) {
            images.add(db.getSign(word));
        }
        return images;
    }
}
