package c.example.speechtobsl.structure_converter.entities;

import java.util.ArrayList;

public class VerbPhrase {

    private String verb;
    private String lemmaVerb;
    private Boolean isModal;
    private Boolean prepVerb;
    private Boolean negated;
    private ArrayList<String> adverbs;

    public VerbPhrase(String nVerb) {
        this.verb = nVerb;
        this.lemmaVerb = "";
        this.isModal = false;
        this.prepVerb = false;
        this.negated = false;
        this.adverbs = new ArrayList<>();
    }

    public VerbPhrase(String nVerb, String lemmaVerb, Boolean nIsModal, Boolean nPrepVerb, Boolean negated, ArrayList<String> nAdverbs) {
        this.verb = nVerb;
        this.lemmaVerb = lemmaVerb;
        this.isModal = nIsModal;
        this.prepVerb = nPrepVerb;
        this.negated = negated;
        this.adverbs = nAdverbs;
    }

    public String getVerb() {
        return this.verb;
    }

    public void setVerb(String verb) {
        this.verb = verb;
    }

    public String getLemmaVerb() {
        return this.lemmaVerb;
    }

    public void setLemmaVerb(String lemmaVerb) {
        this.lemmaVerb = lemmaVerb;
    }

    public Boolean getIsModal() {
        return this.isModal;
    }

    public void setIsModal(Boolean isModal) {
        this.isModal = isModal;
    }

    public Boolean getPrepVerb() {
        return this.prepVerb;
    }

    public void setPrepVerb(Boolean prepVerb) {
        this.prepVerb = prepVerb;
    }

    public Boolean getNegated() {
        return this.negated;
    }

    public void setNegated(Boolean negated) {
        this.negated = negated;
    }

    public ArrayList<String> getAdverbs() {
        return this.adverbs;
    }

    public void setAdverbs(ArrayList<String> adverbs) {
        this.adverbs = adverbs;
    }

    public ArrayList<String> toArrayString() {
        ArrayList<String> VP = new ArrayList<>();
        //negation
        if(this.negated) {
            VP.add("NO");
        }
        //adverbs
        if(this.adverbs.size() > 0) {
            VP.addAll(this.adverbs);
        }
        System.out.println("Lemma: " + lemmaVerb);
        System.out.println("Verb: " + verb);
        if(!this.lemmaVerb.equals(""))
        {
            VP.add(this.lemmaVerb);
        } else {
            VP.add(this.verb);
        }
        return VP;
    }


}
