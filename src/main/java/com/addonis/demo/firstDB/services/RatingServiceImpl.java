package com.addonis.demo.firstDB.services;

import com.addonis.demo.firstDB.models.Addon;
import com.addonis.demo.firstDB.models.Rating;
import com.addonis.demo.firstDB.models.UserInfo;
import com.addonis.demo.firstDB.repository.contracts.RatingRepository;
import com.addonis.demo.firstDB.services.contracts.AddonService;
import com.addonis.demo.firstDB.services.contracts.RatingService;
import com.addonis.demo.firstDB.services.contracts.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * RatingServiceImpl provides information about the average rating and responsible for rating an addon.
 */

@Service
public class RatingServiceImpl implements RatingService {

    private RatingRepository ratingRepository;
    private UserInfoService userInfoService;
    private AddonService addonService;

    @Autowired
    public RatingServiceImpl(RatingRepository ratingRepository, UserInfoService userInfoService, AddonService addonService) {
        this.ratingRepository = ratingRepository;
        this.userInfoService = userInfoService;
        this.addonService = addonService;
    }

    @Override
    public void rateAddon(int addonId, String username, int rating) {
        UserInfo userInfo = userInfoService.getUserByUsername(username);
        Addon addon = addonService.getById(addonId);

        Rating ratingObj = ratingRepository.getByUserInfoIdAndAddonId(userInfo.getId(), addonId); //returns null if not exist //returns rating if exists

        if(ratingObj != null) {
            ratingObj.setRating(rating);
            ratingRepository.save(ratingObj);
        } else {
            //ToDo
//            Rating newRating = Rating.builder().userInfo(userInfo).addon(addon).rating(rating).build();
//            ratingRepository.save(newRating);
        }

    }

    @Override
    public double getUserRating(int addonId, String username) {
        UserInfo userInfo = userInfoService.getUserByUsername(username);
        Addon addon = addonService.getById(addonId);

        Rating rating = ratingRepository.getByUserInfoIdAndAddonId(userInfo.getId(), addon.getId());
        if(rating == null) {
            return 0;
        }

        return rating.getRating();
    }

    @Override
    public double getAddonRating(int addonId) {
        return ratingRepository.getAverageRating(addonId);
    }

}
