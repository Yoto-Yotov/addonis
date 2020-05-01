package com.addonis.demo.firstDB.repository.contracts;

import com.addonis.demo.firstDB.models.Authorities;
import com.addonis.demo.firstDB.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorityRepository extends BaseRepository<Authorities, Integer> {

    @Query("select u.authority from Authorities u where u.username = :name")
    List<Authorities> getUserAuthority(@Param("name") String name);

    List<Authorities> getByUsername(String username);

}
