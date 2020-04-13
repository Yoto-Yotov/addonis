package com.addonis.demo.controllers;

import com.addonis.demo.models.UserDTO;
import com.addonis.demo.models.UserInfo;
import com.addonis.demo.services.contracts.UserInfoService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class RegistrationController {

    private PasswordEncoder passwordEncoder;
    private UserDetailsManager userDetailsManager;
    private UserInfoService userInfoService;

    public RegistrationController(PasswordEncoder passwordEncoder, UserDetailsManager userDetailsManager, UserInfoService userInfoService) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsManager = userDetailsManager;
        this.userInfoService = userInfoService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userDto", new UserDTO());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute(name = "userDto") UserDTO userDto, Model model,
                               @RequestParam("imagefile") MultipartFile file) {
//        if (UserUtils.validateData(userDto, bindingResult, model, file, userDetailsManager, userService)) {
//            return "register";
//        }
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_USER");
        org.springframework.security.core.userdetails.User newUser =
                new org.springframework.security.core.userdetails.User(
                        userDto.getName(),
                        passwordEncoder.encode(userDto.getPassword()),
                        authorities);

        UserInfo userToCreate = new UserInfo();
        userToCreate.setEmail(userDto.getEmail());
        userToCreate.setName(userDto.getName());

        try {
            userDetailsManager.createUser(newUser);
            userInfoService.create(userToCreate);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        } catch (DuplicateUserException | InvalidDataException e) {
//            model.addAttribute("error", e.getMessage());
//            return "register";
//        }
//        try {
//            imageService.saveUserImageFile(userToCreate.getId(), file);
//        } catch (IllegalStateException ex) {
//            model.addAttribute("error", "Image to Large for upload");
//            return "register";
//        }

        return "register-confirmation";
    }


    @GetMapping("/register-confirmation")
    public String showRegisterConfirmation() {
        return "register-confirmation";
    }
}
