package com.addonis.demo.repository.contracts;

import com.addonis.demo.models.Addon;
import com.addonis.demo.models.UserInfo;
import com.addonis.demo.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddonRepository extends BaseRepository<Addon, Integer> {

    @Query("select a from Addon a where a.originLink = :originLink")
    Addon findAddonByOriginLink(@Param("originLink") String originLink);

    List<Addon> findTop6ByOrderByIdDesc();
    List<Addon> findTop6ByOrderByDownloadsCountDesc();

    @Query(value = "SELECT * FROM addons ORDER BY RAND() LIMIT 6", nativeQuery = true)
    List<Addon> get6Random();
}
