package c.example.speechtobsl.structure_converter.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import c.example.speechtobsl.structure_converter.entities.Clause;
import c.example.speechtobsl.structure_converter.entities.NounPhrase;
import c.example.speechtobsl.structure_converter.entities.VerbPhrase;
import c.example.speechtobsl.structure_converter.utils.POS;

public class ClauseModel {

    private final ArrayList<String> excludedVerbs = new ArrayList<>(Arrays.asList("IS", "ARE", "WERE"));

    private ArrayList<JSONObject> POSTags;
    private ArrayList<JSONObject> parse;
    private ArrayList<String> englishText;

    private TagModel tagger;
    private NounModel nModel;
    private VerbModel vModel;

    public ClauseModel(ArrayList<JSONObject> tags, ArrayList<JSONObject> parse, ArrayList<String> sentence) {
        this.POSTags = tags;
        this.parse = parse;
        this.englishText = sentence;
        this.tagger = new TagModel(this.POSTags,this.englishText);
        this.nModel = new NounModel(this.POSTags,this.parse,this.englishText);
        this.vModel = new VerbModel(this.POSTags,this.parse,this.englishText);
    }

    public Clause createClause(ArrayList<String> clauseWords, Integer clauseIndex) {
        ArrayList<NounPhrase> NPs = new ArrayList<>();
        ArrayList<VerbPhrase> VPs = new ArrayList<>();
        ArrayList<String> preps = new ArrayList<>();
        Clause clause = new Clause(clauseIndex);
        for(int i=0; i < clauseWords.size(); i++) {
            String word = clauseWords.get(i);
            POS tag = this.tagger.getGeneralTag(word,true);
            switch (tag) {
                case NOUN:
                    NounPhrase newNP = this.nModel.createNP(word);
                    NPs.add(newNP);
                    break;
                case VRB:
                    if(!this.excludedVerbs.contains(word.toUpperCase())) {
                        VerbPhrase newVP = this.vModel.createVP(word, false);
                        VPs.add(newVP);
                    }
                    break;
                case PREP:
                    preps.add(word);
                    break;
                case TIM:
                    clause.setTimeFrame(word);
                    break;
                case QTN:
                    clause.setQuestion(word);
                    break;
                case CONN:
                    clause.setConnector(word);
                    if(word.equals("that")) {
                        clause.setBeforeConnector(true);
                    } else {
                        clause.setBeforeConnector(false);
                    }
                    break;
            }

        }
        clause = this.addToClause(clause, NPs, VPs, preps);
        //System.out.println("Clause: " + currentClause.toArrayString());
        //System.out.println("New Clause: " + currentClause.toArrayString());
        return clause;
    }

    private Clause addToClause(Clause clause, ArrayList<NounPhrase> NPs, ArrayList<VerbPhrase> VPs, ArrayList<String> preps) {
        if(VPs.size() > 0) {
            clause.setVPs(VPs);
            clause.setNPs(NPs);
        } else {
            clause.setVPs(this.prepForVP(preps));
            clause.setNPs(this.nModel.removePreps(NPs, preps));
        }
        clause = this.checkForTense(clause);
        return clause;
    }

    private Clause checkForTense(Clause clause) {
        if(clause.getTimeFrame().equals("")) {
            Boolean past = false;
            try {
                for(VerbPhrase VP: clause.getVPs()) {
                    JSONObject currentTag = this.tagger.getExactTag(VP.getVerb(), true);
                    String pos = currentTag.getString("pos");
                    if(pos.equals("VBD") | pos.equals("VBN")) {
                        past = true;
                        break;
                    }
                }
            } catch (JSONException e) {
                System.out.println("Failed to check tense of verbs: " + e.getMessage());
            }
            if(past) {
                clause.setTimeFrame("BEFORE");
            }
        }
        return clause;
    }

    private ArrayList<VerbPhrase> prepForVP(ArrayList<String> possibles) {
        ArrayList<VerbPhrase> VPs = new ArrayList<>();
        if(possibles.size() > 0) {
            for(int i = 0; i < possibles.size(); i++) {
                VerbPhrase newVP = this.vModel.createVP(possibles.get(i),true);
                VPs.add(newVP);
            }
        }
        return VPs;

    }
}
