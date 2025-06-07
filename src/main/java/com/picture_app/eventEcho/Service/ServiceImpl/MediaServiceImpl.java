package com.picture_app.eventEcho.Service.ServiceImpl;

import com.picture_app.eventEcho.Entity.Media;
import com.picture_app.eventEcho.Repository.MediaRepo;
import com.picture_app.eventEcho.Service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class MediaServiceImpl implements MediaService {
    private static final String UPLOAD_DIR = "uploads";

    @Autowired
    private MediaRepo mediaRepository;

    @Override
    public List<Media> uploadMultiple(String accessCode, MultipartFile[] files, String[] guestNames, String[] captions) throws IOException {
        List<Media> savedList = new ArrayList<>();
        Files.createDirectories(Paths.get(UPLOAD_DIR));

        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];
            if (file.isEmpty()) continue;

            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR, fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            String guestName = (guestNames.length > i) ? guestNames[i] : "";
            String caption = (captions.length > i) ? captions[i] : "";

            Media media = new Media(fileName, accessCode);
            media.setGuestName(guestName);
            media.setCaption(caption);

            savedList.add(mediaRepository.save(media));
        }

        return savedList;
    }

    @Override
    public List<Media> getMediaByAccessCode(String accessCode) {
        return mediaRepository.findAllByAccessCode(accessCode);
    }
    @Override
    public File createZipForEvent(String accessCode) throws IOException {
        List<Media> mediaList = mediaRepository.findAllByAccessCode(accessCode);

        if (mediaList.isEmpty()) {
            throw new IOException("No media found for this access code");
        }

        Path zipPath = Paths.get("uploads", accessCode + "_media.zip");

        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipPath.toFile()))) {
            for (Media media : mediaList) {
                Path filePath = Paths.get("uploads", media.getFileName());
                if (!Files.exists(filePath)) continue;

                zos.putNextEntry(new ZipEntry(media.getFileName()));
                Files.copy(filePath, zos);
                zos.closeEntry();
            }
        }

        return zipPath.toFile();
    }

}
