package com.example.riza.moviecatalogueuiux.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.riza.moviecatalogueuiux.R;
import com.example.riza.moviecatalogueuiux.data.model.Movie;
import com.example.riza.moviecatalogueuiux.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by riza on 16/08/18.
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.GridViewHolder> {
    private Context context;
    private List<Movie> movieList;
    private ItemClickListener mListener;

    public Movie getItem(int position) {
        return movieList.get(position);
    }

    public void setDataSet(List<Movie> movieList) {
        this.movieList = movieList;
    }

    public MovieAdapter(Context context) {
        this.context = context;
        movieList = new ArrayList<>();
    }

    public void setOnItemClickListener(ItemClickListener listener){
        mListener = listener;
    }

    @NonNull
    @Override
    public GridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_list, parent, false);
        final GridViewHolder mHolder = new GridViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mListener!=null){
                    mListener.onClick(mHolder.getPosition());
                }
            }
        });

        return mHolder;
    }

    @Override
    public void onBindViewHolder(GridViewHolder holder, int position) {
        Movie curItem = getItem(position);
        holder.tvTitle.setText(String.format("%s (%s)",curItem.getTitle(),AppUtils.formatYear(curItem.getDate())));
        holder.tvGenre.setText(curItem.getGenres());
        holder.tvRating.setText(String.format("%s/10", curItem.getRating()));
        holder.tvDesc.setText(String.format("%s...", AppUtils.formatDesc(curItem.getDesc())));
        Glide.with(context)
                .load("http://image.tmdb.org/t/p/w185"+curItem.getImgSource())
                .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(6)))
                .into(holder.imgPoster);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    class GridViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_title) TextView tvTitle;
        @BindView(R.id.tv_rating) TextView tvRating;
        @BindView(R.id.tv_genre) TextView tvGenre;
        @BindView(R.id.tv_desc) TextView tvDesc;
        @BindView(R.id.img_poster) ImageView imgPoster;

        GridViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
