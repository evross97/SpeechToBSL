package c.example.speechtobsl.controllers;

import android.content.Context;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import c.example.speechtobsl.entities.Image;
import c.example.speechtobsl.outer_framework.EndListener;
import c.example.speechtobsl.views.SignView;

/**
 * Runs main translation
 */
public class MainController {

    ConverterController cController;
    ImageRetrieverController iController;
    SignView signView;
    EndListener listener;

    /**
     * Instantiates a new Main controller.
     *
     * @param ctx      the context - needed for sign view and database
     * @param listener the success listener
     */
    public MainController(Context ctx, EndListener listener) {
        this.cController = new ConverterController();
        this.iController = new ImageRetrieverController(ctx);
        this.signView = new SignView(ctx);
        this.listener = listener;
    }

    /**
     * Sends requests to other controllers to translate the text to BSL and convert that into BSL signs
     *
     * @param text the spoken English converted to text
     */
    public void getBSL(String text) {
        ArrayList<String> BSLSentence = this.cController.convertSentence(text);
        ArrayList<JSONObject> tags = this.cController.getTags();
        ArrayList<String> splitSentence = new ArrayList<>(Arrays.asList(text.split(" ")));
        if(BSLSentence.size() > 0 ) {
            ArrayList<Image> BSLSigns = this.iController.getImageSentence(BSLSentence, tags, splitSentence);
            this.showOutput(BSLSigns);
        } else {
            this.listener.onFailure();
        }
    }

    /**
     * Triggers the change of activity and sends the BSL signs to be shown to the user
     * @param signs final signs
     */
    private void showOutput(ArrayList<Image> signs) {
        this.listener.onSuccess();
        this.signView.showSequence(signs);
    }

    /**
     * Tells the sign view to repeat the previous sequence
     */
    public void replaySequence() {
        this.signView.replaySequence();
    }

    /**
     * Tells the sign view the new speed to play the sequence at
     */
    public void updateSpeed(Integer speed) {
        this.signView.speedUpdate(speed);
    }
}
