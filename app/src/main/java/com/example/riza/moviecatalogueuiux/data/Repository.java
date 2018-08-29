package com.example.riza.moviecatalogueuiux.data;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.riza.moviecatalogueuiux.BuildConfig;
import com.example.riza.moviecatalogueuiux.data.model.Genre;
import com.example.riza.moviecatalogueuiux.data.model.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by riza on 09/08/18.
 */

public class Repository {

    public static final String TAG = Repository.class.getSimpleName();

    public static final int SEARCH = 12;
    public static final int NOW_PLAYING = 23;
    public static final int UPCOMING = 45;

    private Context context;
    private AsyncHttpClient mClient;
    private List<Genre> genres = new ArrayList<>();

    public Repository(Context context) {
        this.context = context;
        mClient = new AsyncHttpClient();
        generateGenres();
    }


    public void getMovie(int type, String query, final RequestCallback callback){

        String url = "";
        switch (type){
            case SEARCH:
                url = "https://api.themoviedb.org/3/search/movie?api_key=" +
                        BuildConfig.API_KEY + "&language=en-US&query=" + query;
                break;
            case NOW_PLAYING:
                url = "https://api.themoviedb.org/3/movie/now_playing?api_key="+
                        BuildConfig.API_KEY+"&language=en-US";
                break;
            case UPCOMING:
                url = "https://api.themoviedb.org/3/movie/upcoming?api_key=" +
                        BuildConfig.API_KEY+"&language=en-US";
                break;
        }

        mClient.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                ArrayList<Movie> movies = new ArrayList<>();
                String result = new String (responseBody);
                try {
                    JSONArray movieJSONList = new JSONObject(result).getJSONArray("results");

                    for (int i=0;i<movieJSONList.length();i++){

                        JSONObject movieJson = movieJSONList.getJSONObject(i);

                        Movie movie = new Movie();
                        movie.setTitle(movieJson.getString("title"));
                        movie.setImgSource(movieJson.getString("poster_path"));
                        movie.setDate(movieJson.getString("release_date"));
                        movie.setRating(movieJson.getString("vote_average"));
                        movie.setDescribtion(movieJson.getString("overview"));

                        JSONArray genreJson = movieJson.getJSONArray("genre_ids");

//                        no memory leak
                        StringBuilder sb = new StringBuilder();

                        for(int j=0;j<genreJson.length();j++){
                            int id = genreJson.getInt(j);
                            sb.append(getGenres(id));
                        }
                        movie.setGenres(sb.toString());

                        movies.add(movie);

                    }

                    callback.onSucess(movies);

                } catch (JSONException e) {
                    callback.onError(e.getMessage()+TAG);
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                callback.onError(new String(responseBody));
            }
        });

    }


    private String getGenres(int id){
        for (Genre g:
             genres) {
            if(g.getId()==id) return "#"+g.getName()+" ";
        }
        return "";
    }


    private void generateGenres() {

        mClient.get("https://api.themoviedb.org/3/genre/movie/list?api_key="
                + BuildConfig.API_KEY + "&language=en-US", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                String result = new String(responseBody);

                try {

                    JSONArray genreJson = new JSONObject(result).getJSONArray("genres");

                    for (int i = 0; i < genreJson.length(); i++) {
                        int id = genreJson.getJSONObject(i).getInt("id");
                        String name = genreJson.getJSONObject(i).getString("name");

                        genres.add(new Genre(id, name));
                    }

                } catch (JSONException e) {
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e(TAG, new String(responseBody));
            }
        });

    }



    public void cancelAllRequest(){
        mClient.cancelAllRequests(true);
    }


}
