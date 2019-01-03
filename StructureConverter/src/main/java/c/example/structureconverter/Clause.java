package c.example.structureconverter;

import java.util.ArrayList;

public class Clause {

    private Integer index;
    private ArrayList<NounPhrase> NPs;
    private ArrayList<VerbPhrase> VPs;
    private String timeFrame;
    private String question;
    private String connector;
    //Does this clause appear before the connector
    private Boolean beforeConnector;

    public Clause(Integer nIndex) {
        this.index = nIndex;
        this.NPs = new ArrayList<>();
        this.VPs = new ArrayList<>();
        this.timeFrame = "";
        this.question = "";
        this.connector = "";
        this.beforeConnector = false;
    }
    public Clause(Integer nIndex, ArrayList<NounPhrase> nNPs, ArrayList<VerbPhrase> nVPs,String nTimeFrame, String nQuestion, String nConnector, Boolean nBeforeConnector) {
        this.index = nIndex;
        this.NPs = nNPs;
        this.VPs = nVPs;
        this.timeFrame = nTimeFrame;
        this.question = nQuestion;
        this.connector = nConnector;
        this.beforeConnector = nBeforeConnector;
    }

    public ArrayList<NounPhrase> getNPs() {
        return NPs;
    }

    public void setNPs(ArrayList<NounPhrase> NPs) {
        this.NPs = NPs;
    }

    public void addNP(NounPhrase NP) {
        this.NPs.add(NP);
    }

    public ArrayList<VerbPhrase> getVPs() {
        return VPs;
    }

    public void setVPs(ArrayList<VerbPhrase> VPs) {
        this.VPs = VPs;
    }

    public void addVP(VerbPhrase VP) {
        this.VPs.add(VP);
    }

    public String getConnector() {
        return connector;
    }

    public void setConnector(String connector) {
        this.connector = connector;
    }

    public Boolean getBeforeConnector() {
        return beforeConnector;
    }

    public void setBeforeConnector(Boolean beforeConnector) {
        this.beforeConnector = beforeConnector;
    }

    public String getTimeFrame() {
        return timeFrame;
    }

    public void setTimeFrame(String timeFrame) {
        this.timeFrame = timeFrame;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

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
