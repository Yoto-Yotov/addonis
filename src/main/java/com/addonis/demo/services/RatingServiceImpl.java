package com.addonis.demo.services;

import com.addonis.demo.models.Authorities;
import com.addonis.demo.models.Rating;
import com.addonis.demo.services.contracts.RatingService;
import org.springframework.stereotype.Service;
import java.util.List;

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
    public Authorities create(Rating rating) {

        return null;
    }
}
