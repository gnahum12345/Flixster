package me.gnahum12345.flixster;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import me.gnahum12345.flixster.models.Config;
import me.gnahum12345.flixster.models.Movie;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{

    public MovieAdapter(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    ArrayList<Movie> movies;

    public void setConfig(Config config) {
        this.config = config;
    }

    //config needed for image urls
    Config config;
    //Context for rendering.
    Context context;
    // creates and inflates a new view.
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //get the context and create the inflater.
        context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // creat eht view using the item_movie layout.
        View movieView = inflater.inflate(R.layout.item_movie, viewGroup, false);
        // return a new viewHolder,
        return new ViewHolder(movieView);
    }


    // binds an inflated view to a new item.
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        // get the movie data at the specified position
        Movie movie = movies.get(i);
        // populate the view with the movie data
        viewHolder.tvTitle.setText(movie.getTitle());
        viewHolder.tvOverview.setText(movie.getOverview());

        //build url for poster image
        String imageURL = config.getImageUrl(config.getPosterSize(), movie.getPosterPath());
        // load image using glide
        Glide.with(context)
                .load(imageURL )
                .placeholder(R.drawable.flicks_movie_placeholder)
                .error(R.drawable.flicks_movie_placeholder)
                .into(viewHolder.ivPosterImage);
    }

    // returns the size of the dataset.
    @Override
    public int getItemCount() {
        return movies.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        // track view objects
        ImageView ivPosterImage;
        TextView  tvTitle;
        TextView  tvOverview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //lookup view objects by id.

            ivPosterImage = itemView.findViewById(R.id.ivPosterImage);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            tvTitle = itemView.findViewById(R.id.tvTitle);
        }
    }


}
