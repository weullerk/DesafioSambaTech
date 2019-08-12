package com.weuller.sambatechtest.controllers;

import com.weuller.sambatechtest.services.EncodingService;

public class AppController {

    EncodingService encodingService;

    public AppController(EncodingService encodingService) {
        this.encodingService = encodingService;
    }

    public void start() {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
