package com.picture_app.eventEcho.Service;

import com.picture_app.eventEcho.Entity.Event;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EventService {
    Event createEvent(String name, String hostUsername);
    List<Event> getEventsByHost(String hostUsername);
    void deleteEvent(Long eventId, String hostUsername);

}
