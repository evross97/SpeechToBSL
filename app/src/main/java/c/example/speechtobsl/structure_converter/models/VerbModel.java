package c.example.speechtobsl.structure_converter.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import c.example.speechtobsl.structure_converter.entities.VerbPhrase;

import static c.example.speechtobsl.structure_converter.utils.POS.ADV;
import static c.example.speechtobsl.structure_converter.utils.POS.MDL;

public class VerbModel {

    private ArrayList<JSONObject> POSTags;
    private ArrayList<JSONObject> parse;
    private ArrayList<String> englishText;

    ParseModel parser;
    TagModel tagger;

    public VerbModel(ArrayList<JSONObject> tags, ArrayList<JSONObject> parse, ArrayList<String> sentence) {
        this.POSTags = tags;
        this.parse = parse;
        this.englishText = sentence;
        this.tagger = new TagModel(this.POSTags,this.englishText);
        this.parser = new ParseModel(this.parse,this.tagger);
    }

    public VerbPhrase createVP(String verb, Boolean isPrep) {
        VerbPhrase VP = new VerbPhrase(verb);
        //Get lemma of verb
        try {
            JSONObject currentTag = this.tagger.getExactTag(verb, true);
            String lemma = currentTag.getString("lemma");
            VP.setLemmaVerb(lemma);
        } catch(JSONException e) {
            System.out.println("Couldn't get lemma of verb");
        }
        //Modal
        ArrayList<String> modals = this.parser.findLinks(verb, MDL);
        if(modals.size() > 0) {
            VP.setModal(modals.get(0));
        }
        //Prepverb
        VP.setPrepVerb(isPrep);
        //Adverbs
        VP.setAdverbs(this.parser.findLinks(verb, ADV));
        //System.out.println("VP: " + VP.toArrayString());
        return VP;
    }
}
