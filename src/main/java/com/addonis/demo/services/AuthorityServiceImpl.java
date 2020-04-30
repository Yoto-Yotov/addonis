package com.addonis.demo.services;

import com.addonis.demo.models.Authorities;
import com.addonis.demo.repository.contracts.AuthorityRepository;
import com.addonis.demo.services.contracts.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * AuthorityServiceImpl - Service for checking users permissions.
 */
@Service
public class AuthorityServiceImpl implements AuthorityService {

    private AuthorityRepository authorityRepository;

    @Autowired
    public AuthorityServiceImpl(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    @Override
    public List<Authorities> getAll() {
        return authorityRepository.findAll();
    }

    @Override
    public Authorities getById(Integer id) {
        return authorityRepository.getOne(id);
    }

    @Override
    public void deleteById(Integer id) {
        authorityRepository.deleteById(id);
    }

    @Override
    public void update(Authorities authorities) {
        authorityRepository.save(authorities);
    }

    @Override
    public Authorities create(Authorities authorities) {
        return authorityRepository.save(authorities);
    }

}
