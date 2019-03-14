package c.example.speechtobsl.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;

import java.util.ArrayList;

import c.example.speechtobsl.controllers.MainController;
import c.example.speechtobsl.outer_framework.EndListener;

/**
 * The view that receives instructions from the Speech Actiivty
 */
public class SpeechView implements RecognitionListener {

    private SpeechRecognizer speech;
    private Intent recogniserIntent;
    private String decodedSpeech;
    private MainController mController;
    private EndListener listener;

    private Context appCtx;

    private final String LOG_TAG = "BSL App - SpeechView";

    /**
     * Instantiates a new Speech view.
     *
     * @param ctx      the context - needs to be sent to the main controller
     * @param listener the success listener - needs to be sent to the main controller
     */
    public SpeechView(Context ctx, EndListener listener) {
        this.appCtx = ctx;
        this.listener = listener;
        mController = new MainController(appCtx, listener);
        speech = SpeechRecognizer.createSpeechRecognizer(appCtx);
        speech.setRecognitionListener(this);
        recogniserIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recogniserIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "en");
        recogniserIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
    }

    /**
     * Android speech recognizer method
     */
    @Override
    public void onBeginningOfSpeech() {
        Log.i(LOG_TAG, "onBeginningOfSpeech");
    }

    /**
     * Android speech recognizer method
     */
    @Override
    public void onRmsChanged(float v) {
        Log.i(LOG_TAG, "onRmsChanged");
    }

    /**
     * Android speech recognizer method
     */
    @Override
    public void onBufferReceived(byte[] bytes) {
        Log.i(LOG_TAG, "onBufferReceived: " + bytes);
    }

    /**
     * Android speech recognizer method
     */
    @Override
    public void onEndOfSpeech() {
        Log.i(LOG_TAG, "onEndOfSpeech");
    }

    /**
     * Android speech recognizer method
     */
    @Override
    public void onError(int code) {
        Log.i(LOG_TAG, "FAILED with error code " + getErrorText(code));
        this.listener.onFailure();
    }

    /**
     * Android speech recognizer method
     */
    @Override
    public void onReadyForSpeech(Bundle arg) {
        Log.i(LOG_TAG, "onReadyForSpeech");
    }

    /**
     * Android speech recognizer method
     */
    @Override
    public void onPartialResults(Bundle arg) {
        Log.i(LOG_TAG, "onPartialResults");
    }

    /**
     * Android speech recognizer method
     */
    @Override
    public void onEvent(int i, Bundle bundle) {
        Log.i(LOG_TAG, "onEvent");
    }

    /**
     * Android speech recognizer method
     * Sends request to main controller to begin translation of text
     */
    @Override
    public void onResults(Bundle data) {
        ArrayList<String> matches = data.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        decodedSpeech = matches.get(0);
        mController.getBSL(decodedSpeech);
    }

    /**
     * Tells main controller to replay the previously shown sign sequence
     */
    public void replaySequence() {
        this.mController.replaySequence();
    }

    /**
     * Updates speed of sign sequence
     */
    public void updateSpeed(Integer speed) {
        this.mController.updateSpeed(speed);
    }

    /**
     * Starts the recorder.
     */
    public void startListening() {
        speech.startListening(recogniserIntent);
    }

    /**
     * Stops the recorder.
     */
    public void stopListening() {
        speech.stopListening();
    }

    /**
     * Gets error text for printing
     *
     * @param errorCode the error code
     * @return the error text
     */
    public static String getErrorText(int errorCode) {
        String message;
        switch (errorCode) {
            case SpeechRecognizer.ERROR_AUDIO:
                message = "Audio recording error";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                message = "Client side error";
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                message = "Insufficient permissions";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                message = "Network error";
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = "Network timeout";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                message = "No match";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                message = "RecognitionService busy";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                message = "error from server";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                message = "No speech input";
                break;
            default:
                message = "Didn't understand, please try again.";
                break;
        }
        return message;
    }
}
