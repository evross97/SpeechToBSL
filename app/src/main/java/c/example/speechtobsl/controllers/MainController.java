package c.example.speechtobsl.controllers;


import android.content.Context;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import c.example.speechtobsl.entities.Image;
import c.example.speechtobsl.outer_framework.SuccessListener;
import c.example.speechtobsl.views.SignView;

public class MainController {

    ConverterController cController;
    ImageRetrieverController iController;
    SignView signView;
    SuccessListener listener;

    public MainController(Context ctx, SuccessListener listener) {
        this.cController = new ConverterController();
        this.iController = new ImageRetrieverController(ctx);
        this.signView = new SignView(ctx);
        this.listener = listener;
    }

    public void getBSL(String text) {
        //set up loading screen by sending intent to speech view

        ArrayList<String> BSLSentence = this.cController.convertSentence(text);
        ArrayList<JSONObject> tags = this.cController.getTags();
        ArrayList<String> splitSentence = new ArrayList<>(Arrays.asList(text.split(" ")));
        ArrayList<Image> BSLSigns = this.iController.getImageSentence(BSLSentence, tags, splitSentence);
        this.showOutput(BSLSigns);
    }

    private void showOutput(ArrayList<Image> signs) {
        //switch activity
        this.listener.onSuccess();
        this.signView.showSequence(signs);
    }

}
