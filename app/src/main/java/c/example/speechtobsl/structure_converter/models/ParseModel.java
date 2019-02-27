package c.example.speechtobsl.structure_converter.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import c.example.speechtobsl.structure_converter.utils.POS;

public class ParseModel {

    private ArrayList<JSONObject> parse;

    private TagModel tagger;

    public ParseModel(ArrayList<JSONObject> parse, TagModel tagger) {
        this.parse = parse;
        this.tagger = tagger;
    }

    public ArrayList<String> findLinks(ArrayList<String> clauseWords, String word, POS typeWanted) {
        ArrayList<JSONObject> possibleLinks = getLinkedParses(word);
        ArrayList<String> linkedWords = new ArrayList<>();
        //check which related words are of the correct type
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
                System.out.println("Failed to extract relevant dependencies from parse: " + e.getMessage());
            }
        }

        return linkedWords;
    }

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
                System.out.println("Failed to extract dependencies from parse: " + e.getMessage());
            }
        }
        return links;
    }
}
