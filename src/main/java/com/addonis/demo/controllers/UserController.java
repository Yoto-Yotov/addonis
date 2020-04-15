package com.addonis.demo.controllers;

import com.addonis.demo.models.UserInfo;
import com.addonis.demo.services.contracts.UserInfoService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
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

}
