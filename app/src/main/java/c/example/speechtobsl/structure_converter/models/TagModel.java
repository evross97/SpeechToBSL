package c.example.speechtobsl.structure_converter.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import c.example.speechtobsl.structure_converter.utils.POS;

import static c.example.speechtobsl.structure_converter.utils.POS.*;

public class TagModel {

    private ArrayList<JSONObject> POSTags;
    private ArrayList<String> englishText;

    public TagModel(ArrayList<JSONObject> tags, ArrayList<String> sentence){
        this.POSTags = tags;
        this.englishText = sentence;
    }

    public JSONObject getExactTag(String word, Boolean original) {
        JSONObject finalTag = new JSONObject();
        for(int i = 0; i < this.POSTags.size(); i++) {
            JSONObject currentTag = this.POSTags.get(i);
            try {
                String name;
                if(original) {
                    name = currentTag.getString("word");

                } else {
                    name = currentTag.getString("lemma");
                }
                if (name.equals(word)) {
                    finalTag = currentTag;
                    break;
                }
            } catch(JSONException e) {
                System.out.println("Can't get exact tag for " + word + ": " + e.getMessage());
            }
        }
        return finalTag;
    }

    public POS getGeneralTag(String word, Boolean original) {
        POS finalTag = NA;
        try {
            JSONObject tag = this.getExactTag(word, original);
            String ner = tag.getString("ner");
            if(ner.equals("DATE")) {
                finalTag = TIM;
            } else {
                String pos = tag.getString("pos");
                if(pos.equals("CC")) {
                    finalTag = CONN;
                }
                if(pos.equals("DT")|| pos.contains("PRP")) {
                    finalTag = DET;
                }
                if(pos.contains("JJ")) {
                    finalTag = ADJ;
                }
                if(pos.equals("IN") || pos.equals("TO")) {
                    finalTag = PREP;
                }
                if(pos.equals("MD")) {
                    finalTag = MDL;
                }
                if(pos.contains("NN") || pos.equals("PRP")) {
                    finalTag = NOUN;
                }
                if(pos.startsWith("RB")) {
                    finalTag = ADV;
                }
                if(pos.contains("VB")) {
                    finalTag = VRB;
                }
                if(pos.startsWith("W")) {
                    if(englishText.get(0).equals(word)) {
                        finalTag = QTN;
                    } else {
                        finalTag = CONN;
                    }
                    //System.out.println(finalTag);
                }

            }
        } catch(JSONException e) {
            System.out.println("Can't get general tag for " + word + ": " + e.getMessage());
        }
        return finalTag;
    }
}
