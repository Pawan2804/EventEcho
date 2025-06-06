package com.picture_app.eventEcho.Service.ServiceImpl;

import com.picture_app.eventEcho.Entity.Media;
import com.picture_app.eventEcho.Repository.MediaRepo;
import com.picture_app.eventEcho.Service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
@Service
public class MediaServiceImpl implements MediaService {
    private static final String UPLOAD_DIR = "uploads";

    @Autowired
    private MediaRepo mediaRepository;

    @Override
    public Media uploadMedia(String accessCode, MultipartFile file) throws IOException {
        // Create uploads folder if it doesn't exist
        Files.createDirectories(Paths.get(UPLOAD_DIR));

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(UPLOAD_DIR, fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        Media media = new Media(fileName, accessCode);
        return mediaRepository.save(media);
    }
    @Override
    public List<Media> getMediaByAccessCode(String accessCode) {
        return mediaRepository.findAllByAccessCode(accessCode);
    }
}
