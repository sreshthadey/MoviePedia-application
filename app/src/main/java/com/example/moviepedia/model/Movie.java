package com.example.moviepedia.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Movie {
    private  String title;
    private String release_date;
    private String rating;
    private String language;
    private String img_url;
    private String base_url = "http://image.tmdb.org/t/p/w500";
    private String desc;

    public Movie(JSONObject jsonObject) throws JSONException {
        this.title = jsonObject.getString("original_title");
        this.release_date = jsonObject.getString("release_date");
        this.rating = jsonObject.getString("vote_average");
        this.language = jsonObject.getString("original_language");
        this.img_url = this.base_url+jsonObject.getString("poster_path");
        this.desc = jsonObject.getString("overview");
    }

    public String getTitle() {
        return title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getRating() {
        return rating;
    }

    public String getLanguage() {
        return language;
    }

    public String getImg_url() {
        //this.img_url = img_url.replaceFirst("/+","");
        return this.img_url;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
    @Override
    public String toString() {
        return this.title;
    }
}
