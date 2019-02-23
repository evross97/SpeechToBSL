package c.example.speechtobsl.models;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

import c.example.speechtobsl.entities.Image;

public class DatabaseModel extends SQLiteAssetHelper {

    private static final String dbName = "signDB";
    private static final Integer dbVersion = 1;
    private SQLiteDatabase db;
    private Cursor cursor;


    public DatabaseModel(Context ctx) {
        super(ctx,dbName,null,dbVersion);
        db = this.getReadableDatabase();
    }

    public ArrayList<Image> getAllImages(ArrayList<String> sentence) {
        ArrayList<Image> allSigns = new ArrayList<>();
        ArrayList<String> questionMarks = new ArrayList<>();
        for(int i = 0; i < sentence.size(); i++) {
            sentence.set(i,sentence.get(i).toUpperCase());
            questionMarks.add("?");
        }
        cursor = db.rawQuery("SELECT Image, Description FROM images WHERE images.Description IN (" + TextUtils.join(",", questionMarks) + ")",
                sentence.toArray(new String[sentence.size()]));

        while(cursor.moveToNext()) {
            Integer dIndex = cursor.getColumnIndex("Description");
            String desc = cursor.getString(dIndex);
            Integer iIndex = cursor.getColumnIndexOrThrow("Image");
            byte[] image = cursor.getBlob(iIndex);
            Image sign = new Image(image, desc);
            allSigns.add(sign);
        }
        return allSigns;
    }

    public Image getDBSignForWord(ArrayList<Image> images, String word) {
        String upperWord = word.toUpperCase();
        Image sign = new Image(null, upperWord);
        for(Image possible : images) {
            if(possible.getDesc().equals(upperWord)) {
                sign.setImage(possible.getImage());
                break;
            }
        }
        return sign;
    }

}
