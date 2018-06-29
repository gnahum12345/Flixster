package me.gnahum12345.flixster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static me.gnahum12345.flixster.MovieListActivity.POSTER_SIZES;
import static me.gnahum12345.flixster.MovieListActivity.SECURE_BASE_URL;

public class Config {
    // the base url for loading images
    String imageBaseUrl;
    // the poster size to use when fetching images/
    String posterSize;

    public Config(JSONObject object) throws JSONException {
        JSONObject images = object.getJSONObject("images");
        // get image base url
        imageBaseUrl = images.getString(SECURE_BASE_URL);
        // get poster size
        JSONArray posterSizeOptions = images.getJSONArray(POSTER_SIZES);
        // user the option at index 3 or w342 as a fallback
        posterSize = posterSizeOptions.optString(3, "w342");
    }

    // helper method to construct URLs
    public String getImageUrl(String size, String path) {
        return String.format("%s%s%s", imageBaseUrl, size, path); //concatenating the 3 strings.
    }
    public String getImageBaseUrl() {
        return imageBaseUrl;
    }

    public String getPosterSize() {
        return posterSize;
    }
}
