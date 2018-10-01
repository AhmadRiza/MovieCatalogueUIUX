package com.example.riza.moviecatalogueuiux.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by riza on 23/08/18.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "dbmovie";
    private static final int DATABASE_VERSION = 1;
    private static final String SQL_CREATE_TABLE_MOVIE = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            DBContract.TABLE_MOVIE,
            DBContract.MovieTable._ID,
            DBContract.MovieTable.TITLE,
            DBContract.MovieTable.DATE,
            DBContract.MovieTable.DESC,
            DBContract.MovieTable.GENRES,
            DBContract.MovieTable.RATING,
            DBContract.MovieTable.IMG

    );
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_MOVIE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DBContract.TABLE_MOVIE);
        onCreate(db);
    }
}
