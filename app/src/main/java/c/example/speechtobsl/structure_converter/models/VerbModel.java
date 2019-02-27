package c.example.speechtobsl.structure_converter.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import c.example.speechtobsl.structure_converter.entities.VerbPhrase;

import static c.example.speechtobsl.structure_converter.utils.POS.ADV;
import static c.example.speechtobsl.structure_converter.utils.POS.MDL;

public class VerbModel {

    private ArrayList<JSONObject> POSTags;
    private ArrayList<JSONObject> parse;
    private ArrayList<String> englishText;

    ParseModel parser;
    TagModel tagger;

    private final ArrayList<String> excludedAdverbs = new ArrayList<>(Arrays.asList("N'T","NOT"));

    public VerbModel(ArrayList<JSONObject> tags, ArrayList<JSONObject> parse, ArrayList<String> sentence) {
        this.POSTags = tags;
        this.parse = parse;
        this.englishText = sentence;
        this.tagger = new TagModel(this.POSTags,this.englishText);
        this.parser = new ParseModel(this.parse,this.tagger);
    }

    public VerbPhrase createVP(ArrayList<String> clauseWords, String verb, Boolean isModal, Boolean isPrep) {
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
        VP.setIsModal(isModal);
        //Prepverb
        VP.setPrepVerb(isPrep);
        //Adverbs
        ArrayList<String> adverbs = this.parser.findLinks(clauseWords, verb, ADV);
        ArrayList<String> newAdverbs = new ArrayList<>();
        for(String adverb : adverbs) {
            if(this.excludedAdverbs.contains(adverb.toUpperCase())) {
                VP.setNegated(true);
            } else {
                newAdverbs.add(adverb);
            }
        }
        VP.setAdverbs(newAdverbs);
        //System.out.println("VP: " + VP.toArrayString());
        return VP;
    }

    /**
     * Used to check that there are no Verb Phrases within the clause that shouldn't be signed
     * These include verbs that only demonstrate the tense of the clause or auxiliary verbs that
     * shouldn't be signed
     * Only valid if there's more than one verb in a clause
     * @param VPs all verb phrases proposed for the clause
     * @return List of verbs that should be signed
     */
    public ArrayList<VerbPhrase> checkVerbs(ArrayList<VerbPhrase> VPs) {
        ArrayList<String> excluded = new ArrayList<>();
        excluded.add("used");
        excluded.add("will");
        ArrayList<VerbPhrase> newVPs = new ArrayList<>();
        if(VPs.size() > 1) {
            ArrayList<String> auxVerbs = this.getAuxVerbs();
            excluded.addAll(auxVerbs);
            for(VerbPhrase VP : VPs) {
                if(!excluded.contains(VP.getVerb())) {
                    newVPs.add(VP);
                }
            }
        } else {
            newVPs.addAll(VPs);
        }
        return newVPs;
    }

    private ArrayList<String> getAuxVerbs() {
        ArrayList<String> auxVerbs = new ArrayList<>();
        for(int i = 0; i < this.parse.size(); i++) {
            JSONObject current = this.parse.get(i);
            try {
                String dep = current.getString("dep");
                if(dep.contains("aux")) {
                    String verb = current.getString("dependentGloss");
                    if(!tagger.getGeneralTag(verb,true).equals(MDL)) {
                        auxVerbs.add(verb);
                    }
                }
            } catch(JSONException e) {
                System.out.println("Unable to find auxiliary verbs: " + e.getMessage());
            }
        }
        return auxVerbs;
    }
}