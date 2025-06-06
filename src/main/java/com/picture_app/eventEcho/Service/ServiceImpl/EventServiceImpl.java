package com.picture_app.eventEcho.Service.ServiceImpl;

import com.picture_app.eventEcho.Entity.Event;
import com.picture_app.eventEcho.Entity.Media;
import com.picture_app.eventEcho.Repository.EventRepo;
import com.picture_app.eventEcho.Repository.MediaRepo;
import com.picture_app.eventEcho.Service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
@Service
public class EventServiceImpl implements EventService {
    @Autowired
    private EventRepo eventRepository;
    private MediaRepo mediaRepo;
    @Autowired
    public EventServiceImpl(EventRepo eventRepo, MediaRepo mediaRepo) {
        this.eventRepository = eventRepo;
        this.mediaRepo = mediaRepo;
    }

    @Override
    public Event createEvent(String name, String hostUsername) {
        String accessCode = UUID.randomUUID().toString().substring(0, 6); // Short access code
        Event event = new Event(name, hostUsername, accessCode);
        return eventRepository.save(event);
    }
    @Override
    public List<Event> getEventsByHost(String hostUsername) {
        return eventRepository.findAllByHostUsername(hostUsername);
    }
    @Override
    public void deleteEvent(Long eventId, String hostUsername) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        if (!event.getHostUsername().equals(hostUsername)) {
            throw new RuntimeException("Not authorized to delete this event");
        }

        // Step 1: Delete media records
        List<Media> mediaList = mediaRepo.findAllByAccessCode(event.getAccessCode());
        for (Media media : mediaList) {
            // (Optional) Delete physical file
            try {
                Files.deleteIfExists(Paths.get("uploads", media.getFileName()));
            } catch (IOException e) {
                System.err.println("⚠️ Failed to delete file: " + media.getFileName());
            }
        }

        mediaRepo.deleteAll(mediaList);

        // Step 2: Delete the event
        eventRepository.delete(event);
    }

}
