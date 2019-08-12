package com.weuller.sambatechtest.network.bitmovin;

import com.weuller.sambatechtest.network.bitmovin.models.PostEncodingRequestModel;
import com.weuller.sambatechtest.network.bitmovin.models.PostEncodingResponseModel;
import com.weuller.sambatechtest.services.EncodingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BitmovinApi {

    @Value("bitmovin.api_key")
    private String API_KEY;

    private String INPUT_ID = "";
    private String OUTPUT_ID = "";

    public static final String ENCODING_NAME = "SambaTech Vídeo Encoder";
    public static final String ENCODER_VERSION = "2.22.0";

    public static final String BASE_URL = "https://api.bitmovin.com/v1";
    public static final String ENDPOINT_CREATE_ENCODING = "/encoding/encodings";

    @Autowired
    RestTemplate restTemplate;

    Logger log = LoggerFactory.getLogger(EncodingService.class);

    public BitmovinApi() {}

    public PostEncodingResponseModel createEncoding() {
        PostEncodingRequestModel postBody = new PostEncodingRequestModel(ENCODING_NAME, ENCODER_VERSION);
        HttpHeaders httpHeader = getHeaders();

        HttpEntity<PostEncodingRequestModel> request = new HttpEntity<>(postBody, httpHeader);

        ResponseEntity<PostEncodingResponseModel> response = restTemplate.exchange(BitmovinApi.BASE_URL + BitmovinApi.ENDPOINT_CREATE_ENCODING, HttpMethod.POST, request, PostEncodingResponseModel.class);

        if (response.getStatusCode() == HttpStatus.CREATED && response.getBody().getStatus().equals("SUCCESS")) {
            return response.getBody();
        } else {
            return null;
        }
    }

    private HttpHeaders getHeaders() {
        HttpHeaders httpHeader = new HttpHeaders();
        httpHeader.set("X-Api-Key", API_KEY);

        return httpHeader;
    }
}
