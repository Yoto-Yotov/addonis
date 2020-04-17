package com.addonis.demo.services.contracts;

public interface RatingService {
    void rateAddon(int addonId, String username, int rating);
    double getUserRating(int addonId, String username);
    double getAddonRating(int addonId);
}
