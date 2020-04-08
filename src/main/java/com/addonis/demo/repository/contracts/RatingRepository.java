package com.addonis.demo.repository.contracts;

import com.addonis.demo.models.Rating;
import com.addonis.demo.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends BaseRepository<Rating, Integer> {
}
