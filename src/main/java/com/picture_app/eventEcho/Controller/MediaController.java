package com.picture_app.eventEcho.Controller;

import com.picture_app.eventEcho.Entity.Media;
import com.picture_app.eventEcho.Service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/media")
public class MediaController {
    @Autowired
    private MediaService mediaService;

    // 🔄 1. Upload (for guests)
    @PostMapping("/{accessCode}/upload")
    public ResponseEntity<?> uploadFile(
            @PathVariable String accessCode,
            @RequestParam("file") MultipartFile file) {
        try {
            Media saved = mediaService.uploadMedia(accessCode, file);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Upload failed: " + e.getMessage());
        }
    }

    // 👀 2. View all media for a specific event (for host)
    @GetMapping("/{accessCode}")
    public ResponseEntity<?> getMediaForEvent(@PathVariable String accessCode) {
        List<Media> mediaList = mediaService.getMediaByAccessCode(accessCode);
        return ResponseEntity.ok(mediaList);
    }
}
