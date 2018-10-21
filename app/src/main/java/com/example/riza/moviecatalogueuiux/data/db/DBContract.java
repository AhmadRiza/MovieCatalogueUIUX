package com.example.riza.moviecatalogueuiux.data.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

import com.example.riza.moviecatalogueuiux.data.model.Movie;

/**
 * Created by riza on 23/08/18.
 */

public class DBContract {

    public static final String TABLE_MOVIE = "movie";

    public static final class MovieTable implements BaseColumns {
        public static final String TITLE = "title";
        public static final String DATE ="date";
        public static final String RATING = "rating";
        public static final String DESC = "desc";
        public static final String IMG ="img";
        public static final String  GENRES = "genre";
        public static final String IMG_LANDSCAPE = "img_landscape";
    }

    public static final String AUTHORITY = "com.example.riza.moviecatalogue";

    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_MOVIE)
            .build();

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString( cursor.getColumnIndex(columnName) );
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt( cursor.getColumnIndex(columnName) );
    }

    public static ContentValues getContentValues(Movie movie){
        ContentValues cv = new ContentValues();
        cv.put(MovieTable._ID,movie.getId());
        cv.put(MovieTable.TITLE,movie.getTitle());
        cv.put(MovieTable.DATE,movie.getDate());
        cv.put(MovieTable.RATING,movie.getRating());
        cv.put(MovieTable.DESC,movie.getDesc());
        cv.put(MovieTable.IMG,movie.getImgSource());
        cv.put(MovieTable.GENRES,movie.getGenres());
        cv.put(MovieTable.IMG_LANDSCAPE,movie.getImgLandscape());
        return cv;
    }

}
