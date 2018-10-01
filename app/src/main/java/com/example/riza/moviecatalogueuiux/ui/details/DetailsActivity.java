package com.example.riza.moviecatalogueuiux.ui.details;

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
import com.example.riza.moviecatalogueuiux.R;
import com.example.riza.moviecatalogueuiux.data.db.DBContract;
import com.example.riza.moviecatalogueuiux.data.model.Movie;
import com.example.riza.moviecatalogueuiux.ui.main.MainActivity;
import com.example.riza.moviecatalogueuiux.ui.main.favorite.FavoriteFragment;
import com.example.riza.moviecatalogueuiux.utils.AppUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.example.riza.moviecatalogueuiux.data.db.DBContract.CONTENT_URI;

public class DetailsActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 110;

    private Unbinder unbinder;
    private Movie movie;
    private boolean isFavorite = false;

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
            Uri uri = Uri.parse(CONTENT_URI+"/"+movie.getId());
            if(getContentResolver().query(uri,null,null,null,null, null).moveToFirst()){
                isFavorite = true;
            }else{
                fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorUnFav)));
            }
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

        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.TextAppearance_MyApp_Title_Expanded);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.TextAppearance_MyApp_Title_Collapsed);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isFavorite){
                    isFavorite = false;
                    fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorUnFav)));
                    Uri uri = Uri.parse(CONTENT_URI+"/"+movie.getId());
                    getContentResolver().delete(uri,null,null);
                    Toast.makeText(DetailsActivity.this, String.format(getString(R.string.remove_fav), movie.getTitle()), Toast.LENGTH_SHORT).show();
                }else{
                    isFavorite = true;
                    fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorFav)));
                    getContentResolver().insert(CONTENT_URI, DBContract.getContentValues(movie));
                    Toast.makeText(DetailsActivity.this, String.format(getString(R.string.add_favorite), movie.getTitle()), Toast.LENGTH_SHORT).show();
                }
                notifyFavorite();
            }
        });
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    private void notifyFavorite(){
        Intent notifyFinishIntent = new Intent(FavoriteFragment.ACTION_ADDED_FAVORITE);
        sendBroadcast(notifyFinishIntent);
    }
}
