package com.VideoStreamingApp.Controller;

import com.VideoStreamingApp.Entities.User;
import com.VideoStreamingApp.Entities.Video;
import com.VideoStreamingApp.Repository.UserRepo;

import com.VideoStreamingApp.Services.VideoService;
import com.VideoStreamingApp.forms.UserForm;
import com.VideoStreamingApp.helper.Message;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


@Controller
public class HomeController {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private VideoService videoService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String homeV(){
        return "home";
    }


   @GetMapping("/home")
    public String home(){
        return "home";
    }

    @GetMapping("/login")
    public String login(){
       return "login";
    }


//    @GetMapping("/about")
//    public String about(){
//       return "about";
//    }

    @GetMapping("/register")
    public String register(Model model){
        UserForm userForm=new UserForm();
        model.addAttribute("userForm",userForm); // sending user form data to the registration form
        return "register";
    }

    //processing register
    @PostMapping("/do-register")                         //HttpSession for message handling
    public String processRegister(@Valid  @ModelAttribute UserForm userForm, BindingResult rBindingResult, HttpSession session){ // accessing userform field
        System.out.println("processing registration");
        //fetch form data
           //UserForm
        System.out.println(userForm); // user form data print

        //validation from data
          if(rBindingResult .hasErrors()){ //if error is occur goes to the register page also take errors
              return "register";
          }

        //save to database
           //userService
               //userform-->User
          User user=new User();
          user.setName(userForm.getName()); // accessing userform data and store the users database
          user.setEmail(userForm.getEmail());
          user.setPassword(userForm.getPassword()); //save userform password to the user database
           user.setPassword(passwordEncoder.encode(user.getPassword()));//when password save to database then they encoded and save it

        User savedUser= userRepo.save(user);

        System.out.println("user save");

        //message: "Registration Successful"
          //add the message
      Message message = Message.builder().content("Registration Successfully").build();
           session.setAttribute("message",message);

         //redirect to registration form
        return "redirect:/register";

    }

    // show videos page
    @GetMapping("/videos")
    public String getAll(Model model){
      List<Video> videos= videoService.getAll();
      model.addAttribute("videos", videos);
        return "videos";
    }

    // stream video--  by video id
    //
    @GetMapping("/video/stream/{id}")
    public ResponseEntity<Resource> videoStream(@PathVariable("id") String id,
      @RequestHeader(value = "Range",required = false) String range)
    {

            Video video  =videoService.get(id);
            String contentType= video.getContentType();
          //  String filePath= video.getFilePath();

        Path path=Paths.get(video.getFilePath());

            FileSystemResource resource=new FileSystemResource(path);

            if(contentType==null){
                contentType="application/octet-stream";
            }

            //  length of file
        long filelength=path.toFile().length();
        if(range==null){
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);
        }
        //parse the Range header
        long rangeStart;
        long rangeEnd;

        String[] ranges=range.replace("bytes=","").split("-");
        rangeStart=Long.parseLong(ranges[0]);

        // Handle cases where the end of the range is not specified
        if (ranges.length > 1 && !ranges[1].isEmpty()) {
            rangeEnd = Long.parseLong(ranges[1]);
        } else {
            rangeEnd = filelength - 1; // Default to the end of the file
        }

        // Ensure the end range is within file size limits
        if (rangeStart >= filelength || rangeEnd >= filelength) {
            // Range not satisfiable
            return ResponseEntity.status(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE)
                    .header("Content-Range", "bytes */" + filelength) // Required in 416 responses
                    .build();
        }

        System.out.println("range start" +rangeStart);
        System.out.println("range end" +rangeEnd);

        // Open the file stream and skip to the start of the range
        InputStream inputStream;
        try {
            inputStream = Files.newInputStream(path);
            inputStream.skip(rangeStart);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        // Calculate the content length of the range
        long contentLength = rangeEnd - rangeStart + 1;

        // Set up the headers for partial content
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Range", "bytes " + rangeStart + "-" + rangeEnd + "/" + filelength);
        httpHeaders.add("Cache-Control", "no-cache, no-store, must-revalidate");
        httpHeaders.add("Pragma", "no-cache");
        httpHeaders.add("Expires", "0");
        httpHeaders.add("X-Content-Type-Options", "nosniff");

        // Set the content length
        httpHeaders.setContentLength(contentLength);

        // Return the partial content response
        return ResponseEntity
                .status(HttpStatus.PARTIAL_CONTENT)
                .headers(httpHeaders)
                .contentType(MediaType.parseMediaType(contentType))
                .body(new InputStreamResource(inputStream));
    }

}










