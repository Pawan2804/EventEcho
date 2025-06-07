package com.picture_app.eventEcho.Dto;

import com.picture_app.eventEcho.Entity.Media;

import java.time.LocalDateTime;

public class MediaResponseDto {
    private String fileName;
    private String guestName;
    private String caption;
    private LocalDateTime uploadedAt;
    private String downloadUrl;

    public MediaResponseDto(Media media) {
        this.fileName = media.getFileName();
        this.guestName = media.getGuestName();
        this.caption = media.getCaption();
        this.uploadedAt = media.getUploadedAt();
        this.downloadUrl = "http://localhost:8080/api/media/download/" + media.getFileName();
    }

    public String getFileName() {
        return fileName;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getCaption() {
        return caption;
    }

    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }
}
