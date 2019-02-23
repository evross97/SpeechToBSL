package c.example.speechtobsl.structure_converter.entities;

import java.util.ArrayList;

public class VerbPhrase {

    private String verb;
    private String lemmaVerb;
    private String modal;
    private Boolean prepVerb;
    private ArrayList<String> adverbs;

    public VerbPhrase(String nVerb) {
        this.verb = nVerb;
        this.modal = "";
        this.prepVerb = false;
        this.adverbs = new ArrayList<>();
    }

    public VerbPhrase(String nVerb, String nModal, Boolean nPrepVerb, ArrayList<String> nAdverbs) {
        this.verb = nVerb;
        this.modal = nModal;
        this.prepVerb = nPrepVerb;
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

    public String getModal() {
        return this.modal;
    }

    public void setModal(String modal) {
        this.modal = modal;
    }

    public Boolean getPrepVerb() {
        return this.prepVerb;
    }

    public void setPrepVerb(Boolean prepVerb) {
        this.prepVerb = prepVerb;
    }

    public ArrayList<String> getAdverbs() {
        return this.adverbs;
    }

    public void setAdverbs(ArrayList<String> adverbs) {
        this.adverbs = adverbs;
    }

    public ArrayList<String> toArrayString() {
        ArrayList<String> VP = new ArrayList<>();
        //modal
        if(!this.modal.equals("")) {
            VP.add(this.modal);
        }
        //adverbs
        if(this.adverbs.size() > 0) {
            VP.addAll(this.adverbs);
        }
        VP.add(this.lemmaVerb);
        return VP;
    }


}
