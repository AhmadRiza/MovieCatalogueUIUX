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
import com.example.riza.moviecatalogueuiux.data.network.Repository;
import com.example.riza.moviecatalogueuiux.data.model.Movie;
import com.example.riza.moviecatalogueuiux.data.prefs.MyPreference;
import com.example.riza.moviecatalogueuiux.ui.adapter.PagerAdapter;
import com.example.riza.moviecatalogueuiux.ui.details.DetailsActivity;
import com.example.riza.moviecatalogueuiux.ui.main.favorite.FavoriteFragment;
import com.example.riza.moviecatalogueuiux.ui.main.movielist.MovieListCallback;
import com.example.riza.moviecatalogueuiux.ui.main.movielist.MovieListFragment;
import com.example.riza.moviecatalogueuiux.ui.search.SearchActivity;
import com.example.riza.moviecatalogueuiux.utils.AlarmHelper;

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

    private PagerAdapter pagerAdapter;
    private Unbinder unbinder;
    private AlarmHelper alarmHelper;
    private MyPreference myPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        unbinder = ButterKnife.bind(this);

        toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);

        setupViewPager();

        tabLayout.setupWithViewPager(viewPager);
        alarmHelper = new AlarmHelper(this);
        myPreference = new MyPreference(this);

        if(myPreference.isFirstLaunch()){

            myPreference.setisFirstLaunch(false);
            if(!alarmHelper.isDailySet()){
                alarmHelper.setDailyReminder(true);
            }

            if(!alarmHelper.isReleaseSet()){
                alarmHelper.setReleaseReminder(true);
            }


        }


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

        if(alarmHelper.isDailySet()){
            menu.getItem(2).setTitle("Disable Daily Reminder");
        }else{
            menu.getItem(2).setTitle("Enable Daily Reminder");
        }
        if(alarmHelper.isReleaseSet()){
            menu.getItem(3).setTitle("Disable Release Reminder");
        }else{
            menu.getItem(3).setTitle("Enable Release Reminder");
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.setting:
                Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(mIntent);
                break;
            case R.id.daily_reminder:

                if(alarmHelper.isDailySet()){
                    alarmHelper.setDailyReminder(false);
                    item.setTitle("Enable Daily Reminder");
                }else{
                    alarmHelper.setDailyReminder(true);
                    item.setTitle("Disable Daily Reminder");
                }
                break;
            case R.id.release_reminder:
                if(alarmHelper.isReleaseSet()){
                    alarmHelper.setReleaseReminder(false);
                    item.setTitle("Enable Release Reminder");
                }else{
                    alarmHelper.setReleaseReminder(true);
                    item.setTitle("Disable Release Reminder");
                }
                break;
        }
        return false;
    }

    private void setupViewPager() {
        pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE_EXTRA,Repository.NOW_PLAYING);
        pagerAdapter.addFragment(MovieListFragment.newInstance(bundle),getString(R.string.now_play));
        bundle = new Bundle();
        bundle.putInt(TYPE_EXTRA, Repository.UPCOMING);
        pagerAdapter.addFragment(MovieListFragment.newInstance(bundle), getString(R.string.upcoming));
        pagerAdapter.addFragment(new FavoriteFragment(),getString(R.string.favorite));

        viewPager.setAdapter(pagerAdapter);

    }

    @Override
    public void onMovieClick(Movie movie) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(MOVIE_EXTRA,movie);
        startActivityForResult(intent,DetailsActivity.REQUEST_CODE);
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

}
