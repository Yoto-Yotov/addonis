package com.addonis.demo.repository.contracts;

import com.addonis.demo.models.UserInfo;
import com.addonis.demo.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends BaseRepository<UserDetails, Integer> {

    //Using Normal @Query Anotation
    @Query("select u from UserInfo u where u.email = ?1")
    UserInfo findByEmailAddress(String emailAddress);

    //Using Named Parameters
    @Query("select u from UserInfo u where u.firstName = :firstname")
    UserInfo findByFirstname(@Param("firstname") String firstname);

}
