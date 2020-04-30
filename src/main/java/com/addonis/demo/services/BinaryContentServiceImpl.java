package com.addonis.demo.services;

import com.addonis.demo.models.BinaryContent;
import com.addonis.demo.repository.contracts.BinaryContentRepository;
import com.addonis.demo.services.contracts.BinaryContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * BinaryContentServiceImpl is used for uploading and downloading content.
 */
@Service
public class BinaryContentServiceImpl implements BinaryContentService {

    private BinaryContentRepository binaryContentRepository;

    @Autowired
    public BinaryContentServiceImpl(BinaryContentRepository binaryContentRepository) {
        this.binaryContentRepository = binaryContentRepository;
    }

    @Override
    public List<BinaryContent> getAll() {
        return binaryContentRepository.findAll();
    }

    @Override
    public BinaryContent getById(Integer integer) {
        return binaryContentRepository.getOne(integer);
    }

    @Override
    public void deleteById(Integer integer) {
        binaryContentRepository.deleteById(integer);
    }

    @Override
    public void update(BinaryContent binaryContent) {
        binaryContentRepository.save(binaryContent);
    }

    @Override
    public BinaryContent create(BinaryContent binaryContent) {
        return binaryContentRepository.save(binaryContent);
    }
}
