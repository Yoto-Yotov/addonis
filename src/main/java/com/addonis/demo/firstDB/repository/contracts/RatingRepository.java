package com.addonis.demo.firstDB.repository.contracts;

import com.addonis.demo.firstDB.models.Rating;
import com.addonis.demo.firstDB.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends BaseRepository<Rating, Integer> {

    Rating getByUserInfoIdAndAddonId(int userId, int addonId);

    @Query(value = "SELECT IFNULL(AVG(rating), 0) FROM rating WHERE addon_id = :addon_id", nativeQuery = true)
    double getAverageRating(@Param("addon_id") int addonId);
}
