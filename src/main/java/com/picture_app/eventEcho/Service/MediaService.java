package com.picture_app.eventEcho.Service;

import com.picture_app.eventEcho.Entity.Media;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface MediaService {
    List<Media> uploadMultiple(String accessCode, MultipartFile[] files, String[] guestNames, String[] captions) throws IOException;
    List<Media> getMediaByAccessCode(String accessCode);
    File createZipForEvent(String accessCode) throws IOException;

}
