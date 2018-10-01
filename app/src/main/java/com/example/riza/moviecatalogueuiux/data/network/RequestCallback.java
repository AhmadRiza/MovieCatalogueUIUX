package com.example.riza.moviecatalogueuiux.data.network;

import com.example.riza.moviecatalogueuiux.data.model.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by riza on 09/08/18.
 */

public interface RequestCallback {

    void onSucess(ArrayList<Movie> movies);

    void onError(String message);

}
