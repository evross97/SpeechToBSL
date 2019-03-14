package c.example.speechtobsl.controllers;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import c.example.speechtobsl.models.StructureConverterModel;
import c.example.speechtobsl.models.StanfordParserModel;

/**
 * Converts an English sentence into a BSL sentence
 */
public class ConverterController {

    private StanfordParserModel parser;
    private StructureConverterModel sConverter;
    private final String LOG_TAG = "BSL App - ConverterController";


    /**
     * Instantiates a new Converter controller.
     */
    public ConverterController() {
        this.parser = new StanfordParserModel();
        this.sConverter = new StructureConverterModel();
    }

    /**
     * Gets POS tags for current sentence - required when dealing with synonyms
     *
     * @return POS Tags
     */
    public ArrayList<JSONObject> getTags() {
        return this.sConverter.getPOSTags();
    }

    /**
     * Converts English sentence to BSL sentence
     *
     * @param text English text
     * @return array of BSL words
     */
    public ArrayList<String> convertSentence(String text) {
        String parsedText = this.parser.getParse(text);
        try {
            JSONObject parsedObj = new JSONObject(parsedText);
            ArrayList<String> BSLText = this.sConverter.convertSentence(parsedObj, text);
            return BSLText;
        } catch(JSONException e) {
            Log.i(LOG_TAG, "Unable to convert parsed text to JSON: " + e.getMessage());
        }
        return new ArrayList<>();
    }
}
