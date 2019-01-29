package c.example.speechtobsl.services;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.IIndexWord;

import c.example.speechtobsl.services.Converter.POS;
import edu.mit.jwi.item.IWordID;

public class WordNet {

    private IDictionary dict;
    private final String LOG_TAG = "WordNet";
    private Context signCtx;

    public WordNet(Context ctx) {
        this.signCtx = ctx;
        try {
            String fileDir = "assets.dict";
            URL url = new URL("file",null,fileDir);
            dict = new Dictionary(url);
        } catch(MalformedURLException e) {
            Log.i(LOG_TAG, "Unable to find dictionary files");
        }
    }

    public ArrayList<String> getSynonyms(String word) {
        ArrayList<String> syns = new ArrayList<>();
        //Converter converter = new Converter();
        try {
            dict.open();
            //POS myPOS =
            IIndexWord idxWords = dict.getIndexWord(word, edu.mit.jwi.item.POS.NOUN);
            for(IWordID wordID : idxWords.getWordIDs()) {
                System.out.println("Syns: " + dict.getWord(wordID).toString());
            }
        } catch(IOException e) {
            e.printStackTrace();
            Log.i(LOG_TAG, "Unable to open dictionary");
        }
        return syns;
    }
}
