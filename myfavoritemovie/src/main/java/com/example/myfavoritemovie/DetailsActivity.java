package com.example.myfavoritemovie;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myfavoritemovie.data.model.Movie;
import com.example.myfavoritemovie.utils.AppUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.example.myfavoritemovie.data.db.DBContract.CONTENT_URI;

public class DetailsActivity extends AppCompatActivity {

    private Unbinder unbinder;
    private Movie movie;

    @BindView(R.id.collapsing_toolbar) CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.tv_detail_desc) TextView tvDesc;
    @BindView(R.id.tv_detail_rating) TextView tvRate;
    @BindView(R.id.tv_detail_date) TextView tvDate;
    @BindView(R.id.tv_detail_genre) TextView tvGenre;
    @BindView(R.id.img_detail_poster) ImageView imgPoster;
    @BindView(R.id.fab) FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        unbinder =  ButterKnife.bind(this);

        setUp();

        movie = getIntent().getParcelableExtra(MainActivity.MOVIE_EXTRA);

        if(movie!=null){
            setView(movie);
        }
    }

    private void setView(Movie movie) {

        Glide.with(this)
                .load("http://image.tmdb.org/t/p/w342"+movie.getImgSource())
                .into(imgPoster);

        collapsingToolbarLayout.setTitle(movie.getTitle());

        tvDesc.setText(movie.getDesc());
        tvRate.setText(String.format("%s/10", movie.getRating()));
        tvDate.setText(AppUtils.formatDate(movie.getDate()));
        tvGenre.setText(movie.getGenres());
    }

    private void setUp() {

        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_24dp);

        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.TextAppearance_MyApp_Title_Expanded);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.TextAppearance_MyApp_Title_Collapsed);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareMovie();
            }
        });
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    private void shareMovie(){

        String textMovie = String.format("\"%s(%s)\"\nRate : %s/10\nSynopsis : %s",
                movie.getTitle(),AppUtils.formatYear(movie.getDate()),movie.getRating(),movie.getDesc()
                );

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, textMovie);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

}
