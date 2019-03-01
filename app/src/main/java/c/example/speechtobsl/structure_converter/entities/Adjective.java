package c.example.speechtobsl.structure_converter.entities;

import java.util.ArrayList;

/**
 * The type adjective.
 */
public class Adjective {

    private String adjective;
    private ArrayList<String> adverbs;

    /**
     * Instantiates a new adjective.
     *
     * @param nAdjective the adjective
     * @param nAdverbs   the adverbs for the adjective
     */
    public Adjective(String nAdjective, ArrayList<String> nAdverbs) {
        this.adjective = nAdjective;
        this.adverbs = nAdverbs;
    }

    /**
     * Converts the adjective into a list in the correct BSL order
     *
     * @return the array list
     */
    public ArrayList<String> toArrayString() {
        ArrayList<String> adj = new ArrayList<>();
        if(this.adverbs.size() > 0) {
            adj.addAll(this.adverbs);
        }
        adj.add(adjective);
        return adj;
    }
}
