package c.example.speechtobsl.structure_converter.entities;

import java.util.ArrayList;

/**
 * The type noun phrase.
 */
public class NounPhrase {

    private String noun;
    private Boolean plural;
    private String determiner;
    private ArrayList<Adjective> adjectives;
    private String preposition;
    private Boolean isSubject;

    /**
     * Instantiates a new noun phrase.
     *
     * @param nNoun the noun
     */
    public NounPhrase(String nNoun) {
        this.noun = nNoun;
        this.plural = false;
        this.determiner = "";
        this.adjectives = new ArrayList<>();
        this.preposition = "";
        this.isSubject = false;
    }

    /**
     * Instantiates a new noun phrase.
     *
     * @param nNoun  the noun
     * @param plural indicates whether the noun is a plural
     * @param det    the determiner
     * @param adjs   the adjectives
     * @param prep   the preposition
     * @param subj   indicates whether the noun is the subject of the clause
     */
    public NounPhrase(String nNoun, Boolean plural, String det, ArrayList<Adjective> adjs, String prep, Boolean subj) {
        this.noun = nNoun;
        this.plural = plural;
        this.determiner = det;
        this.adjectives = adjs;
        this.preposition = prep;
        this.isSubject = subj;
    }

    /**
     * Sets noun.
     *
     * @param noun the noun
     */
    public void setNoun(String noun) {
        this.noun = noun;
    }

    /**
     * Sets if the noun is a plural
     *
     * @param plural the plural
     */
    public void setPlural(Boolean plural) {
        this.plural = plural;
    }

    /**
     * Sets determiner of noun.
     *
     * @param determiner the determiner
     */
    public void setDeterminer(String determiner) {
        this.determiner = determiner;
    }

    /**
     * Sets adjectives.
     *
     * @param adjectives the adjectives
     */
    public void setAdjectives(ArrayList<Adjective> adjectives) {
        this.adjectives = adjectives;
    }

    /**
     * Gets preposition.
     *
     * @return the preposition
     */
    public String getPreposition() {
        return preposition;
    }

    /**
     * Sets preposition.
     *
     * @param preposition the preposition
     */
    public void setPreposition(String preposition) {
        this.preposition = preposition;
    }

    /**
     * Gets whether the noun is the subject of the clause
     *
     * @return is the noun the subject
     */
    public Boolean isSubject() {
        return isSubject;
    }

    /**
     * Sets whether the noun is the subject of the clause
     *
     * @param subject the subject
     */
    public void setSubject(Boolean subject) {
        isSubject = subject;
    }

    /**
     * Used to check for possession - only want to include the determiner in the NP if it shows possession
     * @return true if it should be included
     */
    private Boolean checkDet() {
        if(this.determiner.toUpperCase().equals("THE")) {
            return false;
        }
        if(this.determiner.toUpperCase().equals("A")) {
            return false;
        }
        return true;
    }

    /**
     * Converts the noun phrase into a list in the correct BSL order
     *
     * @return the array list
     */
    public ArrayList<String> toArrayString() {
        ArrayList<String> NP = new ArrayList<>();
        //preposition
        if(!this.preposition.equals("")) {
            if(!this.preposition.equals("for") && !this.preposition.equals("of")) {
                NP.add(this.preposition);
            }
        }
        //possession
        if(!this.determiner.equals("")) {
            if (this.checkDet()) {
                NP.add(this.determiner);
            }
        }
        //plurals
        if(this.plural) {
            NP.add("MANY");
        }
        //noun
        NP.add(this.noun);
        //adjectives
        for(int i = 0; i < this.adjectives.size(); i++) {
            Adjective adj = this.adjectives.get(i);
            NP.addAll(adj.toArrayString());
        }
        return NP;
    }
}
