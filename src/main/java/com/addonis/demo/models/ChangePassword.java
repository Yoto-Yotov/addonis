package com.addonis.demo.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * ChangePassword is a class used for updating users passwords.
 */
@Data
@NoArgsConstructor
@Component
public class ChangePassword {

    @NotBlank
    @Size(min = 5, max = 25, message = "Password size should be between 5 and 25 symbols")
    private String password;

    @NotBlank
    @Size(min = 5, max = 25, message = "Password size should be between 5 and 25 symbols")
    private String confirmPassword;

    private String oldPassword;
}
