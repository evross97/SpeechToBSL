package c.example.speechtobsl.structure_converter.entities;

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

    public Clause(Integer nIndex, ArrayList<NounPhrase> NPs, ArrayList<VerbPhrase> VPs, String time, String question, String connector, Boolean before) {
        this.index = nIndex;
        this.NPs = NPs;
        this.VPs = VPs;
        this.timeFrame = time;
        this.question = question;
        this.connector = connector;
        this.beforeConnector = before;
    }

    public void setNPs(ArrayList<NounPhrase> NPs) {
        this.NPs = NPs;
    }

    public ArrayList<VerbPhrase> getVPs() {
        return VPs;
    }

    public void setVPs(ArrayList<VerbPhrase> VPs) {
        this.VPs = VPs;
    }

    public void setConnector(String connector) {
        this.connector = connector;
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
