package c.example.speechtobsl.structure_converter.entities;

import java.util.ArrayList;

public class NounPhrase {

    private String noun;
    private Boolean plural;
    private String determiner;
    private ArrayList<Adjective> adjectives;
    private String preposition;
    private Boolean isSubject;

    public NounPhrase(String nNoun) {
        this.noun = nNoun;
        this.plural = false;
        this.determiner = "";
        this.adjectives = new ArrayList<>();
        this.preposition = "";
        this.isSubject = false;
    }

    public String getNoun() {
        return this.noun;
    }

    public void setNoun(String noun) {
        this.noun = noun;
    }

    public Boolean getPlural() {
        return plural;
    }

    public void setPlural(Boolean plural) {
        this.plural = plural;
    }

    public String getDeterminer() {
        return determiner;
    }

    public void setDeterminer(String determiner) {
        this.determiner = determiner;
    }

    public ArrayList<Adjective> getAdjectives() {
        return adjectives;
    }

    public void setAdjectives(ArrayList<Adjective> adjectives) {
        this.adjectives = adjectives;
    }

    public String getPreposition() {
        return preposition;
    }

    public void setPreposition(String preposition) {
        this.preposition = preposition;
    }

    public Boolean isSubject() {
        return isSubject;
    }

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
