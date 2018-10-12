package com.example.riza.moviecatalogueuiux;

import android.app.Application;

import com.example.riza.moviecatalogueuiux.data.network.RepositorySync;

public class App extends Application {

    RepositorySync repo;

    @Override
    public void onCreate() {
        super.onCreate();
        repo = new RepositorySync();
    }

    public RepositorySync getRepo() {
        return repo;
    }


}
