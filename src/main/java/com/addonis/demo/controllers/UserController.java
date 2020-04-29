package com.addonis.demo.controllers;

import com.addonis.demo.exceptions.DuplicateEntityException;
import com.addonis.demo.exceptions.InvalidDataException;
import com.addonis.demo.models.ChangePassword;
import com.addonis.demo.models.UserChangeDTO;
import com.addonis.demo.models.UserInfo;
import com.addonis.demo.services.contracts.ImageService;
import com.addonis.demo.services.contracts.UserInfoService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;

import static com.addonis.demo.merge.UserMerge.mergeTwoUsers;

/**
 * UserController
 * Page MyAccount. Show users count. Authentication needed - user.
 * Page MyAccountEdit. Edit account - first name, last name, email, picture. Authentication needed.
 */
@Controller
public class UserController {

    private UserInfoService userInfoService;
    private ImageService imageService;
    private UserDetailsManager userdetailsManager;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserInfoService userInfoService, ImageService imageService,
                          UserDetailsManager userdetailsManager, PasswordEncoder passwordEncoder) {
        this.userInfoService = userInfoService;
        this.imageService = imageService;
        this.userdetailsManager = userdetailsManager;
        this.passwordEncoder = passwordEncoder;
    }
    
    @GetMapping("/my-account")
    public String showUserAccount(Model model, Principal principal) {
        model.addAttribute("userinfo", userInfoService.getUserByUsername(principal.getName()));
        return "my-account";
    }

    @GetMapping("/my-account/{id}/userimage")
    public void showUserImage(@PathVariable int id, HttpServletResponse response) throws IOException {

        UserInfo user = userInfoService.getById(id);

        if(user.getProfileImage() != null) {
            byte[] byteArray = new byte[user.getProfileImage().length];
            int i = 0;

            for(Byte wByte : user.getProfileImage()) {
                byteArray[i++] = wByte;
            }

            response.setContentType("image/jpeg");
            InputStream is = new ByteArrayInputStream(byteArray);
            IOUtils.copy(is, response.getOutputStream());
        }
    }

    @GetMapping("/my-account/edit")
    public String editAccountEdit(Model model, Principal principal) {
        UserChangeDTO newuser = new UserChangeDTO();
        UserInfo user = userInfoService.getUserByUsername(principal.getName());
        newuser.setEmail(user.getEmail());
        model.addAttribute("newuser", newuser);
        model.addAttribute("olduser", user);
        return "edit-profile";
    }

    @PostMapping("/my-account/edit")
    public String updateUser(@Valid @ModelAttribute("newuser") UserChangeDTO newuser, Principal principal, BindingResult errors, Model model,
                             @RequestParam("imagefile") MultipartFile file) {

        if(errors.hasErrors()) {
            model.addAttribute("error", errors.getAllErrors().get(0));
            return "my-profile-edit";
        }

        UserInfo oldUser = userInfoService.getUserByUsername(principal.getName());
        mergeTwoUsers(oldUser, newuser);

        if(file.getSize() > 2) {
            try {
                imageService.saveImageFile(oldUser.getId(), file);
            } catch (IllegalStateException | IllegalArgumentException ex) {
                model.addAttribute("error", ex.getMessage());
                return "my-profile-edit";
            }
        }

        try {
            userInfoService.update(oldUser);
        } catch (DuplicateEntityException | InvalidDataException ex) {
            model.addAttribute("error", ex.getMessage());
            return "my-profile-edit";
        }

        return  "redirect:/my-account";
    }

    @GetMapping("/my-account/password-change")
    public String showPasswordChangePage(Model model) {
        model.addAttribute("newpass", new ChangePassword());
        return "password-change";
    }

    @PostMapping("/my-account/password-change")
    public String updateUserPassword(@Valid @ModelAttribute("newpass") ChangePassword newpass, BindingResult errors, Model model) {

        if(errors.hasErrors()) {
            model.addAttribute("errors", errors.getAllErrors().get(0));
            return "password-change";
        }

        if(!newpass.getPassword().equals(newpass.getConfirmPassword()) ) {
            model.addAttribute("error", "Password does not match!");
            return "password-change";
        }

        userdetailsManager.changePassword(passwordEncoder.encode(newpass.getOldPassword()), passwordEncoder.encode(newpass.getPassword()));
        return  "redirect:/my-account";
    }
}
