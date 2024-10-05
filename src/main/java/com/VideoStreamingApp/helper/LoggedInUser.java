package com.VideoStreamingApp.helper;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;

// fetching data from logged user
// accessing data to save the profile page
public class LoggedInUser {

    public static String getLoggedInUser(Principal principal) {
        // Get the authentication object
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Check if the authentication contains user details
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            // Retrieve user details (such as username or email)
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();  // This is typically the email or username
            return username;
        }

        return null;
    }
}
