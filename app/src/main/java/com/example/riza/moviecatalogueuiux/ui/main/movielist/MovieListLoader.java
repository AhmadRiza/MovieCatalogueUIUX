package com.example.riza.moviecatalogueuiux.ui.main.movielist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.example.riza.moviecatalogueuiux.App;
import com.example.riza.moviecatalogueuiux.data.model.Movie;
import com.example.riza.moviecatalogueuiux.data.network.Repository;
import com.example.riza.moviecatalogueuiux.data.network.RequestCallback;

import java.util.ArrayList;
import java.util.List;

public class MovieListLoader extends AsyncTaskLoader<List<Movie>> {

    private int requestCode;
    private ArrayList<Movie> mData;

    public MovieListLoader(@NonNull Context context, int requestCode) {
        super(context);
        mData= new ArrayList<>();
        this.requestCode = requestCode;
    }

    @Nullable
    @Override
    public List<Movie> loadInBackground() {

        ((App) getContext()).getRepo().getMovie(requestCode, null, new RequestCallback() {
            @Override
            public void onSucess(ArrayList<Movie> movies) {
                mData.clear();
                mData.addAll(movies);
            }

            @Override
            public void onError(String message) {

            }
        });
        return mData;
    }




}
