package com.picture_app.eventEcho.Controller;

import com.picture_app.eventEcho.Dto.MediaResponseDto;
import com.picture_app.eventEcho.Entity.Media;
import com.picture_app.eventEcho.Service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/media")
public class MediaController {
    @Autowired
    private MediaService mediaService;

    // 🔄 1. Upload (for guests)
    @PostMapping("/{accessCode}/upload")
    public ResponseEntity<?> uploadFilesWithMeta(
            @PathVariable String accessCode,
            @RequestParam("files") MultipartFile[] files,
            @RequestParam("guestNames") String[] guestNames,
            @RequestParam("captions") String[] captions) {
        try {
            List<Media> uploaded = mediaService.uploadMultiple(accessCode, files, guestNames, captions);
            return ResponseEntity.ok(uploaded);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Upload failed: " + e.getMessage());
        }
    }



    // 👀 2. View all media for a specific event (for host)
    @GetMapping("/{accessCode}")
    public ResponseEntity<?> getMediaForEvent(@PathVariable String accessCode) {
        List<Media> mediaList = mediaService.getMediaByAccessCode(accessCode);
        List<MediaResponseDto> response = mediaList.stream()
                .map(MediaResponseDto::new)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{accessCode}/download")
    public ResponseEntity<?> downloadZip(@PathVariable String accessCode) {
        try {
            File zipFile = mediaService.createZipForEvent(accessCode);

            InputStreamResource resource = new InputStreamResource(new FileInputStream(zipFile));

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + zipFile.getName())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .contentLength(zipFile.length())
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating ZIP: " + e.getMessage());
        }
    }

    @GetMapping("/{accessCode}/photographer-view")
    @PreAuthorize("hasAuthority('PHOTOGRAPHER') or hasAuthority('HOST')")
    public ResponseEntity<?> photographerView(@PathVariable String accessCode) {
        List<Media> mediaList = mediaService.getMediaByAccessCode(accessCode);
        List<MediaResponseDto> response = mediaList.stream()
                .map(MediaResponseDto::new)
                .toList();
        return ResponseEntity.ok(response);
    }
    @GetMapping("/download/{fileName:.+}")
    @PreAuthorize("hasAnyAuthority('HOST', 'PHOTOGRAPHER')")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) throws IOException {
        Path path = Paths.get("uploads").resolve(fileName).normalize();
        Resource resource = new UrlResource(path.toUri());

        if (!resource.exists()) {
            throw new FileNotFoundException("File not found: " + fileName);
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }


}
