package com.addonis.demo.firstDB.models;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * UserChangeDTO is a class used for editing users information.
 */
@Component
@Data
public class UserChangeDTO {

    private String email;

    @Size(min = 5, max = 25, message = "Password size should be between 5 and 25 symbols")
    private String password;

    private String firstName;

    private String lastName;

    @Lob
    private Byte[] picture;

}
