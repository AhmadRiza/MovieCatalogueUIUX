package com.example.riza.moviecatalogueuiux.ui.main.favorite;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.riza.moviecatalogueuiux.R;
import com.example.riza.moviecatalogueuiux.ui.adapter.ItemClickListener;
import com.example.riza.moviecatalogueuiux.ui.adapter.MovieCursorAdapter;
import com.example.riza.moviecatalogueuiux.ui.main.movielist.MovieListCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{


    private static final String MOVIES_KEY = "moov";
    public static final String ACTION_ADDED_FAVORITE = "favfav";

    @BindView(R.id.rv_upcoming)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_layout_list)
    SwipeRefreshLayout refreshLayout;

    MovieListCallback mCallback;
    MovieCursorAdapter adapter;
    private Unbinder unbinder;

    BroadcastReceiver favReceiver;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);
        unbinder = ButterKnife.bind(this, view);

        setAddedFavReceiver();
        adapter = new MovieCursorAdapter(getContext());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        mRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                mCallback.onMovieClick(adapter.getItem(position));
            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
        loadData();
        return view;
    }

    private void setAddedFavReceiver() {
        favReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                loadData();
            }
        };
        IntentFilter favFilter = new IntentFilter(ACTION_ADDED_FAVORITE);
        getActivity().registerReceiver(favReceiver, favFilter);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof MovieListCallback){
            mCallback = (MovieListCallback) context;
        }else{
            Toast.makeText(context, R.string.must_implement, Toast.LENGTH_SHORT).show();
        }
    }

    public void loadData(){
        refreshLayout.setRefreshing(true);
        getLoaderManager().restartLoader(1,null,this);
    }

    @Override
    public void onDestroy() {
        if(unbinder!=null){
            unbinder.unbind();
        }
        if(favReceiver!=null){
            getActivity().unregisterReceiver(favReceiver);
        }
        super.onDestroy();
    }


    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new FavLoader(getContext());
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        adapter.setData(data);
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        adapter.setData(null);
    }
}
