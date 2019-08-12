package com.weuller.sambatechtest.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;



@Service
public class EncodingService {

    @Value("bitmovin.input_id")
    private String INPUT_ID = "";

    @Value("bitmovin.output_id")
    private String OUTPUT_ID = "";

    Logger log = LoggerFactory.getLogger(EncodingService.class);

    public EncodingService() {

    }

    public void encode() {

    }
}
