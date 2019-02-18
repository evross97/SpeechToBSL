package c.example.speechtobsl.controllers;


import android.content.Context;

import java.util.ArrayList;

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
        ArrayList<Image> BSLSigns = this.iController.getImageSentence(BSLSentence);
        this.showOutput(BSLSigns);
    }

    private void showOutput(ArrayList<Image> signs) {
        //switch activity
        this.listener.onSuccess();
        this.signView.showSequence(signs);
    }

}
