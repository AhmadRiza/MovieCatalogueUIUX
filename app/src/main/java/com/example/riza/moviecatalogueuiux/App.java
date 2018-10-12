package com.example.riza.moviecatalogueuiux;

import android.app.Application;
import android.os.AsyncTask;

import com.example.riza.moviecatalogueuiux.data.network.RepositorySync;

public class App extends Application {

    RepositorySync repo;

    @Override
    public void onCreate() {
        super.onCreate();
        repo = new RepositorySync();
        new LoadCategory().execute();
    }

    public RepositorySync getRepo() {
        return repo;
    }

    private class LoadCategory extends AsyncTask<Void,Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            getRepo().generateGenres();
            return null;
        }
    }


}
