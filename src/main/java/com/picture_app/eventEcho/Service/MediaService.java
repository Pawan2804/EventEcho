package com.picture_app.eventEcho.Service;

import com.picture_app.eventEcho.Entity.Media;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MediaService {
    Media uploadMedia(String accessCode, MultipartFile file) throws IOException;
    List<Media> getMediaByAccessCode(String accessCode);

}
