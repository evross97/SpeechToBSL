package c.example.speechtobsl.structure_converter.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import c.example.speechtobsl.structure_converter.entities.Adjective;
import c.example.speechtobsl.structure_converter.entities.NounPhrase;

import static c.example.speechtobsl.structure_converter.utils.POS.ADJ;
import static c.example.speechtobsl.structure_converter.utils.POS.ADV;
import static c.example.speechtobsl.structure_converter.utils.POS.DET;
import static c.example.speechtobsl.structure_converter.utils.POS.PREP;

public class NounModel {

    private ArrayList<JSONObject> POSTags;
    private ArrayList<JSONObject> parse;
    private ArrayList<String> englishText;

    private TagModel tagger;
    private ParseModel parser;

    public NounModel(ArrayList<JSONObject> tags, ArrayList<JSONObject> parse, ArrayList<String> sentence){
        this.POSTags = tags;
        this.parse = parse;
        this.englishText = sentence;
        this.tagger = new TagModel(this.POSTags, this.englishText);
        this.parser = new ParseModel(this.parse, this.tagger);
    }

    public NounPhrase createNP(String noun) {
        NounPhrase NP = new NounPhrase(noun);
        //Adjectives
        ArrayList<String> stringAdjs = this.parser.findLinks(noun, ADJ);
        ArrayList<Adjective> adjs = new ArrayList<>();
        for(int i = 0; i < stringAdjs.size(); i++) {
            String currentAdj = stringAdjs.get(i);
            ArrayList<String> advs = this.parser.findLinks(currentAdj, ADV);
            Adjective adj = new Adjective(currentAdj, advs);
            adjs.add(adj);
        }
        NP.setAdjectives(adjs);

        //Det
        ArrayList<String> dets = this.parser.findLinks(noun, DET);
        if(!dets.isEmpty()) {
            NP.setDeterminer(dets.get(0));
        }
        //Prep
        ArrayList<String> preps = this.parser.findLinks(noun, PREP);
        if(!preps.isEmpty()) {
            NP.setPreposition(preps.get(0));
        }
        //Plural
        Boolean plural = false;
        try{
            JSONObject currentTag = this.tagger.getExactTag(noun, true);
            String pos = currentTag.getString("pos");
            if (pos.equals("NNS") || pos.equals("NNPS")) {
                plural = true;
                NP.setNoun(this.getSingular(noun));
            }
        } catch(JSONException e) {
            System.out.println("Unable to check if noun ("+ noun + ") is plural: " + e.getMessage());
        }
        NP.setPlural(plural);

        //IsSubject
        Boolean nsubj = false;
        for(int j = 0; j < this.parse.size(); j++) {
            JSONObject currentParse = this.parse.get(j);
            try {
                String type = currentParse.getString("dep");
                if(type.equals("nsubj")) {
                    String dependent = currentParse.getString("dependentGloss");
                    if(dependent.equals(noun)) {
                        nsubj = true;
                        break;
                    }
                }

            } catch(JSONException e) {
                System.out.println("Failed to extract governor from parse: " + e.getMessage());
            }
        }
        NP.setSubject(nsubj);
        //System.out.println("NP: " + NP.toArrayString());
        return NP;
    }

    private String getSingular(String plural) {
        String sing = "";
        try {
            JSONObject currentTag = this.tagger.getExactTag(plural,true);
            sing = currentTag.getString("lemma");
        } catch (JSONException e) {
            System.out.println("Failed to get singular of noun: " + e.getMessage());
        }
        return sing;
    }

    public ArrayList<NounPhrase> removePreps(ArrayList<NounPhrase> NPs, ArrayList<String> preps) {
        for(NounPhrase NP : NPs) {
            //why is preps plural???
        }
        return NPs;
    }
}
