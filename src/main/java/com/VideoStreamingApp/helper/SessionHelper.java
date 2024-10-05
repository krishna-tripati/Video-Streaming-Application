package com.VideoStreamingApp.helper;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

//remove message when registration page refresh

@Component
public class SessionHelper {

    public static void removeMessage(){
        try{
            System.out.println("removing message from session");
            HttpSession session= ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getSession();
            session.removeAttribute("message");

        }catch (Exception e){
            System.out.println("Eerror in Session Helper:"+e);
            e.printStackTrace();
        }
    }
}
