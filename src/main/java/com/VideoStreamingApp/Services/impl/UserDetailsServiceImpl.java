package com.VideoStreamingApp.Services.impl;

import com.VideoStreamingApp.Entities.CustomUserDetails;
import com.VideoStreamingApp.Entities.User;
import com.VideoStreamingApp.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// userDetails implementing file
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepo userRepo; // for accessing user repo // mainly access findbyEmail
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //fetching user from database

        User user = userRepo.findByEmail(username); //username accessing on the CustomeUserDetails class

        if(user == null){
            throw new UsernameNotFoundException("Could not found user"); //exception when user is not found
        }

        CustomUserDetails customUserDetails=new CustomUserDetails(user); // user is on the CustomUserDetails constructur
        return customUserDetails;
    }
}
