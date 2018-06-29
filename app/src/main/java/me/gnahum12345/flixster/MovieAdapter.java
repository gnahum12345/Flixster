package me.gnahum12345.flixster;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.TransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
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
        final Movie movie = movies.get(i);
        // populate the view with the movie data
        viewHolder.tvTitle.setText(movie.getTitle());
        viewHolder.tvOverview.setText(movie.getOverview());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class );
                intent.putExtra("title", movie.getTitle());
                intent.putExtra("overview", movie.getOverview());
                intent.putExtra("posterPath", movie.getPosterPath());
                intent.putExtra("backdropPath", movie.getBackdropPath());
                Toast.makeText(context, "BYEEEE", Toast.LENGTH_LONG).show();
                context.startActivity(intent);

            }
        });
        // determine current orientation
        boolean isPortrait = context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;

        // build url for poster image
        String imageURL;
        // load image using glide

        //poster v. backdrop

        if (isPortrait) {
           imageURL = config.getImageUrl(config.getPosterSize(), movie.getPosterPath());
        } else {
           imageURL = config.getImageUrl(config.getBackdropSize(), movie.getBackdropPath());
        }

        int placeholder_ID = isPortrait ? R.drawable.flicks_movie_placeholder : R.drawable.flicks_backdrop_placeholder;
        ImageView imageView = isPortrait ? viewHolder.ivPosterImage : viewHolder.ivBackdropImage;
        Glide.with(viewHolder.itemView)
                .load(imageURL)
                .apply(RequestOptions.placeholderOf(placeholder_ID)
                        .error(placeholder_ID)
                        .fitCenter())
                .apply(RequestOptions.bitmapTransform(new RoundedCornersTransformation(25, 0)))
                .into(imageView);
    }

    // returns the size of the dataset.
    @Override
    public int getItemCount() {
        return movies.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {

        // track view objects
        ImageView ivPosterImage;
        ImageView ivBackdropImage;
        TextView  tvTitle;
        TextView  tvOverview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //lookup view objects by id.
            ivBackdropImage = itemView.findViewById(R.id.ivBackdropImage);
            ivPosterImage = itemView.findViewById(R.id.ivPosterImage);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            tvTitle = itemView.findViewById(R.id.tvTitle);
        }
    }



}
