package com.addonis.demo.services.contracts;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    void saveImageFile(int userId, MultipartFile file);
}
