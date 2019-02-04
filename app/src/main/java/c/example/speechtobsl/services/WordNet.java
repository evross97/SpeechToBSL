package c.example.speechtobsl.services;

import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;

import rita.RiString;
import rita.RiWordNet;


public class WordNet {

    private RiWordNet wordnet;
    private final String LOG_TAG = "WordNet";
    private Context appCtx;

    public WordNet(Context ctx) {
        this.appCtx = ctx;
        StringBuffer fpath = new StringBuffer(400);
        fpath.append(appCtx.getExternalFilesDir(null).toString()
                + "/WordNet-3.0");
        wordnet = new RiWordNet(fpath.toString());
    }

    public ArrayList<String> getSynonyms(String word) {
        ArrayList<String> syns = new ArrayList<>();
        //Converter converter = new Converter();
        syns.addAll(Arrays.asList(wordnet.getSynonyms(word, RiWordNet.NOUN, 10)));
        return syns;
    }
}