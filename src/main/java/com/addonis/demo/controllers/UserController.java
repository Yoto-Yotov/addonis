package com.addonis.demo.controllers;

import com.addonis.demo.models.UserChangeDTO;
import com.addonis.demo.models.UserInfo;
import com.addonis.demo.services.contracts.UserInfoService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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

@Controller
public class UserController {

    private UserInfoService userInfoService;

    @Autowired
    public UserController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    //ToDo Get all user addons
    @GetMapping("/my-account")
    public String showUserAccount(Model model, Principal principal) {
        model.addAttribute("userinfo", userInfoService.gerUserByUsername(principal.getName()));
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
        UserChangeDTO userEditDTO = new UserChangeDTO();
        UserInfo user = userInfoService.gerUserByUsername(principal.getName());
        model.addAttribute("olduser", user);
        return "edit-profile";
    }

    @PostMapping("/my-account/edit")
    public String updateUser(@Valid @ModelAttribute("newuser") UserChangeDTO newuser, Principal principal, BindingResult errors, Model model,
                             @RequestParam("imagefile") MultipartFile file, Authentication authentication) {
        if(errors.hasErrors()) {
            model.addAttribute("error", errors.getAllErrors().get(0));
            return "my-profile-edit";
        }

        UserInfo userToUpdate = userInfoService.gerUserByUsername(principal.getName());

//        if(!newuser.getEmail().equalsIgnoreCase(userToUpdate.getEmail())) {
//            if(!userService.checkUserExistsEmail(newuser.getEmail())) {
//                userToUpdate.setEmail(newuser.getEmail());
//            } else {
//                model.addAttribute("error", "User with this email already exists");
//                return "my-profile-edit";
//            }
//        }
//
//        if(file.getSize() > 2) {
//            try {
//                imageService.setUserImageFile(userToUpdate, file);
//            } catch (IllegalStateException | IllegalArgumentException ex) {
//                model.addAttribute("error", ex.getMessage());
//                return "my-profile-edit";
//            }
//        }
//
//        try {
//            userService.updateUser(userToUpdate);
//        } catch (DuplicateEntityException | InvalidDataException ex) {
//            model.addAttribute("error", ex.getMessage());
//            return "my-profile-edit";
//        }

        return  "redirect:/my-account";
    }
}
