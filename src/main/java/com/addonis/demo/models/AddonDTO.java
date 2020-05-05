package com.addonis.demo.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Lob;
import javax.validation.constraints.*;

import static com.addonis.demo.constants.Constants.*;

/**
 * AddonDTO is a class used for creating an addon. Represents the information needed to create addon.
 */

@Data
@NoArgsConstructor
public class AddonDTO {

    @NotNull(message = NAME_NOT_BLANK)
    @NotBlank(message = NAME_NOT_BLANK)
    @Size(min = 2, max = 50, message = "Addon name length should be between 2 and 50 symbols")
    private String name;

    @NotNull(message = DESCRIPTION_NOT_BLANK)
    @NotBlank(message = DESCRIPTION_NOT_BLANK)
    private String description;

    private UserInfo creator;

    @NotNull(message = LINK_NOT_BLANK)
    @NotBlank(message = LINK_NOT_BLANK)
    @Pattern(regexp = "https:[/][/]github.com[/][a-zA-Z0-9/-]+[/][a-zA-Z0-9/-]+", message = "Invalid Link")
    private String link;

    @Lob
    private Byte[] addonPicture;

    private MultipartFile file;

    private String addonIde;
}
