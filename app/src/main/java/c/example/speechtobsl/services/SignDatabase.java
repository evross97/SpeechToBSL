package c.example.speechtobsl.services;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class SignDatabase extends SQLiteAssetHelper {

    public static final String dbName = "signs.db";
    public static final Integer dbVersion = 1;

    public SignDatabase(Context ctx) {
        super(ctx,dbName,null,dbVersion);
        SQLiteDatabase db = this.getReadableDatabase();
        System.out.println("connected to db");
    }
}
