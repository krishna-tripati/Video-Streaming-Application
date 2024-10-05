package com.VideoStreamingApp.Controller;
import com.VideoStreamingApp.Entities.Video;
import com.VideoStreamingApp.Playload.CustomMessage;
import com.VideoStreamingApp.Services.VideoService;
import com.VideoStreamingApp.forms.VideoForm;
import com.VideoStreamingApp.helper.Message;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static org.hibernate.boot.model.process.spi.MetadataBuildingProcess.build;


@Controller
@RequestMapping(value = "/user")
public class VideoController {
    private VideoService videoService;
    public VideoController(VideoService videoService){
       this.videoService=videoService;
    }

    //upload videos page
    @GetMapping("/upload-video")
    public String videoUpload(Model model){
        Video v=new Video();
        model.addAttribute("video", v);

        return "user/upload_video";
    }


    @PostMapping("/upload-video")
    public String videoUploadProcess(
            @RequestParam("file")MultipartFile file,
            @RequestParam("title")String title,
            @RequestParam("description") String description,
         HttpSession session
    ){

        Video video=new Video();
        video.setTitle(title);
        video.setDescription(description);
        video.setVideoId(UUID.randomUUID().toString());

        Video savedVideo=videoService.save(video,file);
        if(savedVideo!=null){

            Message message = Message.builder().content( "Video uploaded successfully!").build();
            session.setAttribute("message",message);
            //return ResponseEntity.status(HttpStatus.OK).body(video);
            return "redirect:/user/upload_video";
        }else{

            Message message = Message.builder().content("Video could not be uploaded. Please try again.").build();
            session.setAttribute("message",message);
            return "redirect:/user/upload_video";
        }
    }

}
