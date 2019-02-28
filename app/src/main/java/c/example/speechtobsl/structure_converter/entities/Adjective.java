package c.example.speechtobsl.structure_converter.entities;

import java.util.ArrayList;

public class Adjective {

    private String adjective;
    private ArrayList<String> adverbs;

    public Adjective(String nAdjective, ArrayList<String> nAdverbs) {
        this.adjective = nAdjective;
        this.adverbs = nAdverbs;
    }

    public ArrayList<String> toArrayString() {
        ArrayList<String> adj = new ArrayList<>();
        if(this.adverbs.size() > 0) {
            adj.addAll(this.adverbs);
        }
        adj.add(adjective);
        return adj;
    }
}
