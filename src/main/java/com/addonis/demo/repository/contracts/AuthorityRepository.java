package com.addonis.demo.repository.contracts;

import com.addonis.demo.models.Authorities;
import com.addonis.demo.models.UserInfo;
import com.addonis.demo.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AuthorityRepository extends BaseRepository<Authorities, String> {

    @Query("select u.authority from Authorities u where u.username = :name")
    List<Authorities> getUserAuthority(@Param("name") String name);

    List<Authorities> getByUsername(String username);

}
