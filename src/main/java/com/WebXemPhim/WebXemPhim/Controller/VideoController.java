package com.WebXemPhim.WebXemPhim.Controller;
import com.WebXemPhim.WebXemPhim.Service.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@CrossOrigin
public class VideoController {

    @Autowired
    private CloudinaryService cloudinaryService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadVideo(@RequestPart("file") MultipartFile file) {
        try {
            String videoUrl = cloudinaryService.uploadVideo(file);
            return ResponseEntity.status(HttpStatus.OK).body(videoUrl);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Upload failed");
        }
    }
}
