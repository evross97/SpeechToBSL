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

/**
 * Used to create and manipulate noun phrases.
 */
public class NounModel {

    private ArrayList<JSONObject> POSTags;
    private ArrayList<JSONObject> parse;
    private ArrayList<String> englishText;

    private TagModel tagger;
    private ParseModel parser;

    /**
     * Instantiates a new noun model.
     *
     * @param tags     the POS tags
     * @param parse    the dependency parse
     * @param sentence the English sentence
     */
    public NounModel(ArrayList<JSONObject> tags, ArrayList<JSONObject> parse, ArrayList<String> sentence){
        this.POSTags = tags;
        this.parse = parse;
        this.englishText = sentence;
        this.tagger = new TagModel(this.POSTags, this.englishText);
        this.parser = new ParseModel(this.parse, this.tagger);
    }

    /**
     * Create a new noun phrase using the noun given and other words that are linked to it.
     *
     * @param clauseWords all the words in the current clause
     * @param noun        the noun
     * @return the noun phrase
     */
    public NounPhrase createNP(ArrayList<String> clauseWords, String noun) {
        NounPhrase NP = new NounPhrase(noun);
        NP.setAdjectives(this.createAdjectives(clauseWords, noun));
        //Det
        ArrayList<String> dets = this.parser.findLinks(clauseWords, noun, DET);
        if(!dets.isEmpty()) {
            NP.setDeterminer(dets.get(0));
        }
        //Prep
        ArrayList<String> preps = this.parser.findLinks(clauseWords, noun, PREP);
        if(!preps.isEmpty()) {
            NP.setPreposition(preps.get(0));
        }
        //Plural
        NP.setPlural(this.isPlural(noun));
        if(NP.getPlural()) {
            NP.setNoun(this.getSingular(noun));
        }
        NP.setSubject(this.isSubject(noun));
        return NP;
    }

    /**
     * Creates the list of adjectives for the current noun
     * @param clauseWords used to check that the connected adjectives are in the current clause
     * @param noun
     * @return list of adjectives
     */
    private ArrayList<Adjective> createAdjectives(ArrayList<String> clauseWords, String noun) {
        ArrayList<String> stringAdjs = this.parser.findLinks(clauseWords, noun, ADJ);
        ArrayList<Adjective> adjs = new ArrayList<>();
        for(int i = 0; i < stringAdjs.size(); i++) {
            String currentAdj = stringAdjs.get(i);
            ArrayList<String> advs = this.parser.findLinks(clauseWords, currentAdj, ADV);
            Adjective adj = new Adjective(currentAdj, advs);
            adjs.add(adj);
        }
        return adjs;
    }

    /**
     * Determines if the noun is in plural form
     * @param noun
     * @return true if plural
     */
    private Boolean isPlural(String noun) {
        Boolean plural = false;
        try{
            JSONObject currentTag = this.tagger.getExactTag(noun, true);
            String pos = currentTag.getString("pos");
            if (pos.equals("NNS") || pos.equals("NNPS")) {
                plural = true;
            }
        } catch(JSONException e) {
            System.out.println("Unable to check if noun ("+ noun + ") is plural: " + e.getMessage());
        }
        return plural;
    }

    /**
     * Determines if the current noun is the subject of the current clause
     * @param noun
     * @return true if the noun is the subject
     */
    private Boolean isSubject(String noun) {
        Boolean nsubj = false;
        ArrayList<JSONObject> links = this.parser.getLinkedParses(noun);
        for(int j = 0; j < links.size(); j++) {
            JSONObject currentParse = links.get(j);
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
        return nsubj;
    }

    /**
     * Retrieves the singular form of the noun from the tagger
     *
     * @param plural the plural form of the noun
     * @return the singular form of the noun
     */
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

    /**
     * If a preposition is being used as a verb in the clause, then it shouldn't also be present in any related noun phrases
     * This method removes any occurrences of that preposition
     *
     * @param NPs  all noun phrases in the clause
     * @param prep the preposition
     * @return the NPs with the preposition removed
     */
    public ArrayList<NounPhrase> removePreps(ArrayList<NounPhrase> NPs, String prep) {
        for(NounPhrase NP : NPs) {
            if(NP.getPreposition().equals(prep)) {
                NP.setPreposition("");
            }
        }
        return NPs;
    }
}
