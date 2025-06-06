package com.picture_app.eventEcho.Repository;

import com.picture_app.eventEcho.Entity.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MediaRepo extends JpaRepository<Media,Long> {
    List<Media> findByAccessCode(String accessCode);
    List<Media> findAllByAccessCode(String accessCode);

}
