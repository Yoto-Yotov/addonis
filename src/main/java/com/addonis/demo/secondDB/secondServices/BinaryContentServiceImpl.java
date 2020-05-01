package com.addonis.demo.secondDB.secondServices;

import com.addonis.demo.secondDB.secondModels.BinaryContent;
import com.addonis.demo.secondDB.secondRepository.contracts.BinaryContentRepository;
import com.addonis.demo.secondDB.secondServices.contracts.BinaryContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.List;

/**
 * BinaryContentServiceImpl is used for uploading and downloading content.
 */
@PersistenceContext(type = PersistenceContextType.EXTENDED)
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
