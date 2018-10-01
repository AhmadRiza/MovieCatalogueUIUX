package com.example.myfavoritemovie.data.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.myfavoritemovie.data.db.DBContract;


/**
 * Created by riza on 09/08/18.
 */

public class Movie implements Parcelable {

    private int id;
    private String title;
    private String date;
    private String rating;
    private String desc;
    private String imgSource;
    private String  genres;

    public Movie(int id,String title, String date, String rating, String desc, String imgSource, String genres) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.rating = rating;
        this.desc = desc;
        this.imgSource = imgSource;
        this.genres = genres;
    }

    public Movie() {
    }

    public Movie(Cursor cursor){
        this.id = DBContract.getColumnInt(cursor, DBContract.MovieTable._ID);
        this.title = DBContract.getColumnString(cursor, DBContract.MovieTable.TITLE);
        this.date = DBContract.getColumnString(cursor, DBContract.MovieTable.DATE);
        this.desc = DBContract.getColumnString(cursor, DBContract.MovieTable.DESC);
        this.rating = DBContract.getColumnString(cursor, DBContract.MovieTable.RATING);
        this.imgSource = DBContract.getColumnString(cursor, DBContract.MovieTable.IMG);
        this.genres = DBContract.getColumnString(cursor, DBContract.MovieTable.GENRES);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getRating() {
        return rating;
    }

    public String getDesc() {
        return desc;
    }

    public String getImgSource() {
        return imgSource;
    }

    public String  getGenres() {
        return genres;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setImgSource(String imgSource) {
        this.imgSource = imgSource;
    }

    public void setGenres(String  genres) {
        this.genres = genres;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.date);
        dest.writeString(this.rating);
        dest.writeString(this.desc);
        dest.writeString(this.imgSource);
        dest.writeString(this.genres);
    }

    protected Movie(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.date = in.readString();
        this.rating = in.readString();
        this.desc = in.readString();
        this.imgSource = in.readString();
        this.genres = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
