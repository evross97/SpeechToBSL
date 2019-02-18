package c.example.speechtobsl.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;

import c.example.speechtobsl.outer_framework.Client;

public class SynonymsModel {

    private ArrayList<String> syns;
    private Client thesaurus;

    public SynonymsModel() {
        this.syns = new ArrayList<>();
        this.thesaurus = new Client();
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
                    extractSyns(response);
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
     * @param response all the synonyms
     */
    private void extractSyns(String response) {
        try {
            JSONObject result = new JSONObject(response).getJSONArray("results").getJSONObject(0);
            JSONObject entries = result.getJSONArray("lexicalEntries").getJSONObject(0).getJSONArray("entries").getJSONObject(0);
            JSONArray synonyms = entries.getJSONArray("senses").getJSONObject(0).getJSONArray("synonyms");
            for(int i = 0; i < synonyms.length(); i++) {
                JSONObject singleSyn = synonyms.getJSONObject(i);
                syns.add(singleSyn.get("text").toString());
            }

        } catch(JSONException e) {
            System.out.println("Couldn't extract synonyms from response: " + e.getLocalizedMessage());
        }

    }
}