package com.VideoStreamingApp.Repository;

//doing database related action

import com.VideoStreamingApp.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
    User findByEmail(String email);

}
