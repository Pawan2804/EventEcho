package com.picture_app.eventEcho.Controller;

import com.picture_app.eventEcho.Entity.Event;
import com.picture_app.eventEcho.Security.CustomerUserDetails;
import com.picture_app.eventEcho.Service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {
    @GetMapping("/secure-test")
    public String onlyForLoggedInUsers() {
        return "✅ You are authenticated!";
    }

    @Autowired
    private EventService eventService;

    @PostMapping("/create")
    public ResponseEntity<Event> createEvent(@RequestParam String name, Authentication authentication) {
        String hostUsername = authentication.getName();  // from JWT token
        Event newEvent = eventService.createEvent(name, hostUsername);
        return ResponseEntity.ok(newEvent);
    }
    @GetMapping("/my")
    public ResponseEntity<?> getMyEvents(@AuthenticationPrincipal CustomerUserDetails userDetails) {
        String username = userDetails.getUsername();
        List<Event> events = eventService.getEventsByHost(username);
        return ResponseEntity.ok(events);
    }
    @DeleteMapping("/{eventId}")
    public ResponseEntity<?> deleteEvent(
            @PathVariable Long eventId,
            @AuthenticationPrincipal CustomerUserDetails userDetails) {
        try {
            eventService.deleteEvent(eventId, userDetails.getUsername());
            return ResponseEntity.ok("Event deleted");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

}
