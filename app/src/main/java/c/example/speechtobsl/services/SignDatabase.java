package c.example.speechtobsl.services;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.io.InputStream;
import java.sql.Blob;

import c.example.speechtobsl.utils.Image;

public class SignDatabase extends SQLiteAssetHelper {

    private static final String dbName = "signs_db";
    private static final Integer dbVersion = 1;
    private SQLiteDatabase db;
    private Cursor cursor;


    public SignDatabase(Context ctx) {
        super(ctx,dbName,null,dbVersion);
        db = this.getReadableDatabase();
    }

    public Image getSign(String word) {
        Image sign = new Image(null, word);
        cursor = db.rawQuery("SELECT Image FROM images " +
                "INNER JOIN synonyms ON images.ImageId = synonyms.ImageId " +
                "WHERE synonyms.Synonym = ?",
                new String[]{word});
        if(cursor.moveToFirst()) {
            sign.setImage(cursor.getBlob(0));
        }
        else {

        }
        return sign;
    }
}
