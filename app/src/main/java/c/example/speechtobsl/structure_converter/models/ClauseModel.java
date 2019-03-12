package c.example.speechtobsl.structure_converter.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import c.example.speechtobsl.structure_converter.entities.Clause;
import c.example.speechtobsl.structure_converter.entities.NounPhrase;
import c.example.speechtobsl.structure_converter.entities.VerbPhrase;
import c.example.speechtobsl.structure_converter.utils.POS;

/**
 * Used to create and manipulate clauses.
 */
public class ClauseModel {

    private ArrayList<JSONObject> POSTags;
    private ArrayList<JSONObject> parse;
    private ArrayList<String> englishText;
    private ArrayList<String> clauseWords;

    private TagModel tagger;
    private NounModel nModel;
    private VerbModel vModel;

    private final ArrayList<String> excludedVerbs = new ArrayList<>(Arrays.asList("IS", "ARE", "WERE", "WAS"));

    /**
     * Instantiates a new clause model.
     *
     * @param tags     the POS tags
     * @param parse    the dependency parse
     * @param sentence the English sentence
     */
    public ClauseModel(ArrayList<JSONObject> tags, ArrayList<JSONObject> parse, ArrayList<String> sentence) {
        this.POSTags = tags;
        this.parse = parse;
        this.englishText = sentence;
        this.tagger = new TagModel(this.POSTags,this.englishText);
        this.nModel = new NounModel(this.POSTags,this.parse,this.englishText);
        this.vModel = new VerbModel(this.POSTags,this.parse,this.englishText);
    }

    /**
     * Create a new clause.
     * Goes through each word, determines its type and assigns it to the correct property within the clause
     *
     * @param words       the words to be included in the clause
     * @param clauseIndex the clause index
     * @return the clause
     */
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

    /**
     * Adds all the relevant information to the clause
     * Once all items for the clause have been created, it's all added to the clause
     * Also checks for tense and creates a verb phrase out of a preposition if there are currently no verb phrases
     * @param clause the clause so far
     * @param NPs the noun phrases
     * @param VPs the verb phrases
     * @param prep the preposition
     * @return the fully formed clause
     */
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

    /**
     * This method will assign a tense to a clause, unless it's present tense or a time word already exists
     * If any of the verbs in the clause have a past tense POS then it's past tense
     * If there are any modal verbs, then the clause is future tense
     * @param clause
     * @return the same clause but possibly with a tense set
     */
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

    /**
     * If there are no verb phrases within a clause then, if a preposition exists, a verb phrase is made from the preposition
     *
     * @param possible the preposition
     * @return the new verb phrase made from the preposition
     */
    private ArrayList<VerbPhrase> prepForVP(String possible) {
        ArrayList<VerbPhrase> VPs = new ArrayList<>();
        VerbPhrase newVP = this.vModel.createVP(this.clauseWords, possible,false,true);
        VPs.add(newVP);
        return VPs;
    }
}
