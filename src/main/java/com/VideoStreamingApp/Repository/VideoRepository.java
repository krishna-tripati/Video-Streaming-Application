package com.VideoStreamingApp.Repository;

import com.VideoStreamingApp.Entities.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VideoRepository extends JpaRepository<Video,String>
{
    //custom method
    Optional<Video> findByTitle(String title);
}
