package c.example.speechtobsl.structure_converter.entities;

import java.util.ArrayList;

/**
 * The type verb phrase.
 */
public class VerbPhrase {

    private String verb;
    private String lemmaVerb;
    private Boolean isModal;
    private Boolean prepVerb;
    private Boolean negated;
    private ArrayList<String> adverbs;

    /**
     * Instantiates a new verb phrase.
     *
     * @param nVerb the verb
     */
    public VerbPhrase(String nVerb) {
        this.verb = nVerb;
        this.lemmaVerb = "";
        this.isModal = false;
        this.prepVerb = false;
        this.negated = false;
        this.adverbs = new ArrayList<>();
    }

    /**
     * Instantiates a new verb phrase.
     *
     * @param nVerb     the verb
     * @param lemmaVerb the lemma form of the verb
     * @param nIsModal  is the verb a modal
     * @param nPrepVerb is the verb a preposition
     * @param negated   is the verb phrase negative
     * @param nAdverbs  the adverbs
     */
    public VerbPhrase(String nVerb, String lemmaVerb, Boolean nIsModal, Boolean nPrepVerb, Boolean negated, ArrayList<String> nAdverbs) {
        this.verb = nVerb;
        this.lemmaVerb = lemmaVerb;
        this.isModal = nIsModal;
        this.prepVerb = nPrepVerb;
        this.negated = negated;
        this.adverbs = nAdverbs;
    }

    /**
     * Gets the verb.
     *
     * @return the verb
     */
    public String getVerb() {
        return this.verb;
    }

    /**
     * Sets the lemma form of the verb.
     *
     * @param lemmaVerb the lemma
     */
    public void setLemmaVerb(String lemmaVerb) {
        this.lemmaVerb = lemmaVerb;
    }

    /**
     * Gets whether the verb is a modal verb
     *
     * @return whether the verb is modal
     */
    public Boolean getIsModal() {
        return this.isModal;
    }

    /**
     * Sets whether the verb is a modal verb
     *
     * @param isModal whether the verb is modal
     */
    public void setIsModal(Boolean isModal) {
        this.isModal = isModal;
    }

    /**
     * Sets whether the verb is a preposition
     *
     * @param prepVerb whether the verb is a preposition
     */
    public void setPrepVerb(Boolean prepVerb) {
        this.prepVerb = prepVerb;
    }

    /**
     * Sets whether the verb phrase is negative
     *
     * @param negated whether the verb phrase is negative
     */
    public void setNegated(Boolean negated) {
        this.negated = negated;
    }

    /**
     * Sets the adverbs.
     *
     * @param adverbs the adverbs
     */
    public void setAdverbs(ArrayList<String> adverbs) {
        this.adverbs = adverbs;
    }

    /**
     * Converts the verb phrase into a list in the correct BSL order
     *
     * @return the array list
     */
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
