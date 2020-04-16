package com.addonis.demo.services;

import com.addonis.demo.models.Rating;
import com.addonis.demo.services.contracts.RatingService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * RatingServiceImpl
 * Provides informaiton about the average rating. No authentication needed
 * Rates an addon. Authentication needed - user or admin.
 */
@Service
public class RatingServiceImpl implements RatingService {

    @Override
    public List<Rating> getAll() {
        return null;
    }

    @Override
    public Rating getById(Integer integer) {
        return null;
    }

    @Override
    public void deleteById(Integer integer) {

    }

    @Override
    public void update(Rating rating) {

    }

    @Override
    public Rating create(Rating rating) {

        return null;
    }
}
