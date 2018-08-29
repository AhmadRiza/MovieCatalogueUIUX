package com.example.riza.moviecatalogueuiux.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.riza.moviecatalogueuiux.R;
import com.example.riza.moviecatalogueuiux.data.Repository;
import com.example.riza.moviecatalogueuiux.data.model.Movie;
import com.example.riza.moviecatalogueuiux.ui.adapter.PagerAdapter;
import com.example.riza.moviecatalogueuiux.ui.details.DetailsActivity;
import com.example.riza.moviecatalogueuiux.ui.main.movielist.MovieListCallback;
import com.example.riza.moviecatalogueuiux.ui.main.movielist.MovieListFragment;
import com.example.riza.moviecatalogueuiux.ui.search.SearchActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity implements MovieListCallback {

    public static final String MOVIE_EXTRA = "movieextra";
    public static final String TYPE_EXTRA = "type";
    public static final String SEARCH_QUERY = "search";
    public static final String LANG_EXTRA = "lang";

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.viewpager) ViewPager viewPager;
    @BindView(R.id.tabs) TabLayout tabLayout;

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        unbinder = ButterKnife.bind(this);

        toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);

        setupViewPager();

        tabLayout.setupWithViewPager(viewPager);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        final SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setQueryHint(getResources().getString(R.string.search_hint));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                intent.putExtra(SEARCH_QUERY, query);
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.setting:
                Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(mIntent);
                break;
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void setupViewPager() {
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE_EXTRA, Repository.UPCOMING);
        adapter.addFragment(MovieListFragment.newInstance(bundle), getString(R.string.upcoming));
        bundle = new Bundle();
        bundle.putInt(TYPE_EXTRA,Repository.NOW_PLAYING);
        adapter.addFragment(MovieListFragment.newInstance(bundle),getString(R.string.now_play));
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onMovieClick(Movie movie) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(MOVIE_EXTRA,movie);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }
}
