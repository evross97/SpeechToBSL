package c.example.speechtobsl.services;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
import java.util.ArrayList;

import c.example.speechtobsl.utils.Image;

public class SignDatabase extends SQLiteAssetHelper {

    private static final String dbName = "sign_db";
    private static final Integer dbVersion = 1;
    private SQLiteDatabase db;
    private Cursor cursor;
    private Context signCtx;


    public SignDatabase(Context ctx) {
        super(ctx,dbName,null,dbVersion);
        db = this.getReadableDatabase();
        signCtx = ctx;
    }

    public ArrayList<Image> getSigns(String word) {
        ArrayList<Image> finalSigns = new ArrayList<>();
        Image sign = this.queryDatabase(word);
        Boolean signFound = sign.getImage() != null;
        if(!signFound){
            System.out.println("Need wordnet");
            WordNet wordNet = new WordNet(signCtx);
            ArrayList<String> synonyms = wordNet.getSynonyms(word);
            for(String syn : synonyms) {
                sign = this.queryDatabase(syn);
                signFound = sign.getImage() != null;
                if(signFound) {
                    finalSigns.add(sign);
                    break;
                }
            }
            if(!signFound) {
                System.out.println("Need fingerspelling");
                finalSigns = this.fingerSpellWord(word);
            }
        } else {
            System.out.println("First time");
            finalSigns.add(sign);
        }
        return finalSigns;
    }

    private Image queryDatabase(String word) {
        String upperWord = word.toUpperCase();
        Image sign = new Image(null, upperWord);
        cursor = db.rawQuery("SELECT Image FROM images WHERE images.Description = ?",
                new String[]{upperWord});
        if(cursor.moveToFirst()) {
            sign.setImage(cursor.getBlob(0));
        }
        return sign;
    }

    private ArrayList<Image> fingerSpellWord(String word) {
        ArrayList<Image> letterSigns = new ArrayList<>();
        String[] letters = word.split("");
        for(String letter : letters) {
            if(!letter.equals("")) {
                Image sign = this.queryDatabase(letter);
                sign.setDesc(word.toUpperCase());
                letterSigns.add(sign);
                Image emptySign = new Image(null," ");
                letterSigns.add(emptySign);
            }
        }
        Image emptyEndSign = new Image(null,"endWord");
        letterSigns.add(emptyEndSign);
        return letterSigns;
    }
}
