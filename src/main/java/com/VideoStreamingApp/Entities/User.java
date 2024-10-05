package com.VideoStreamingApp.Entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int uId;
    private String name;
    private String email;
    private String password;

    // methods for storing in database //also use lombk for stter

    public String setName(String name) {
        return this.name=name;
    }

    public String setEmail(String email) {
        return this.email=email;
    }

    public String setPassword(String password) {
        return this.password=password;
    }



}
