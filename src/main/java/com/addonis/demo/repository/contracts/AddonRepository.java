package com.addonis.demo.repository.contracts;

import com.addonis.demo.models.Addon;
import com.addonis.demo.models.UserInfo;
import com.addonis.demo.models.enums.Status;
import com.addonis.demo.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddonRepository extends BaseRepository<Addon, Integer> {

    @Query("select a from Addon a where a.originLink = :originLink")
    Addon findAddonByOriginLink(@Param("originLink") String originLink);

    @Query("SELECT a from Addon a WHERE a.status = :status")
    List<Addon> getAddonByStatus(@Param(value = "status") Status status);

    @Query("SELECT a from Addon a WHERE a.userInfo = :user")
    List<Addon> getMyAddons(@Param(value = "user") UserInfo user);

    @Query("select a from Addon a where a.name = :name")
    Addon getByName(@Param("name") String name);

    boolean existsByName(String name);

}
