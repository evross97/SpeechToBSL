package c.example.speechtobsl.services;

import android.content.Context;
import android.content.res.AssetManager;
import android.net.Uri;

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

import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.IWordID;

public class WordNet {

    private IDictionary dict;
    private final String LOG_TAG = "WordNet";
    private Context appCtx;

    public WordNet(Context ctx) {
        this.appCtx = ctx;
        try {
            AssetManager assets = this.appCtx.getAssets();
            InputStream in = assets.open("dict");
            /**System.out.println("HEEELLLOOO" + assets.list("")[1]);
            Uri path = Uri.parse("file:///android_asset/dict");
            String fileDir = path.toString();
            URL url = new URL("file",null,fileDir);**/

            byte[] buffer = new byte[in.available()];
            in.read(buffer);

            File targetFile = new File("file:///android_asset/dict");
            OutputStream outStream = new FileOutputStream(targetFile);
            outStream.write(buffer);
            dict = new Dictionary(targetFile);
        } catch(IOException e) {
            System.out.println("Unable to find dictionary files");
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
            System.out.println("Unable to open dictionary");
        }
        return syns;
    }
}