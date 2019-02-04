package c.example.speechtobsl.services;

import android.content.Context;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.ISynset;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;

public class WordNet {

    private IDictionary dict;
    private final String LOG_TAG = "WordNet";
    private Context appCtx;

    public WordNet(Context ctx) {
        this.appCtx = ctx;
        StringBuffer fpath = new StringBuffer(400);
        fpath.append(appCtx.getExternalFilesDir(null).toString()
                + "/dict");
        File f = new File(fpath.toString());
        dict = new Dictionary(f);
        try {
            dict.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getSynonyms(String word) {
        ArrayList<String> syns = new ArrayList<>();
        //Converter converter = new Converter();
        try {
            IIndexWord idxWord = dict.getIndexWord(word.toLowerCase(), edu.mit.jwi.item.POS.NOUN);
            IWordID iWordID = idxWord.getWordIDs().get(0);
            IWord wordMeaning = dict.getWord(iWordID);
            ISynset synsWords = wordMeaning.getSynset();
            for(IWord synonym: synsWords.getWords()) {
                syns.add(synonym.getLemma());
            }
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Unable to open dictionary");
        }
        return syns;
    }
}