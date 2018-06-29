package me.gnahum12345.flixster;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import me.gnahum12345.flixster.models.Config;
import me.gnahum12345.flixster.models.Movie;

public class MovieListActivity extends AppCompatActivity {

    // instance fields
    AsyncHttpClient client;

    // the list of currently playing movies
    ArrayList<Movie> movies;
    // the recycler view
    RecyclerView rvMovies;
    // the adapter wired to the recyclerView
    MovieAdapter adapter;

    //image config
    Config config;

    // TAG
    private final String TAG = "DEBUGGINGHAHAHAHAHA";

    public final static String SECURE_BASE_URL = "secure_base_url";
    public final static String POSTER_SIZES = "poster_sizes";
    // Base URL for the API
    public final static String API_BASE_URL = "https://api.themoviedb.org/3";
    // The parameter name for the API_KEY
    public final static String API_KEY_PARAM = "api_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializing Client
        client = new AsyncHttpClient();
        //initalize the list of movies
        movies = new ArrayList<>();
        // initialize the adapter -- movies array cannot be reinitialized after this point.
        adapter = new MovieAdapter(movies);
        //resolve the recycler view and connect a layout manager and the adapter.
        rvMovies = findViewById(R.id.rvMovies);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvMovies.setLayoutManager(manager);
        rvMovies.setAdapter(adapter);
        // get the configuration on app creation.
        getConfigurations();
    }

    private void getNowPlaying() {
        // create the URL
        String url = API_BASE_URL + "/movie/now_playing";
        // Set the request parameters
        RequestParams params =  new RequestParams();
        params.put(API_KEY_PARAM, getString(R.string.API_KEY)); //API key, always requires.
        //execute the GET Request.
        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray results = response.getJSONArray("results");

                    for (int i = 0; i < results.length(); i++) {
                        Movie movie = new Movie(results.getJSONObject(i));
                        movies.add(movie);
                        // notify adapter that a row was added
                        adapter.notifyItemInserted(movies.size() - 1);
                    }
                    Log.i(TAG, String.format("\n\n\n\\n\nLoading %s movies", results.length()));
                } catch (JSONException e) {
                    logError("Failed to parse now playing movies", e, true);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.i(TAG,"\n\n\nFailed to get the data from now_playing endpoint 1 ");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.i(TAG,"\n\n\nFailed to get the data from now_playing endpoint 2 ");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.i(TAG,"\n\n\nFailed to get the data from now_playing endpoint 3 ");
                Log.i(TAG, throwable.getMessage());
                Log.i(TAG, errorResponse.toString());
            }
        });

    }

    //Retrieve the configurations From the API
    private void getConfigurations() {
        // create the URL
        String url = API_BASE_URL + "/configuration";
        // Set the request parameters
        RequestParams params =  new RequestParams();
        params.put(API_KEY_PARAM, getString(R.string.API_KEY)); //API key, always requires.
        //execute the GET Request.

        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
               try {
                   config = new Config(response);
                   Log.i(TAG, String.format("Loaded configurations with imageBaseUrl %s and posterSize %s",
                           config.getImageBaseUrl(),
                           config.getPosterSize()));
                   //pass to adapter.
                   adapter.setConfig(config);
                   getNowPlaying();
               } catch (JSONException e) {
                   logError("Failed parsing configuration", e, true);
               }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.i(TAG,"\n\n\nFailed to get the data from config endpoint 1 ");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.i(TAG,"\n\n\nFailed to get the data from config endpoint 2 ");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.i(TAG,"\n\n\nFailed to get the data from config endpoint 3 ");
                Log.i(TAG, throwable.getMessage());
                Log.i(TAG, errorResponse.toString());
            }


        });

    }

    //handle errors, log and alert user
    private void logError(String message, Throwable error, boolean alertUser) {
        Log.e(TAG, message, error);

        if (alertUser) {
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        }
    }
}
