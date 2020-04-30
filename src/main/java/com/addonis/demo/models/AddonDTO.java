package com.addonis.demo.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * AddonDTO is a class used for creating an addon. Represents the information needed to create addon.
 */

@Data
@NoArgsConstructor
public class AddonDTO {

    @NotNull
    @NotBlank
    @Size(min = 2, max = 50, message = "Addon name length should be between 2 and 50 symbols")
    private String name;

    @NotNull
    @NotBlank
    private String description;

    private UserInfo creator;

    @NotNull
    @NotBlank
    private String link;

    @Lob
    private Byte[] addonPicture;

    private MultipartFile file;
}
