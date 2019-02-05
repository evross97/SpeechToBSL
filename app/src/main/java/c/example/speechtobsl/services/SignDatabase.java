package c.example.speechtobsl.services;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import c.example.speechtobsl.utils.Image;

public class SignDatabase extends SQLiteAssetHelper {

    private static final String dbName = "sign_db";
    private static final Integer dbVersion = 1;
    private SQLiteDatabase db;
    private Cursor cursor;


    public SignDatabase(Context ctx) {
        super(ctx,dbName,null,dbVersion);
        db = this.getReadableDatabase();
    }

    public Image queryDatabase(String word) {
        String upperWord = word.toUpperCase();
        Image sign = new Image(null, upperWord);
        cursor = db.rawQuery("SELECT Image FROM images WHERE images.Description = ?",
                new String[]{upperWord});
        if(cursor.moveToFirst()) {
            sign.setImage(cursor.getBlob(0));
        }
        return sign;
    }

}
