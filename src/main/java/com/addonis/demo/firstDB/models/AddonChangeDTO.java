package com.addonis.demo.firstDB.models;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Lob;

/**
 * AddonChangeDTO is a class used for addon update. Represents the needed for update information.
 */
@Component
@Data
public class AddonChangeDTO {

    private String name;

    private String description;

    @Lob
    private Byte[] addonPicture;

    private MultipartFile file;
}
