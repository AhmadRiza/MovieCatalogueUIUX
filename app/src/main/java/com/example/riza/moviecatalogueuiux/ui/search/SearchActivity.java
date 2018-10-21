package com.example.riza.moviecatalogueuiux.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

import com.example.riza.moviecatalogueuiux.R;
import com.example.riza.moviecatalogueuiux.data.network.Repository;
import com.example.riza.moviecatalogueuiux.data.network.RequestCallback;
import com.example.riza.moviecatalogueuiux.data.model.Movie;
import com.example.riza.moviecatalogueuiux.ui.adapter.ItemClickListener;
import com.example.riza.moviecatalogueuiux.ui.adapter.MovieAdapter;
import com.example.riza.moviecatalogueuiux.ui.details.DetailsActivity;
import com.example.riza.moviecatalogueuiux.ui.main.MainActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SearchActivity extends AppCompatActivity {

    private static final String DATA_KEY = "datum";
    private static final String QUERY_KEY = "query";

    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    Unbinder unbinder;
    MovieAdapter adapter;
    Repository repository;
    ArrayList<Movie> data = new ArrayList<>();

    String query = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        unbinder = ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        adapter = new MovieAdapter(this);
        repository = new Repository(this);

        adapter.setOnItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(SearchActivity.this, DetailsActivity.class);
                intent.putExtra(MainActivity.MOVIE_EXTRA, adapter.getItem(position));
                startActivity(intent);
            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(adapter);

        if(savedInstanceState!=null){
            data = savedInstanceState.getParcelableArrayList(DATA_KEY);
            query = savedInstanceState.getString(QUERY_KEY);
        }else{
            query = getIntent().getStringExtra(MainActivity.SEARCH_QUERY);
            loadData();
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if(!data.isEmpty()){
            outState.putParcelableArrayList(DATA_KEY, data);
        }
        outState.putString(QUERY_KEY, query);
        super.onSaveInstanceState(outState);
    }

    private void loadData() {
        refreshLayout.setRefreshing(true);
        repository.getMovie(Repository.SEARCH, query, new RequestCallback() {
            @Override
            public void onSucess(ArrayList<Movie> movies) {
                data = movies;
                updateList();
                refreshLayout.setRefreshing(false);
            }
            @Override
            public void onError(String message) {
                refreshLayout.setRefreshing(false);
                Toast.makeText(SearchActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateList() {
        adapter.setDataSet(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        final SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        MenuItem searchItem = menu.findItem(R.id.search);
//        searchView.setIconified(false);
        searchView.setQueryHint(getResources().getString(R.string.search_hint));
        searchItem.expandActionView();

        searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                return false;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                finish();
                return false;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                query = s;
                loadData();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        searchView.setQuery(query,true);
        searchView.clearFocus();
        return true;
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }
}
