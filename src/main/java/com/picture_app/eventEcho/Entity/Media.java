package com.picture_app.eventEcho.Entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "media")
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    private String accessCode;

    @CreationTimestamp
    private LocalDateTime uploadedAt = LocalDateTime.now();

    private String guestName;
    private String caption;


    public Media() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public Media(Long id, String fileName, String accessCode, LocalDateTime uploadedAt, String guestName, String caption) {
        this.id = id;
        this.fileName = fileName;
        this.accessCode = accessCode;
        this.uploadedAt = uploadedAt;
        this.guestName = guestName;
        this.caption = caption;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }

    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    public Media(String fileName, String accessCode) {
        this.fileName = fileName;
        this.accessCode = accessCode;
        this.uploadedAt = LocalDateTime.now();
    }

}
