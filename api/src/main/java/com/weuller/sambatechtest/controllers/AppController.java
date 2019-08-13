package com.weuller.sambatechtest.controllers;

import com.weuller.sambatechtest.services.EncodingService;
import com.weuller.sambatechtest.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class AppController {

    @Autowired
    EncodingService encodingService;

    @Autowired
    StorageService storageService;

    public AppController(EncodingService encodingService) {
        this.encodingService = encodingService;
    }

    @PostMapping("/encode")
    public ResponseEntity<String> encode(@RequestParam MultipartFile inputFile) {

        String folder = "test";
        String audioCodecId = "8161a341-2f1f-4a91-a454-a2a64d6246de";
        String videoCodecId = "3770cc0a-de10-4387-9891-db0c65a23344";

        String inputFileName = storageService.upload(inputFile);
        if (!inputFileName.isEmpty()) {
            String encodingId = encodingService.encode(inputFileName, folder, audioCodecId, videoCodecId);
            if (!encodingId.isEmpty()) {
                return new ResponseEntity<>("Link", HttpStatus.CREATED);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
