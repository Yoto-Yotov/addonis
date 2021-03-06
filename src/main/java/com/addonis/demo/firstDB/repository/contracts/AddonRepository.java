package com.addonis.demo.firstDB.repository.contracts;

import com.addonis.demo.firstDB.models.Addon;
import com.addonis.demo.firstDB.models.UserInfo;
import com.addonis.demo.firstDB.repository.base.BaseRepository;
import com.addonis.demo.models.enums.Status;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddonRepository extends BaseRepository<Addon, Integer> {

    @Query("select a from Addon a where a.originLink = :originLink")
    Addon findAddonByOriginLink(@Param("originLink") String originLink);

    List<Addon> findTop6ByStatusOrderByIdDesc(Status status);
    List<Addon> findAllByStatusAndNameContaining(Status status, String name);

    @Query(value = "SELECT * FROM addons WHERE ide_name in (SELECT ide_id FROM ide WHERE ide_name = :ide)", nativeQuery = true)
    List<Addon> getAllByIDE(@Param("ide") String ideName);

    List<Addon> findAllByIdeId_IdeName(String ideName);

    List<Addon> findTop6ByStatusOrderByDownloadsCountDesc(Status status);

    @Query(value = "SELECT * FROM addons WHERE status = 'APPROVED' ORDER BY RAND() LIMIT 6", nativeQuery = true)
    List<Addon> get6Random();

    List<Addon> findAllByStatus(Status status, Sort sort);

    @Query("SELECT a from Addon a WHERE a.status = :status")
    List<Addon> getAddonByStatus(@Param(value = "status") Status status);

    @Query("SELECT a from Addon a WHERE a.userInfo = :user")
    List<Addon> getMyAddons(@Param(value = "user") UserInfo user);

    @Query("select a from Addon a where a.name = :name")
    Addon getByName(@Param("name") String name);

    @Modifying
    @Query("UPDATE Addon set enabled = 0 where name = :name")
    void softDeleteAddonInfo(@Param("name") String name);

    boolean existsByName(String name);
}
