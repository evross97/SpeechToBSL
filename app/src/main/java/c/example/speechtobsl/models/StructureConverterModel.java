package c.example.speechtobsl.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import c.example.speechtobsl.structure_converter.models.ClauseModel;
import c.example.speechtobsl.structure_converter.models.TagModel;
import c.example.speechtobsl.structure_converter.utils.POS;


/**
 * The model that converts the English sentence into a BSL sentence.
 */
public class StructureConverterModel {

    private ArrayList<JSONObject> POSTags;
    private ArrayList<JSONObject> parse;
    private ArrayList<String> englishText;

    private TagModel tagger;
    private ClauseModel cModel;

    /**
     * Instantiates a new structure converter model.
     */
    public StructureConverterModel() {
        this.POSTags = new ArrayList<>();
        this.parse = new ArrayList<>();
    }

    /**
     * Gets all the POS tags for the current sentence
     *
     * @return the POS tags
     */
    public ArrayList<JSONObject> getPOSTags() {
        return this.POSTags;
    }

    /**
     * Converts an English sentence into a BSL sentence
     *
     * @param englishParsedText the parsed English sentence
     * @param originalText      the original English sentence
     * @return the BSL sentence
     */
    public ArrayList<String> convertSentence(JSONObject englishParsedText, String originalText) {
        this.POSTags.clear();
        this.parse.clear();
        this.extractData(englishParsedText, originalText);
        this.tagger = new TagModel(this.POSTags, this.englishText);
        this.cModel = new ClauseModel(this.POSTags,this.parse,this.englishText);
        return this.createClauses();
    }

    /**
     * Extracts the dependency parse and the POS tags from the server response
     *
     * @param englishParsedText
     * @param originalText
     */
    private void extractData(JSONObject englishParsedText, String originalText) {
        this.englishText = new ArrayList<>(Arrays.asList(originalText.split(" ")));
        try {
            JSONArray sentences = (JSONArray) englishParsedText.get("sentences");
            JSONObject sentence = (JSONObject) sentences.get(0);
            this.POSTags = this.toArrayList((JSONArray)sentence.get("tokens"));
            this.parse = this.toArrayList((JSONArray)sentence.get("enhancedPlusPlusDependencies"));
        } catch(JSONException e) {
            System.out.println("Failed to extract tags and parse of sentence: " + e.getMessage());
        }
    }

    /**
     * Converts a JSON array to an array of JSON objects
     * Used to manipulate the POS tags and parse into more manageable data structures
     * @param jsonArray
     * @return the array of objects
     */
    private ArrayList<JSONObject> toArrayList(JSONArray jsonArray) {
        ArrayList<JSONObject> arrayList = new ArrayList<>();
        for(int i = 0; i < jsonArray.length(); i++) {
            try {
                arrayList.add((JSONObject)jsonArray.get(i));
            } catch(JSONException e) {
                System.out.println("Failed to convert to arraylist: " + e.getMessage());
            }
        }
        return arrayList;
    }

    /**
     * Creates BSL clauses from the English sentence
     * Adds all words to a list and once a connective/the end of the sentence is reached, a new clause is created from the list
     */
    private ArrayList<String> createClauses() {
        ArrayList<String> sentence = new ArrayList<>();
        ArrayList<String> currentClauseWords = new ArrayList<>();
        Integer clauseIndex = 0;
        for(int i=0; i < this.englishText.size(); i++) {
            String word = this.englishText.get(i);
            POS tag = this.tagger.getGeneralTag(word, true);
            switch (tag) {
                case CONN:
                    sentence.addAll(this.cModel.createClause(currentClauseWords,clauseIndex).toArrayString());
                    currentClauseWords.clear();
                    currentClauseWords.add(word);
                    clauseIndex++;
                    break;
                default:
                    currentClauseWords.add(word);
            }
        }
        sentence.addAll(this.cModel.createClause(currentClauseWords,clauseIndex).toArrayString());
        return sentence;
    }
}
