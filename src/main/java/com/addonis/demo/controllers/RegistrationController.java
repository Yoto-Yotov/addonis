package com.addonis.demo.controllers;

import com.addonis.demo.exceptions.DuplicateEntityException;
import com.addonis.demo.models.Authorities;
import com.addonis.demo.models.User;
import com.addonis.demo.models.UserDTO;
import com.addonis.demo.models.UserInfo;
import com.addonis.demo.services.contracts.AuthorityService;
import com.addonis.demo.services.contracts.ImageService;
import com.addonis.demo.services.contracts.UserInfoService;
import com.addonis.demo.services.contracts.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import static com.addonis.demo.utils.UserUtils.mergeUserInfo;

@Controller
public class RegistrationController {

    private PasswordEncoder passwordEncoder;
    private UserInfoService userInfoService;
    private ImageService imageService;
    private UserService userService;
    private AuthorityService authorityService;

    public RegistrationController(PasswordEncoder passwordEncoder, UserInfoService userInfoService,
                                  ImageService imageService, UserService userService, AuthorityService authorityService) {
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
                               @RequestParam("imagefile") MultipartFile file) {

        User user = new User();
        user.setUsername(userDto.getName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        UserInfo userInfo = mergeUserInfo(userDto);

        Authorities authority = new Authorities();
        authority.setUsername(userDto.getName());
        authority.setAuthority("ROLE_USER");
        authorityService.create(authority);

        try {
            userService.create(user);
            userInfoService.create(userInfo);
        } catch (DuplicateEntityException e) {
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
