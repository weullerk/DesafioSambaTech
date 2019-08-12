package com.weuller.sambatechtest.controllers;

import com.bitmovin.api.BitmovinApi;
import com.bitmovin.api.encoding.inputs.AzureInput;
import com.bitmovin.api.encoding.outputs.AzureOutput;
import com.bitmovin.api.exceptions.BitmovinApiException;
import com.bitmovin.api.http.RestException;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.weuller.sambatechtest.services.EncodingService;

import java.io.IOException;
import java.net.URISyntaxException;

public class AppController {

    EncodingService encodingService;

    public AppController(EncodingService _encodingService) {
        encodingService = _encodingService;
    }

    public void start() {
        try {



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
