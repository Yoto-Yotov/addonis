package com.addonis.demo.validation;

import com.addonis.demo.exceptions.DataConflictException;
import com.addonis.demo.exceptions.DuplicateEntityException;
import com.addonis.demo.exceptions.InvalidDataException;
import com.addonis.demo.firstDB.models.UserDTO;
import com.addonis.demo.firstDB.services.contracts.UserInfoService;

import static com.addonis.demo.constants.Constants.EMAIL;
import static com.addonis.demo.constants.Constants.USER_U;
import static com.addonis.demo.validation.EmailValidator.isValidEmailAddress;

/**
 * Validates the user information provided by user creation.
 */
public class UserDtoValidator {

    public static void validateDto(UserDTO userDto, UserInfoService userInfoService) {
        if (userInfoService.checkUserExistByName(userDto.getName())) {
            throw new DuplicateEntityException(USER_U, userDto.getName());
        }
        if (isValidEmailAddress(userDto.getEmail())) {
            throw new InvalidDataException(EMAIL);
        }
        if (userInfoService.checkUserExistByEmail(userDto.getEmail())) {
            throw new DuplicateEntityException(USER_U, EMAIL, userDto.getEmail());
        }
        if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            throw new DataConflictException("Password mismatch");
        }
    }
}
