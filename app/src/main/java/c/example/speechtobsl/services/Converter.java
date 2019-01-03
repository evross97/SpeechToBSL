package c.example.speechtobsl.services;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import c.example.structureconverter.datatypes.Adjective;
import c.example.structureconverter.datatypes.Clause;
import c.example.structureconverter.datatypes.NounPhrase;
import c.example.structureconverter.datatypes.VerbPhrase;

import static c.example.speechtobsl.services.Converter.POS.ADJ;
import static c.example.speechtobsl.services.Converter.POS.ADV;
import static c.example.speechtobsl.services.Converter.POS.CONN;
import static c.example.speechtobsl.services.Converter.POS.DET;
import static c.example.speechtobsl.services.Converter.POS.MDL;
import static c.example.speechtobsl.services.Converter.POS.NA;
import static c.example.speechtobsl.services.Converter.POS.NOUN;
import static c.example.speechtobsl.services.Converter.POS.PREP;
import static c.example.speechtobsl.services.Converter.POS.QTN;
import static c.example.speechtobsl.services.Converter.POS.VRB;

public class Converter {

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

    enum POS{
        NOUN, VRB, DET, ADJ, ADV, CONN, TIM, MDL, QTN, PREP, NA
    }

    private final ArrayList<String> excludedVerbs = new ArrayList<>(Arrays.asList("IS", "ARE", "WERE"));

    private Context appCtx;
    private ArrayList<JSONObject> POSTags;
    private ArrayList<JSONObject> parse;
    private ArrayList<String> englishText;

    private ArrayList<Clause> clauses;


    public Converter(Context ctx) {
        this.appCtx = ctx;
        this.clauses = new ArrayList<>();
        this.POSTags = new ArrayList<>();
        this.parse = new ArrayList<>();
    }

    public void convertSentence(JSONObject englishParsedText, String originalText) {
        this.POSTags.clear();
        this.parse.clear();
        this.clauses.clear();
        this.extractData(englishParsedText, originalText);
        this.createClauses();
        String BSLText = mergeClauses();
        System.out.println(BSLText);
        Intent localIntent = new Intent("text-convert");
        localIntent.putExtra("text-convert-done", BSLText);
        LocalBroadcastManager.getInstance(appCtx.getApplicationContext()).sendBroadcast(localIntent);
    }

    private void extractData(JSONObject englishParsedText, String originalText) {
        this.englishText = new ArrayList<>(Arrays.asList(originalText.split(" ")));
        try {
            JSONArray sentences = (JSONArray) englishParsedText.get("sentences");
            JSONObject sentence = (JSONObject) sentences.get(0);
            System.out.println(sentence.toString(2));
            this.POSTags = this.toArrayList((JSONArray)sentence.get("tokens"));
            this.parse = this.toArrayList((JSONArray)sentence.get("enhancedPlusPlusDependencies"));
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
        ArrayList<NounPhrase> NPs = new ArrayList<>();
        ArrayList<VerbPhrase> VPs = new ArrayList<>();
        ArrayList<String> preps = new ArrayList<>();
        Integer clauseIndex = 0;
        Clause currentClause = new Clause(clauseIndex);
        for(int i=0; i < this.englishText.size(); i++) {
            String word = this.englishText.get(i);
            POS tag = this.getTag(word);
            switch (tag) {
                case NOUN:
                    NounPhrase newNP = this.createNP(word);
                    NPs.add(newNP);
                    break;
                case VRB:
                    if(!this.excludedVerbs.contains(word.toUpperCase())) {
                        VerbPhrase newVP = this.createVP(word, false);
                        VPs.add(newVP);
                    }
                    break;
                case PREP:
                    preps.add(word);
                    break;
                case TIM:
                    currentClause.setTimeFrame(word);
                    break;
                case QTN:
                    currentClause.setQuestion(word);
                    break;
                case CONN:
                    currentClause.setNPs(NPs);
                    if(VPs.size() > 0) {
                        currentClause.setVPs(VPs);
                    } else {
                        currentClause.setVPs(this.prepForVP(preps));
                    }
                    currentClause = this.checkForTense(currentClause);
                    this.clauses.add(currentClause);
                    clauseIndex+=1;
                    currentClause = new Clause(clauseIndex);
                    currentClause.setConnector(word);
                    if(word.equals("that")) {
                        currentClause.setBeforeConnector(true);
                    } else {
                        currentClause.setBeforeConnector(false);
                    }
                    break;
            }

        }
        currentClause.setNPs(NPs);
        if(VPs.size() > 0) {
            currentClause.setVPs(VPs);
        } else {
            currentClause.setVPs(this.prepForVP(preps));
        }
        currentClause = this.checkForTense(currentClause);
        this.clauses.add(currentClause);
    }

    private String mergeClauses() {
        ArrayList<String> sentence = new ArrayList<>();
        this.clauses.forEach((clause) -> sentence.addAll(clause.toArrayString()));
        return sentence.toString();
    }

    private POS getTag(String word) {
        POS finalTag = NA;
        try {
            for(int i = 0; i < this.POSTags.size(); i++) {
                JSONObject currentTag = this.POSTags.get(i);
                String name = currentTag.getString("word");
                if(name.equals(word)) {
                    String pos = currentTag.getString("pos");
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
                        finalTag = QTN;
                    }
                    break;
                }
            }
        } catch(JSONException e) {
            System.out.println("Can't get tag for " + word + ": " + e.getMessage());
        }
        return finalTag;
    }

