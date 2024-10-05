package com.VideoStreamingApp.Entities;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

// for implementing user details
public class CustomUserDetails implements UserDetails {

   private User user; // for accessing fields
    public CustomUserDetails(User user){
        super();
        this.user=user;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    //user name is Email for login
    @Override
    public String getUsername() {
        return user.getEmail();
    }
}
