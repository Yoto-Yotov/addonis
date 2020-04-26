package com.addonis.demo.utils;

import com.addonis.demo.exceptions.DataConflictException;
import com.addonis.demo.exceptions.DuplicateEntityException;
import com.addonis.demo.exceptions.InvalidDataException;
import com.addonis.demo.models.UserDTO;
import com.addonis.demo.services.contracts.UserInfoService;
import com.addonis.demo.services.contracts.UserService;

import static com.addonis.demo.utils.UserUtils.isValidEmailAddress;

public class UserDtoValidator {

    public static void validateDto(UserDTO userDto, UserInfoService userInfoService) {
        if (userInfoService.checkUserExistByName(userDto.getName())) {
            throw new DuplicateEntityException("User", userDto.getName());
        }
        if (isValidEmailAddress(userDto.getEmail())) {
            throw new InvalidDataException("email");
        }
        if (userInfoService.checkUserExistByEmail(userDto.getEmail())) {
            throw new DuplicateEntityException("User", "email", userDto.getEmail());
        }
        if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            throw new DataConflictException("Password mismatch");
        }
    }
}
