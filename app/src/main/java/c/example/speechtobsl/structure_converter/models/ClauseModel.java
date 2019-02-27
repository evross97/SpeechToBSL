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

    private ArrayList<JSONObject> POSTags;
    private ArrayList<JSONObject> parse;
    private ArrayList<String> englishText;
    private ArrayList<String> clauseWords;

    private TagModel tagger;
    private NounModel nModel;
    private VerbModel vModel;

    private final ArrayList<String> excludedVerbs = new ArrayList<>(Arrays.asList("IS", "ARE", "WERE", "WAS"));

    public ClauseModel(ArrayList<JSONObject> tags, ArrayList<JSONObject> parse, ArrayList<String> sentence) {
        this.POSTags = tags;
        this.parse = parse;
        this.englishText = sentence;
        this.tagger = new TagModel(this.POSTags,this.englishText);
        this.nModel = new NounModel(this.POSTags,this.parse,this.englishText);
        this.vModel = new VerbModel(this.POSTags,this.parse,this.englishText);
    }

    public Clause createClause(ArrayList<String> words, Integer clauseIndex) {
        this.clauseWords = words;
        ArrayList<NounPhrase> NPs = new ArrayList<>();
        ArrayList<VerbPhrase> VPs = new ArrayList<>();
        String prep = "";
        Clause clause = new Clause(clauseIndex);
        for(int i=0; i < this.clauseWords.size(); i++) {
            String word = this.clauseWords.get(i);
            POS tag = this.tagger.getGeneralTag(word,true);
            switch (tag) {
                case NOUN:
                    System.out.println("Noun:" + word);
                    NounPhrase newNP = this.nModel.createNP(this.clauseWords, word);
                    NPs.add(newNP);
                    break;
                case VRB:
                    if(!this.excludedVerbs.contains(word.toUpperCase())) {
                        VerbPhrase newVP = this.vModel.createVP(this.clauseWords, word,false, false);
                        VPs.add(newVP);
                    }
                    break;
                case MDL:
                    VerbPhrase newVP = this.vModel.createVP(this.clauseWords, word, true, false);
                    VPs.add(newVP);
                    break;
                case PREP:
                    prep = word;
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
        clause = this.addToClause(clause, NPs, VPs, prep);
        return clause;
    }

    private Clause addToClause(Clause clause, ArrayList<NounPhrase> NPs, ArrayList<VerbPhrase> VPs, String prep) {
        if(VPs.size() > 0) {
            clause.setVPs(VPs);
            clause.setNPs(NPs);
        } else {
            if(!prep.equals("")) {
                clause.setVPs(this.prepForVP(prep));
                clause.setNPs(this.nModel.removePreps(NPs, prep));
            } else {
                clause.setNPs(NPs);
            }
        }
        clause = this.checkForTense(clause);
        clause.setVPs(vModel.checkVerbs(clause.getVPs()));
        return clause;
    }

    private Clause checkForTense(Clause clause) {
        if(clause.getTimeFrame().equals("")) {
            Integer tense = 0;
            Boolean isModal = false;
            try {
                for(VerbPhrase VP: clause.getVPs()) {
                    JSONObject currentTag = this.tagger.getExactTag(VP.getVerb(), true);
                    String pos = currentTag.getString("pos");
                    if(pos.equals("VBD") | pos.equals("VBN")) {
                        tense = 1;
                    }
                    if(VP.getIsModal()) {
                        isModal = true;
                    }
                }
                if(tense.equals(0) && isModal) {
                    tense = 2;
                }
            } catch (JSONException e) {
                System.out.println("Failed to check tense of verbs: " + e.getMessage());
            }
            if(tense.equals(1)) {
                clause.setTimeFrame("BEFORE");
            }
            if(tense.equals(2)) {
                clause.setTimeFrame("FUTURE");
            }
        }
        return clause;
    }

    private ArrayList<VerbPhrase> prepForVP(String possible) {
        ArrayList<VerbPhrase> VPs = new ArrayList<>();
        VerbPhrase newVP = this.vModel.createVP(this.clauseWords, possible,false,true);
        VPs.add(newVP);
        return VPs;
    }
}
