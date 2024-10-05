package com.VideoStreamingApp.forms;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class VideoForm {

    @NotBlank(message = "Title is required") //form validation
    private String title;
    private String description;
    private String contentType;
    private String filePath;
}
