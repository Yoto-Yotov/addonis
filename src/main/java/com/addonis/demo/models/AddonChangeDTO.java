package com.addonis.demo.models;

import lombok.Data;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Lob;

@Component
@Data
public class AddonChangeDTO {

    private String name;

    private String description;

    @Lob
    private Byte[] addonPicture;

    private MultipartFile file;
}
