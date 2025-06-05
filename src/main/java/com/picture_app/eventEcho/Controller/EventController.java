package com.picture_app.eventEcho.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/events")
public class EventController {
    @GetMapping("/secure-test")
    public String onlyForLoggedInUsers() {
        return "✅ You are authenticated!";
    }
}
