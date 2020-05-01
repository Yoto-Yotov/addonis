package com.addonis.demo.models;

public class RatingDTO {

    private int rating;

    private String username;

    private int addonID;

    public RatingDTO() {
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAddonID() {
        return addonID;
    }

    public void setAddonID(int addonID) {
        this.addonID = addonID;
    }
}
