package com.addonis.demo.services.contracts;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    void saveAddonFile(int addonId, MultipartFile file);
    Byte[] getFile(int addonId);

}
