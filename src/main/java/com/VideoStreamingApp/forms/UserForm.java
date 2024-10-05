package com.VideoStreamingApp.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserForm {


    @NotBlank(message = "Name is required") //form validation
    @Size(min = 3, message = "Minimum 3 Character is required")
    private String name;
    @NotBlank(message = "Email is Required")
    @Email(message = "Invalid  Email address")
    private String email;
    @NotBlank(message = "password is required")
    private String password;

}
