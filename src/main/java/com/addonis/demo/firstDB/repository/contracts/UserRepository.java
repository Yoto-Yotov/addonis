package com.addonis.demo.firstDB.repository.contracts;

import com.addonis.demo.firstDB.models.User;
import com.addonis.demo.firstDB.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface UserRepository extends BaseRepository<User, Integer> {

    @Query("select u from User u where u.username = :name")
    User getByName(@Param("name") String name);

    @Modifying
    @Query("update User set enabled = 0 where username =: name")
    void softDelete(@Param("name") String userName);

    boolean existsByUsername(String name);
    User findUserByUsername(String username);
}
