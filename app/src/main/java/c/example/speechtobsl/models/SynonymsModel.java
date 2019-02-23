package c.example.speechtobsl.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;

import c.example.speechtobsl.outer_framework.Client;
import c.example.speechtobsl.structure_converter.models.TagModel;
import c.example.speechtobsl.structure_converter.utils.POS;

public class SynonymsModel {

    private ArrayList<String> syns;
    private Client thesaurus;
    private TagModel tagger;

    public SynonymsModel(TagModel tagger) {
        this.syns = new ArrayList<>();
        this.thesaurus = new Client();
        this.tagger = tagger;
    }

    public ArrayList<String> getSynonyms(String word) {
        try {
            String wordId = URLEncoder.encode(word.toLowerCase(), "UTF-8");
            String language = "en";
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    String response = thesaurus.sendRequest(new String[]{
                            "GET",
                            "https://od-api.oxforddictionaries.com",
                            "443",
                            "/api/v1/entries/" + language + "/" + wordId + "/synonyms"}
                    );
                    extractSyns(word, response);
                }
            });
            thread.start();
            thread.join();

            System.out.println(this.syns);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return syns;
    }

    /**
     * Request is sent to the oxford dictionary API to get the thesaurus entry for the specified word
     * Synonyms are then extracted from the JSON response
     * @param word the original word
     * @param response all the synonyms
     */
    private void extractSyns(String word, String response) {
        POS tag = tagger.getGeneralTag(word.toLowerCase(), false);
        String synType = "";
        switch (tag) {
            case NOUN:
                synType = "Noun";
                break;
            case VRB:
            case MDL:
                synType = "Verb";
                break;
            case ADJ:
                synType = "Adjective";
                break;
            case ADV:
            case CONN:
                synType = "Adverb";
                break;
            case PREP:
                synType = "Preposition";
                break;
        }
        if(!synType.equals("")) {
            try {
                JSONObject result = new JSONObject(response).getJSONArray("results").getJSONObject(0);
                JSONArray allEntries = result.getJSONArray("lexicalEntries");
                for(int i = 0; i < allEntries.length(); i++) {
                    JSONObject entry = allEntries.getJSONObject(i);
                    String type = entry.getString("lexicalCategory");
                    if(type.equals(synType)) {
                        JSONObject correctEntry = entry.getJSONArray("entries").getJSONObject(0);
                        JSONArray senses = correctEntry.getJSONArray("senses");
                        for(int j = 0; j < senses.length(); j++) {
                            JSONArray synonyms = senses.getJSONObject(j).getJSONArray("synonyms");
                            for(int k = 0; k < synonyms.length(); k++) {
                                JSONObject singleSyn = synonyms.getJSONObject(k);
                                syns.add(singleSyn.get("text").toString());
                            }
                        }
                        break;
                    }
                }
            } catch(JSONException e) {
                System.out.println("Couldn't extract synonyms from response: " + e.getLocalizedMessage());
            }
        }
    }
}