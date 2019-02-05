package c.example.speechtobsl.services;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class SynonymsClient {

    private ArrayList<String> syns;

    public SynonymsClient() {
        this.syns = new ArrayList<>();
    }

    public ArrayList<String> getSynonyms(String word) {
        try {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    runOxfordRequest(new String[]{word});
                }
            });
            thread.start();
            thread.join();
            System.out.println(this.syns);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        return syns;
    }

    /**
     * Request is sent to the oxford dictionary API to get the thesaurus entry for the specified word
     * Synonyms are then extracted from the JSON response
     * @param params contains the word that requires synonyms
     */
    private void runOxfordRequest(String[] params) {
        StringBuilder sb = new StringBuilder();
        try {
            String wordId = URLEncoder.encode(params[0].toLowerCase(), "UTF-8");
            String language = "en";
            URL url = new URL("https://od-api.oxforddictionaries.com:443/api/v1/entries/" + language + "/" + wordId + "/synonyms");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Accept","application/json");
            connection.setRequestProperty("app_id","a7e2cc18");
            connection.setRequestProperty("app_key", "5836a8400e0fcbcc9922288a0479ca6d");

            BufferedReader bf = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String in;
            while ((in = bf.readLine()) != null) {
                sb.append(in);
            }
            bf.close();
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println(e.getLocalizedMessage());
        }


        try {
            JSONObject result = new JSONObject(sb.toString()).getJSONArray("results").getJSONObject(0);
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