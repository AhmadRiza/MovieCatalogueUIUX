package com.example.riza.moviecatalogueuiux.ui.details;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.riza.moviecatalogueuiux.R;
import com.example.riza.moviecatalogueuiux.data.model.Movie;
import com.example.riza.moviecatalogueuiux.ui.main.MainActivity;
import com.example.riza.moviecatalogueuiux.utils.AppUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DetailsActivity extends AppCompatActivity {

    private Unbinder unbinder;

    @BindView(R.id.collapsing_toolbar) CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.tv_detail_desc) TextView tvDesc;
    @BindView(R.id.tv_detail_rating) TextView tvRate;
    @BindView(R.id.tv_detail_date) TextView tvDate;
    @BindView(R.id.tv_detail_genre) TextView tvGenre;
    @BindView(R.id.img_detail_poster) ImageView imgPoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        unbinder =  ButterKnife.bind(this);

        setUp();

        Movie movie = getIntent().getParcelableExtra(MainActivity.MOVIE_EXTRA);

        if(movie!=null){
            setView(movie);
        }
    }

    private void setView(Movie movie) {

        Glide.with(this)
                .load("http://image.tmdb.org/t/p/w342"+movie.getImgSource())
                .into(imgPoster);

        collapsingToolbarLayout.setTitle(movie.getTitle());

        tvDesc.setText(movie.getDescribtion());
        tvRate.setText(String.format("%s/10", movie.getRating()));
        tvDate.setText(AppUtils.formatDate(movie.getDate()));
        tvGenre.setText(movie.getGenres());
    }


    private void setUp() {

        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.TextAppearance_MyApp_Title_Expanded);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.TextAppearance_MyApp_Title_Collapsed);
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }
}
