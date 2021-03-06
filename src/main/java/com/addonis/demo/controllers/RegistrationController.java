package com.addonis.demo.controllers;

import com.addonis.demo.exceptions.DataConflictException;
import com.addonis.demo.exceptions.DuplicateEntityException;
import com.addonis.demo.exceptions.InvalidDataException;
import com.addonis.demo.firstDB.models.Authorities;
import com.addonis.demo.firstDB.models.User;
import com.addonis.demo.firstDB.models.UserDTO;
import com.addonis.demo.firstDB.models.UserInfo;
import com.addonis.demo.firstDB.services.contracts.AuthorityService;
import com.addonis.demo.firstDB.services.contracts.ImageService;
import com.addonis.demo.firstDB.services.contracts.UserInfoService;
import com.addonis.demo.firstDB.services.contracts.UserService;
import com.addonis.demo.validation.UserDtoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.Valid;

import static com.addonis.demo.constants.Constants.ROLE_USER;
import static com.addonis.demo.merge.UserMerge.mergeUserInfo;


/**
 * RegistrationController - register user and set main information + picture
 */
@Controller
public class RegistrationController {

    private PasswordEncoder passwordEncoder;
    private UserInfoService userInfoService;
    private ImageService imageService;
    private UserService userService;
    private AuthorityService authorityService;

    @Autowired
    public RegistrationController(PasswordEncoder passwordEncoder,
                                  UserInfoService userInfoService, ImageService imageService, UserService userService, AuthorityService authorityService) {
        this.passwordEncoder = passwordEncoder;
        this.userInfoService = userInfoService;
        this.imageService = imageService;
        this.userService = userService;
        this.authorityService = authorityService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userDto", new UserDTO());
        return "register";
    }

    //ToDo Check Validations
    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute(name = "userDto") UserDTO userDto, Model model,
                               BindingResult bindingResult,
                               @RequestParam("imagefile") MultipartFile file) {

        try {
            UserDtoValidator.validateDto(userDto, userInfoService);
        } catch (DuplicateEntityException | DataConflictException | InvalidDataException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
        User user = new User();
        user.setUsername(userDto.getName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        UserInfo userInfo = mergeUserInfo(userDto);

        Authorities authority = new Authorities();
        authority.setUsername(userDto.getName());
        authority.setAuthority(ROLE_USER);

        try {
            userService.create(user);
            authorityService.create(authority);
            userInfoService.create(userInfo);
        } catch (DuplicateEntityException | InvalidDataException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }

        try {
            imageService.saveImageFile(userInfo.getId(), file);
        } catch (IllegalStateException ex) {
            model.addAttribute("error", "Image is too large! Please try again!");
            return "register";
        }
        return "registration-confirmation";
    }


    @GetMapping("/registration-confirmation")
    public String showRegisterConfirmation() {
        return "registration-confirmation";
    }
}
