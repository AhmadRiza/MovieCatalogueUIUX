package com.example.riza.moviecatalogueuiux.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.example.riza.moviecatalogueuiux.data.model.Movie;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by riza on 23/08/18.
 */
public class MovieDBHelper {

    private Context context;
    private DBHelper dataBaseHelper;

    private SQLiteDatabase database;

    public MovieDBHelper(Context context) {
        this.context = context;
    }

    public MovieDBHelper open() throws SQLException {
        dataBaseHelper = new DBHelper(context);
        database = dataBaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dataBaseHelper.close();
    }

    public ArrayList<Movie> getAllData() {
        Cursor cursor = database.query(DBContract.TABLE_MOVIE, null, null, null, null, null, DBContract.MovieTable._ID + " ASC", null);
        cursor.moveToFirst();
        ArrayList<Movie> arrayList = new ArrayList<>();
        Movie movie;
        if (cursor.getCount() > 0) {
            do {
                movie = new Movie();
                movie.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DBContract.MovieTable._ID)));
                movie.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(DBContract.MovieTable.TITLE)));
                movie.setDate(cursor.getString(cursor.getColumnIndexOrThrow(DBContract.MovieTable.DATE)));
                movie.setDesc(cursor.getString(cursor.getColumnIndexOrThrow(DBContract.MovieTable.DESC)));
                movie.setGenres(cursor.getString(cursor.getColumnIndexOrThrow(DBContract.MovieTable.GENRES)));
                movie.setRating(cursor.getString(cursor.getColumnIndexOrThrow(DBContract.MovieTable.RATING)));
                movie.setImgSource(cursor.getString(cursor.getColumnIndexOrThrow(DBContract.MovieTable.IMG)));

                arrayList.add(movie);
                cursor.moveToNext();


            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public void beginTransaction() {
        database.beginTransaction();
    }

    public void setTransactionSuccess() {
        database.setTransactionSuccessful();
    }

    public void endTransaction() {
        database.endTransaction();
    }

    public void insertTransaction(Movie movie) {
        String sql = "INSERT INTO " + DBContract.TABLE_MOVIE + " ("
                + DBContract.MovieTable.TITLE + ", "
                + DBContract.MovieTable.DATE + ", "
                + DBContract.MovieTable.DESC + ", "
                + DBContract.MovieTable.RATING + ", "
                + DBContract.MovieTable.GENRES + ", "
                + DBContract.MovieTable.IMG
                + ") VALUES (?, ?, ?, ?, ?, ?)";
        SQLiteStatement stmt = database.compileStatement(sql);
        stmt.bindString(1, movie.getTitle());
        stmt.bindString(2, movie.getDate());
        stmt.bindString(3, movie.getDesc());
        stmt.bindString(4, movie.getRating());
        stmt.bindString(5, movie.getGenres());
        stmt.bindString(6, movie.getImgSource());
        stmt.execute();
        stmt.clearBindings();

    }

    public int update(Movie word) {
        ContentValues args = new ContentValues();
        args.put(DBContract.MovieTable.TITLE, word.getTitle());
        args.put(DBContract.MovieTable.DATE, word.getDate());
        args.put(DBContract.MovieTable.DESC, word.getDesc());
        args.put(DBContract.MovieTable.RATING, word.getRating());
        args.put(DBContract.MovieTable.GENRES, word.getGenres());
        args.put(DBContract.MovieTable.IMG, word.getImgSource());
        return database.update(DBContract.TABLE_MOVIE, args, DBContract.MovieTable._ID + "= '" + word.getId() + "'", null);
    }


    public int delete(int id) {
        return database.delete(DBContract.TABLE_MOVIE, DBContract.MovieTable._ID + " = '" + id + "'", null);
    }

    //    Provider
    public Cursor queryByIdProvider(String id) {
        return database.query(DBContract.TABLE_MOVIE, null
                , DBContract.MovieTable._ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public Cursor queryProvider() {
        return database.query(DBContract.TABLE_MOVIE
                , null
                , null
                , null
                , null
                , null
                , DBContract.MovieTable._ID + " DESC");
    }

    public long insertProvider(ContentValues values) {
        return database.insert(DBContract.TABLE_MOVIE, null, values);
    }

    public int updateProvider(String id, ContentValues values) {
        return database.update(DBContract.TABLE_MOVIE, values, DBContract.MovieTable._ID + " = ?", new String[]{id});
    }

    public int deleteProvider(String id) {
        return database.delete(DBContract.TABLE_MOVIE, DBContract.MovieTable._ID + " = ?", new String[]{id});
    }

}
