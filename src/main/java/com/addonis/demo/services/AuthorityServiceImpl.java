package com.addonis.demo.services;

import com.addonis.demo.models.Authorities;
import com.addonis.demo.repository.contracts.AuthorityRepository;
import com.addonis.demo.services.contracts.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorityServiceImpl implements AuthorityService {

    private AuthorityRepository authorityRepository;

    @Autowired
    public AuthorityServiceImpl(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    @Override
    public List<Authorities> getAll() {
        return null;
    }

    @Override
    public Authorities getById(String s) {
        return null;
    }

    @Override
    public void deleteById(String s) {

    }

    @Override
    public void update(Authorities authorities) {
    }

    @Override
    public Authorities create(Authorities authorities) {
        return authorityRepository.save(authorities);
    }
}
