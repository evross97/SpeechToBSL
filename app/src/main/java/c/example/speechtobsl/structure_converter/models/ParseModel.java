package c.example.speechtobsl.structure_converter.models;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import c.example.speechtobsl.structure_converter.utils.POS;

/**
 * Extracts required information from the dependency parse of the sentence
 */
public class ParseModel {

    private final String LOG_TAG = "BSL App - ParseModel";

    private ArrayList<JSONObject> parse;

    private TagModel tagger;

    /**
     * Instantiates a new parse model.
     *
     * @param parse  the parse of the sentence
     * @param tagger the tagger
     */
    public ParseModel(ArrayList<JSONObject> parse, TagModel tagger) {
        this.parse = parse;
        this.tagger = tagger;
    }

    /**
     * Finds all words of a particular POS type that are related to a given word
     * They must be in the same clause as the word
     *
     * @param clauseWords all the words in the current clause
     * @param word        the word
     * @param typeWanted  the POS type wanted
     * @return array of all relevant links
     */
    public ArrayList<String> findLinks(ArrayList<String> clauseWords, String word, POS typeWanted) {
        ArrayList<JSONObject> possibleLinks = getLinkedParses(word);
        ArrayList<String> linkedWords = new ArrayList<>();
        for(int j = 0; j < possibleLinks.size(); j++) {
            JSONObject currentLink = possibleLinks.get(j);
            try {
                String currentName = currentLink.getString("dependentGloss");
                if(currentName.equals(word)) {
                    currentName = currentLink.getString("governorGloss");
                }
                POS tag = this.tagger.getGeneralTag(currentName, true);
                if(tag.equals(typeWanted) && clauseWords.contains(currentName)) {
                    linkedWords.add(currentName);
                }
            } catch(JSONException e) {
                Log.i(LOG_TAG,"Failed to extract relevant dependencies from parse: " + e.getMessage());
            }
        }
        return linkedWords;
    }

    /**
     * Gets all words that are linked in any way to the given word
     *
     * @param word the word
     * @return list of all relevant parse objects
     */
    public ArrayList<JSONObject> getLinkedParses(String word) {
        ArrayList<JSONObject> links = new ArrayList<>();
        for(int i =0; i < this.parse.size(); i++) {
            JSONObject currentParse = this.parse.get(i);
            try {
                String governorWord = currentParse.getString("governorGloss");
                String dependentWord = currentParse.getString("dependentGloss");
                if(governorWord.equals(word) || dependentWord.equals(word)) {
                    links.add(currentParse);
                }

            } catch(JSONException e) {
                Log.i(LOG_TAG,"Failed to extract dependencies from parse: " + e.getMessage());
            }
        }
        return links;
    }
}
