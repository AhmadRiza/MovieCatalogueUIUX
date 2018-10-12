package com.example.riza.moviecatalogueuiux.ui.main.movielist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.example.riza.moviecatalogueuiux.data.model.Movie;

import java.util.List;

public class MovieListLoader extends AsyncTaskLoader<List<Movie>> {

    public MovieListLoader(@NonNull Context context) {
        super(context);
    }

    @Nullable
    @Override
    public List<Movie> loadInBackground() {
        return null;
    }




}
