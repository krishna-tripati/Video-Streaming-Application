package com.VideoStreamingApp.Controller;

import com.VideoStreamingApp.Entities.User;
import com.VideoStreamingApp.Repository.UserRepo;
import com.VideoStreamingApp.helper.LoggedInUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepo userRepo;

    private Logger logger= LoggerFactory.getLogger(UserController.class);



    @GetMapping("/user_profile")
    public String profile(Model model, Principal principal){
        String username = LoggedInUser.getLoggedInUser(principal);
        logger.info("User logged in: {}",username);

      //fetching data from database: get user from db: email,name,address
        User user=userRepo.findByEmail(username);
        System.out.println(user.getName());

        model.addAttribute("loggedInUser",user);

        return "user/user_profile";
    }

}
