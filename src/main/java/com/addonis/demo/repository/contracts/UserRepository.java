package com.addonis.demo.repository.contracts;

import com.addonis.demo.models.User;
import com.addonis.demo.models.UserInfo;
import com.addonis.demo.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface UserRepository extends BaseRepository<User, String>{

    @Query("select u from User u where u.username = :name")
    User getByName(@Param("name") String name);

    @Modifying
    @Query("update User set enabled = 0 where username =: name")
    void softDelete(@Param("name") String userName);

    boolean existsByUsername(String name);
}
