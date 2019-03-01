package c.example.speechtobsl.structure_converter.entities;

import java.util.ArrayList;

/**
 * The type Clause.
 */
public class Clause {

    private Integer index;
    private ArrayList<NounPhrase> NPs;
    private ArrayList<VerbPhrase> VPs;
    private String timeFrame;
    private String question;
    private String connector;
    private Boolean beforeConnector;

    /**
     * Instantiates a new Clause.
     *
     * @param nIndex the index of the clause
     */
    public Clause(Integer nIndex) {
        this.index = nIndex;
        this.NPs = new ArrayList<>();
        this.VPs = new ArrayList<>();
        this.timeFrame = "";
        this.question = "";
        this.connector = "";
        this.beforeConnector = false;
    }

    /**
     * Instantiates a new Clause.
     *
     * @param nIndex    the index
     * @param NPs       the noun phrases
     * @param VPs       the verb phrases
     * @param time      the time word
     * @param question  the question word
     * @param connector the connector/connective
     * @param before    indicates if the clause should come before or after the connector
     */
    public Clause(Integer nIndex, ArrayList<NounPhrase> NPs, ArrayList<VerbPhrase> VPs, String time, String question, String connector, Boolean before) {
        this.index = nIndex;
        this.NPs = NPs;
        this.VPs = VPs;
        this.timeFrame = time;
        this.question = question;
        this.connector = connector;
        this.beforeConnector = before;
    }

    /**
     * Sets the noun phrases
     *
     * @param NPs the noun phrases
     */
    public void setNPs(ArrayList<NounPhrase> NPs) {
        this.NPs = NPs;
    }

    /**
     * Gets the verb phrases
     *
     * @return the verb phrases
     */
    public ArrayList<VerbPhrase> getVPs() {
        return VPs;
    }

    /**
     * Sets the verb phrases
     *
     * @param VPs the verb phrases
     */
    public void setVPs(ArrayList<VerbPhrase> VPs) {
        this.VPs = VPs;
    }

    /**
     * Sets the connector.
     *
     * @param connector the connector
     */
    public void setConnector(String connector) {
        this.connector = connector;
    }

    /**
     * Sets indicator for whether the clause comes before or after the connector
     *
     * @param beforeConnector the before connector
     */
    public void setBeforeConnector(Boolean beforeConnector) {
        this.beforeConnector = beforeConnector;
    }

    /**
     * Gets time frame.
     *
     * @return the time frame
     */
    public String getTimeFrame() {
        return timeFrame;
    }

    /**
     * Sets time frame.
     *
     * @param timeFrame the time frame
     */
    public void setTimeFrame(String timeFrame) {
        this.timeFrame = timeFrame;
    }

    /**
     * Sets question word.
     *
     * @param question the question
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * Converts the clause into a list in the correct BSL order
     *
     * @return the array list
     */
    public ArrayList<String> toArrayString() {
        ArrayList<String> clause = new ArrayList<>();
        //timeframe
        if(!this.timeFrame.equals("")) {
            clause.add(this.timeFrame);
        }
        //Objects & Subjects
        ArrayList<String> subjs = new ArrayList<>();
        ArrayList<String> objs = new ArrayList<>();
        this.NPs.forEach((NP) -> {
            if(NP.isSubject()) {
                subjs.addAll(NP.toArrayString());
            } else {
                objs.addAll(NP.toArrayString());
            }
        });
        clause.addAll(objs);
        clause.addAll(subjs);
        //Verbs
        this.VPs.forEach((VP) -> clause.addAll(VP.toArrayString()));
        //Question
        if(!this.question.equals("")) {
            clause.add(this.question);
        }
        //Connector
        if(!this.connector.equals("")) {
            if(this.beforeConnector) {
                clause.add(connector);
            } else {
                clause.add(0,this.connector);
            }
        }
        return clause;
    }
}
