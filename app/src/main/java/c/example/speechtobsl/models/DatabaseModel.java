package c.example.speechtobsl.models;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

import c.example.speechtobsl.entities.Image;

/**
 * Handles all interaction with the sign database
 */
public class DatabaseModel extends SQLiteAssetHelper {

    private static final String dbName = "signDB";
    private static final Integer dbVersion = 1;
    private SQLiteDatabase db;

    /**
     * Creates a new database
     *
     * @param ctx the context
     */
    public DatabaseModel(Context ctx) {
        super(ctx,dbName,null,dbVersion);
        db = this.getReadableDatabase();
    }

    /**
     * Gets all the possible images for all the words in a list
     * Uses WHERE IN to decrease number of database queries needed
     *
     * @param sentence the words that need equivalent images
     * @return all possible images that could be useful
     */
    public ArrayList<Image> getAllImages(ArrayList<String> sentence) {
        ArrayList<Image> allSigns = new ArrayList<>();
        System.out.println("HI");
        ArrayList<String> questionMarks = new ArrayList<>();
        for(int i = 0; i < sentence.size(); i++) {
            sentence.set(i,sentence.get(i).toUpperCase());
            questionMarks.add("?");
        }
        Cursor cursor = db.rawQuery("SELECT Image, Description FROM images WHERE images.Description IN (" + TextUtils.join(",", questionMarks) + ")",
                sentence.toArray(new String[sentence.size()]));

        while(cursor.moveToNext()) {
            Integer dIndex = cursor.getColumnIndex("Description");
            String desc = cursor.getString(dIndex);
            Integer iIndex = cursor.getColumnIndexOrThrow("Image");
            byte[] image = cursor.getBlob(iIndex);
            Image sign = new Image(image, desc);
            allSigns.add(sign);
        }
        cursor.close();
        return allSigns;
    }

    /**
     * Gets sign for a particular word from all the possible images related to the overall sentence
     *
     * @param images all images retrieved from query
     * @param word   the particular word that needs a sign
     * @return the database image for that word
     */
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
