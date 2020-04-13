package com.addonis.demo.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class UserDTO {

    @NotNull
    @NotBlank
    @Size(min = 2, max = 50, message = "Name size should be between 2 and 50 symbols")
    private String name;

    @NotNull
    @NotBlank
    private String email;

    @NotNull
    @NotBlank
    @Size(min = 5, max = 25, message = "Password size should be between 5 and 25 symbols")
    private String password;

    private String confirmPassword;

    private String oldPassword;

    @Lob
    private Byte[] profileImage;
}
