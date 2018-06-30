package me.gnahum12345.flixster.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Movie {

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }
    public Double getVoteAverage() {
        return voteAverage;
    }

    // instance from API
    private String title;
    private String overview;
    private String posterPath;
    private String backdropPath;
    private Double voteAverage;

    public Movie(JSONObject object) throws JSONException {
        title = object.getString("title");
        overview = object.getString("overview");
        posterPath = object.getString("poster_path");
        backdropPath = object.getString("backdrop_path");
        voteAverage = object.getDouble("vote_average");
    }

    public Movie(Object object) throws JSONException {
        title = ((JSONObject) object).getString("name");
        overview = ((JSONObject) object).getString("overview");
        posterPath = ((JSONObject) object).getString("poster_path");
        posterPath = ((JSONObject) object).getString("backdrop_path");
        voteAverage = ((JSONObject) object).getDouble("vote_average");
    }



    public Movie(String title, String overview, String posterPath, String backdropPath, Double voterAverage) {
        this.title = title;
        this.overview = overview;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.voteAverage = voterAverage;
    }

}