    private NounPhrase createNP(String noun) {
        NounPhrase NP = new NounPhrase(noun);
        //Adjectives
        ArrayList<String> stringAdjs = this.findLinks(noun, ADJ);
        ArrayList<Adjective> adjs = new ArrayList<>();
        for(int i = 0; i < stringAdjs.size(); i++) {
            String currentAdj = stringAdjs.get(i);
            ArrayList<String> advs = this.findLinks(currentAdj, ADV);
            Adjective adj = new Adjective(currentAdj, advs);
            adjs.add(adj);
        }
        NP.setAdjectives(adjs);

        //Det
        ArrayList<String> dets = this.findLinks(noun, DET);
        if(!dets.isEmpty()) {
            NP.setDeterminer(dets.get(0));
        }
        //Prep
        ArrayList<String> preps = this.findLinks(noun, PREP);
        if(!preps.isEmpty()) {
            NP.setPreposition(preps.get(0));
        }
        //Plural
        Boolean plural = false;
        for(int k = 0; k < this.POSTags.size(); k++) {
            JSONObject currentTag = this.POSTags.get(k);
            try {
                String name = currentTag.getString("word");
                if (name.equals(noun)) {
                    String pos = currentTag.getString("pos");
                    if (pos.equals("NNS") || pos.equals("NNPS")) {
                        plural = true;
                        NP.setNoun(this.getSingular(noun));
                        break;
                    }
                }
            } catch(JSONException e) {
                System.out.println("Unable to check if noun is plural: " + e.getMessage());
            }
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
        System.out.println(NP.toArrayString());
        return NP;
    }

    private VerbPhrase createVP(String verb, Boolean isPrep) {
        VerbPhrase VP = new VerbPhrase(verb);
        //Modal
        ArrayList<String> modals = this.findLinks(verb, MDL);
        if(modals.size() > 0) {
            VP.setModal(modals.get(0));
        }
        //Prepverb
        VP.setPrepVerb(isPrep);
        //Adverbs
        VP.setAdverbs(this.findLinks(verb, ADV));
        System.out.println(VP.toArrayString());
        return VP;
    }

    private ArrayList<String> findLinks(String word, POS typeWanted) {
        ArrayList<JSONObject> possibleLinks = new ArrayList<>();
        ArrayList<String> linkedWords = new ArrayList<>();
        //get all related words to the word passed
        for(int i =0; i < this.parse.size(); i++) {
            JSONObject currentParse = this.parse.get(i);
            try {
                String governorWord = currentParse.getString("governorGloss");
                String dependentWord = currentParse.getString("dependentGloss");
                if(governorWord.equals(word) || dependentWord.equals(word)) {
                    possibleLinks.add(currentParse);
                }

            } catch(JSONException e) {
                System.out.println("Failed to extract governor from parse: " + e.getMessage());
            }
        }
        //check which related words are of the correct type
        for(int j = 0; j < possibleLinks.size(); j++) {
            JSONObject currentLink = possibleLinks.get(j);
            try {
                String currentName = currentLink.getString("dependentGloss");
                if(currentName.equals(word)) {
                    currentName = currentLink.getString("governorGloss");
                }
                POS tag = this.getTag(currentName);
                if(tag.equals(typeWanted)) {
                    linkedWords.add(currentName);
                }
            } catch(JSONException e) {
                System.out.println("Failed to extract dependent fro parse: " + e.getMessage());
            }
        }

        return linkedWords;
    }

    private ArrayList<VerbPhrase> prepForVP(ArrayList<String> possibles) {
        ArrayList<VerbPhrase> VPs = new ArrayList<>();
        if(possibles.size() > 0) {
            for(int i = 0; i < possibles.size(); i++) {
                VerbPhrase newVP = this.createVP(possibles.get(i),true);
                VPs.add(newVP);
            }
        }
        return VPs;

    }

    private String getSingular(String plural) {
        String sing = "";
        try {
            for(int i = 0; i < this.POSTags.size(); i++) {
                JSONObject currentTag = this.POSTags.get(i);
                String name = currentTag.getString("word");
                if (name.equals(plural)) {
                    sing = currentTag.getString("lemma");
                    break;
                }
            }
        } catch (JSONException e) {
            System.out.println("Failed to get singular of noun: " + e.getMessage());
        }

        return sing;
    }

    private Clause checkForTense(Clause clause) {
        if(clause.getTimeFrame().equals("")) {

        }
        return clause;
    }

}
