package com.addonis.demo.repository.contracts;

import com.addonis.demo.models.Rating;
import com.addonis.demo.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends BaseRepository<Rating, Integer> {

    Rating getByUserInfo_IdAndAddon_Id(int userId, int addonId);

    @Query(value = "SELECT IFNULL(AVG(rating), 0) FROM rating WHERE addon_id = :addon_id", nativeQuery = true)
    double getAverageRating(@Param("addon_id") int addonId);
}
