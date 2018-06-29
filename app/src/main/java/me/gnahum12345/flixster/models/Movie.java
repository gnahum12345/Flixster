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

    // instance from API
    private String title;
    private String overview;
    private String posterPath;

    public Movie(JSONObject object) throws JSONException {
        title = object.getString("title");
        overview = object.getString("overview");
        posterPath = object.getString("poster_path");
    }
}
