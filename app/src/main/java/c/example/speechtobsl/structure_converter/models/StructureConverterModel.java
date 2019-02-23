package c.example.speechtobsl.structure_converter.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import c.example.speechtobsl.structure_converter.utils.POS;


public class StructureConverterModel {

    /**
     * Till Connector is found:
     * - Look at next word in sentence and analyse accordingly
     * - If connector is found - add everything found so far to clause
     *
     * NPs
     * 1. Find all nouns
     * 2. For each noun - find all POS that are linked to that noun
     * 3. Create NP from all above info
     *
     * VPs
     * 1. Find all verbs
     * 2. For each verb - find all POS that are linked to that verb
     * 3. Create VP from all above
     *
     * Clauses
     * 1. Add connector
     * 2. Add NP list
     * 3. Add VP list
     * 4. Add index to clause
     * 5. Add  time frame
     *
     * Join Clauses
     */


    private ArrayList<JSONObject> POSTags;
    private ArrayList<JSONObject> parse;
    private ArrayList<String> englishText;
    private ArrayList<String> sentence;

    private TagModel tagger;
    private ClauseModel cModel;

    public StructureConverterModel() {
        this.sentence = new ArrayList<>();
        this.POSTags = new ArrayList<>();
        this.parse = new ArrayList<>();

    }

    public ArrayList<String> convertSentence(JSONObject englishParsedText, String originalText) {
        this.POSTags.clear();
        this.parse.clear();
        this.sentence.clear();
        this.extractData(englishParsedText, originalText);
        this.tagger = new TagModel(this.POSTags, this.englishText);
        this.cModel = new ClauseModel(this.POSTags,this.parse,this.englishText);
        this.createClauses();
        return this.sentence;
    }

    private void extractData(JSONObject englishParsedText, String originalText) {
        this.englishText = new ArrayList<>(Arrays.asList(originalText.split(" ")));
        try {
            JSONArray sentences = (JSONArray) englishParsedText.get("sentences");
            JSONObject sentence = (JSONObject) sentences.get(0);
            this.POSTags = this.toArrayList((JSONArray)sentence.get("tokens"));
            System.out.println(this.POSTags);
            this.parse = this.toArrayList((JSONArray)sentence.get("enhancedPlusPlusDependencies"));
            System.out.println(this.englishText);

        } catch(JSONException e) {
            System.out.println("Failed to extract tags and parse of sentence: " + e.getMessage());
        }
    }

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

    private void createClauses() {
        ArrayList<String> currentClauseWords = new ArrayList<>();
        Integer clauseIndex = 0;
        for(int i=0; i < this.englishText.size(); i++) {
            String word = this.englishText.get(i);
            POS tag = this.tagger.getGeneralTag(word);
            switch (tag) {
                case CONN:
                    currentClauseWords.add(word);
                    this.sentence.addAll(this.cModel.createClause(currentClauseWords,clauseIndex).toArrayString());
                    currentClauseWords.clear();
                    clauseIndex++;
                    break;
                default:
                    currentClauseWords.add(word);
            }
        }
        this.sentence.addAll(this.cModel.createClause(currentClauseWords,clauseIndex).toArrayString());
    }
}
