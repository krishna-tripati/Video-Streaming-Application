package com.VideoStreamingApp.Security;

import com.VideoStreamingApp.Services.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// for spring security
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    //making password encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //configuration of authentication provider for spring security
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        //take a user service object
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        //take a password encoder object
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.authorizeHttpRequests(authorize -> {
           //   authorize.requestMatchers("/home","/register","/about").permitAll(); // public page

            //configuration for which page is private or public
            authorize.requestMatchers("/user/**").authenticated();  // private only /user/**
            authorize.anyRequest().permitAll();  // other page are public
        });


        //         //any changes to related login to reach here //failureUrl
         httpSecurity.formLogin(formLogin->{
          formLogin.loginPage("/login");
          formLogin.loginProcessingUrl("/authenticate"); //authenticate same name put on login post section// login page processing are apply in authenticated pages
          formLogin.defaultSuccessUrl("/user/user_profile"); // when login are successful then redirect in user-dashboard
          formLogin.failureUrl("/login?error=true"); //when login are failure
          formLogin.usernameParameter("email"); // email same name put on login page ->email field -> name="email"
          formLogin.passwordParameter("password"); // password same name put on login page ->password field -> name="password"

         });

        //logout form
        httpSecurity.csrf(AbstractHttpConfigurer::disable); //disable the csrf
        httpSecurity.logout(logoutform->{
            logoutform.logoutUrl("/do-logout");
            logoutform.logoutSuccessUrl("/login?logout=true");
        });

      //  httpSecurity.formLogin(Customizer.withDefaults());//default form

        return httpSecurity.build();
    }

}