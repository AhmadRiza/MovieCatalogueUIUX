package com.example.riza.moviecatalogueuiux.ui.main.movielist;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.riza.moviecatalogueuiux.R;
import com.example.riza.moviecatalogueuiux.data.network.Repository;
import com.example.riza.moviecatalogueuiux.data.network.RequestCallback;
import com.example.riza.moviecatalogueuiux.data.model.Movie;
import com.example.riza.moviecatalogueuiux.ui.adapter.ItemClickListener;
import com.example.riza.moviecatalogueuiux.ui.adapter.MovieAdapter;
import com.example.riza.moviecatalogueuiux.ui.main.MainActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieListFragment extends Fragment {

    private static final String MOVIES_KEY = "moov";
    private int requestType;

    @BindView(R.id.rv_upcoming) RecyclerView mRecyclerView;
    @BindView(R.id.swipe_layout_list) SwipeRefreshLayout refreshLayout;

    MovieListCallback mCallback;
    MovieAdapter adapter;
    private Unbinder unbinder;
    Repository repository;
    ArrayList<Movie> data = new ArrayList<>();

    public MovieListFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance(Bundle bundle){
        Fragment me = new MovieListFragment();
        me.setArguments(bundle);
        return me;
    }


    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        retrieveArgument();

        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);
        unbinder = ButterKnife.bind(this, view);

        repository = new Repository(getContext());
        adapter = new MovieAdapter(getContext());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        mRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(int position) {
                mCallback.onMovieClick(adapter.getItem(position));
            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });

        if(savedInstanceState!=null){
            data = savedInstanceState.getParcelableArrayList(MOVIES_KEY);
            updateList();

        }else{
            loadData();
        }

        return view;
    }

    private void updateList() {
        adapter.setDataSet(data);
        adapter.notifyDataSetChanged();
    }

    private void retrieveArgument() {
        if(getArguments()!=null){
            requestType = getArguments().getInt(MainActivity.TYPE_EXTRA, Repository.UPCOMING);
        }
    }

//    simpan data ketika rotate screen
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        if(!data.isEmpty()){
            outState.putParcelableArrayList(MOVIES_KEY, data);
        }
        super.onSaveInstanceState(outState);
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
        repository.getMovie(requestType, null, new RequestCallback() {
            @Override
            public void onSucess(ArrayList<Movie> movies) {
               data = movies;
               updateList();
               refreshLayout.setRefreshing(false);
            }

            @Override
            public void onError(String message) {
                refreshLayout.setRefreshing(false);
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroy() {
        if(unbinder!=null){
            unbinder.unbind();
        }
        if(repository!=null){
            repository.cancelAllRequest();
        }
        super.onDestroy();
    }


}
