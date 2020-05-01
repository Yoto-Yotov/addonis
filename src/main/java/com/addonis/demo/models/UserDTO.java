package com.addonis.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static com.addonis.demo.constants.Constants.*;

/**
 * UserDTO is needed for registering users. Creating user with all needed information.
 */
@Data
@NoArgsConstructor
public class UserDTO {

    @NotNull(message = NAME_NOT_BLANK)
    @NotBlank(message = NAME_NOT_BLANK)
    @Size(min = 2, max = 50, message = "Name length should be between 2 and 50 symbols")
    private String name;

    @NotNull(message = EMAIL_NOT_BLANK)
    @NotBlank(message = EMAIL_NOT_BLANK)
    private String email;

    @NotNull(message = PASS_NOT_BLANK)
    @NotBlank(message = PASS_NOT_BLANK)
    @Size(min = 5, max = 25, message = "Password length should be between 5 and 25 symbols")
    private String password;

    private String confirmPassword;

    private String firstName;

    private String lastName;

    @JsonIgnore
    @Lob
    private Byte[] profileImage;
}
