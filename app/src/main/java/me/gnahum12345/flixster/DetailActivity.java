package me.gnahum12345.flixster;

import android.content.res.Configuration;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import me.gnahum12345.flixster.models.Config;
import me.gnahum12345.flixster.models.Movie;

public class DetailActivity extends AppCompatActivity {


    private static final String TAG = "DEBUGGINGING_HAHAHAHAH";
    Movie movie;

    // the view objects
    TextView tvTitle;
    TextView tvOverview;
    RatingBar rbVoteAverage;
    ImageView ivImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        movie = createMovie();
        String posterSize = getIntent().getStringExtra("posterSize");
        String backdropSize = getIntent().getStringExtra("backdropSize");
        String baseURL = getIntent().getStringExtra("baseURL");
        Log.i(TAG, "Detail Activity works");

        tvTitle = findViewById(R.id.tvTitle);
        tvOverview = findViewById(R.id.tvOverview);
        rbVoteAverage = findViewById(R.id.rbVoteAverage);
        ivImage = findViewById(R.id.ivPosterImage);
        // set the title and overview
        tvTitle.setTextColor(Color.WHITE);
        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());


        // vote average is 0..10, convert to 0..5 by dividing by 2
        float voteAverage = movie.getVoteAverage().floatValue();
        rbVoteAverage.setRating(voteAverage > 0 ? voteAverage / 2.0f : voteAverage);
//        rbVoteAverage.setRating(3);


        boolean isPortrait = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;

        // build url for poster image
        String imageURL;
        // load image using glide

        //poster v. backdrop

        if (isPortrait) {
            imageURL = Config.getImageUrl(posterSize, movie.getPosterPath(), baseURL);
        } else {
            imageURL = Config.getImageUrl(backdropSize, movie.getBackdropPath(), baseURL);
        }

        Log.i(TAG, imageURL);
        int placeholder_ID = isPortrait ? R.drawable.flicks_movie_placeholder : R.drawable.flicks_backdrop_placeholder;
        Glide.with(getApplicationContext())
                .load(imageURL)
                .apply(RequestOptions.placeholderOf(placeholder_ID)
                        .error(placeholder_ID)
                        .fitCenter())
                .into(ivImage);
    }

    private Movie createMovie() {
        String title = getIntent().getStringExtra("title");
        String overview = getIntent().getStringExtra("overview");
        String posterPath = getIntent().getStringExtra("posterPath");
        String backdropPath = getIntent().getStringExtra("backdropPath");
        Double voterAverage = getIntent().getDoubleExtra("voterAverage", 0);
        return new Movie(title, overview, posterPath, backdropPath, voterAverage);

    }

}

