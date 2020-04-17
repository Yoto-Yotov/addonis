package com.addonis.demo.services;

import com.addonis.demo.exceptions.EntityNotFoundException;
import com.addonis.demo.models.Addon;
import com.addonis.demo.models.Rating;
import com.addonis.demo.models.UserInfo;
import com.addonis.demo.repository.contracts.RatingRepository;
import com.addonis.demo.services.contracts.AddonService;
import com.addonis.demo.services.contracts.RatingService;
import com.addonis.demo.services.contracts.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        UserInfo userInfo = userInfoService.gerUserByUsername(username);
        Addon addon = addonService.getAddonById(addonId);

        Rating ratingObj = ratingRepository.getByUserInfo_IdAndAddon_Id(userInfo.getId(), addonId); //returns null if not exist //returns rating if exists

        if(ratingObj != null) {
            ratingObj.setRating(rating);
            ratingRepository.save(ratingObj);
        } else {
            Rating newRating = Rating.builder().userInfo(userInfo).addon(addon).rating(rating).build();
            ratingRepository.save(newRating);
        }

    }

    @Override
    public double getUserRating(int addonId, String username) {
        UserInfo userInfo = userInfoService.gerUserByUsername(username);
        Addon addon = addonService.getAddonById(addonId);

        Rating rating = ratingRepository.getByUserInfo_IdAndAddon_Id(userInfo.getId(), addon.getId());
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
