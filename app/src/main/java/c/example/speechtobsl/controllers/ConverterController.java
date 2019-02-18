package c.example.speechtobsl.controllers;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import c.example.speechtobsl.models.StructureConverterModel;
import c.example.speechtobsl.models.ParserModel;

public class ConverterController {

    private ParserModel parser;
    private StructureConverterModel sConverter;
    private final String LOG_TAG = "BSL - ConverterController";


    public ConverterController() {
        this.parser = new ParserModel();
        this.sConverter = new StructureConverterModel();
    }

    public ArrayList<String> convertSentence(String text) {
        String parsedText = this.parser.getParse(text);
        try {
            JSONObject parsedObj = new JSONObject(parsedText);
            ArrayList<String> BSLText = this.sConverter.convertSentence(parsedObj, text);
            return BSLText;
        } catch(JSONException e) {
            Log.i(LOG_TAG, "Unable to convert parsed text to JSON: " + e.getMessage());
        }
        return null;
    }
}
