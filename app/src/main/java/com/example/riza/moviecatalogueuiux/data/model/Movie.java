package com.example.riza.moviecatalogueuiux.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by riza on 09/08/18.
 */

public class Movie implements Parcelable {

    private String title;
    private String date;
    private String rating;
    private String describtion;
    private String imgSource;
    private String  genres;

    public Movie(String title, String date, String rating, String descriprion, String imgSource, String genres) {
        this.title = title;
        this.date = date;
        this.rating = rating;
        this.describtion = descriprion;
        this.imgSource = imgSource;
        this.genres = genres;
    }

    public Movie() {
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

    public String getDescribtion() {
        return describtion;
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

    public void setDescribtion(String describtion) {
        this.describtion = describtion;
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
        dest.writeString(this.title);
        dest.writeString(this.date);
        dest.writeString(this.rating);
        dest.writeString(this.describtion);
        dest.writeString(this.imgSource);
        dest.writeString(this.genres);
    }

    protected Movie(Parcel in) {
        this.title = in.readString();
        this.date = in.readString();
        this.rating = in.readString();
        this.describtion = in.readString();
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
