package com.picture_app.eventEcho.Repository;

import com.picture_app.eventEcho.Entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepo extends JpaRepository<Event,Long> {
    Event findByAccessCode(String accessCode);
    List<Event> findAllByHostUsername(String hostUsername);

}
