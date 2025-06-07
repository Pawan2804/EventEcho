package com.picture_app.eventEcho.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String hostUsername;

    private String accessCode;

    private LocalDateTime createdAt = LocalDateTime.now();
    @Column
    private String photographerUsername;

    public Event() {}

    public Event(String name, String hostUsername, String accessCode) {
        this.name = name;
        this.hostUsername = hostUsername;
        this.accessCode = accessCode;
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getHostUsername() { return hostUsername; }
    public void setHostUsername(String hostUsername) { this.hostUsername = hostUsername; }

    public String getAccessCode() { return accessCode; }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }


    public void setAccessCode(String accessCode) { this.accessCode = accessCode; }

    public LocalDateTime getCreatedAt() { return createdAt; }
}
