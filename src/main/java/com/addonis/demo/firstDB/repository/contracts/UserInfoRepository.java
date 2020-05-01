package com.addonis.demo.firstDB.repository.contracts;

import com.addonis.demo.firstDB.models.UserInfo;
import com.addonis.demo.firstDB.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface UserInfoRepository extends BaseRepository<UserInfo, Integer> {

    //Using Normal @Query Anotation
    @Query("select u from UserInfo u where u.email = ?1")
    UserInfo findByEmailAddress(String emailAddress);

    //Using Named Parameters
    @Query("select u from UserInfo u where u.name = :name")
    UserInfo getByUserName(@Param("name") String name);

    @Modifying
    @Query("UPDATE UserInfo set enabled = 0 where name = :name")
    void softDeleteUserInfo(@Param("name") String name);

    boolean existsByEmail(String email);

    boolean existsByName(String username);

    boolean existsById(Integer id);
}
