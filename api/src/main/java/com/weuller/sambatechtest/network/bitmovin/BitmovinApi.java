package com.weuller.sambatechtest.network.bitmovin;

import com.weuller.sambatechtest.network.bitmovin.models.encoding.PostEncodingRequestModel;
import com.weuller.sambatechtest.network.bitmovin.models.encoding.PostEncodingResponseModel;
import com.weuller.sambatechtest.network.bitmovin.models.streams.PostStreamsRequestModel;
import com.weuller.sambatechtest.network.bitmovin.models.streams.PostStreamsResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Service
public class BitmovinApi {

    @Value("bitmovin.api_key")
    private String API_KEY;

    private String INPUT_ID = "";
    private String OUTPUT_ID = "";

    public static final String RESPONSE_STATUS_SUCCESS = "SUCCESS";
    public static final String RESPONSE_STATUS_ERROR = "ERROR";

    public static final String ENCODING_NAME = "SambaTech Vídeo Encoder";
    public static final String ENCODER_VERSION = "2.22.0";

    public static final String BASE_URL = "https://api.bitmovin.com/v1";
    public static final String ENDPOINT_CREATE_ENCODING = "/encoding/encodings";
    public static final String ENDPOINT_CREATE_STREAMS = "/encoding/encodings/%s/streams";

    @Autowired
    RestTemplate restTemplate;

    Logger log = LoggerFactory.getLogger(BitmovinApi.class);

    public BitmovinApi() {}

    public PostEncodingResponseModel createEncoding() {
        PostEncodingRequestModel body = new PostEncodingRequestModel(ENCODING_NAME, ENCODER_VERSION);
        HttpHeaders header = getHeaders();

        HttpEntity<PostEncodingRequestModel> request = new HttpEntity<>(body, header);

        ResponseEntity<PostEncodingResponseModel> response = restTemplate.exchange(BitmovinApi.BASE_URL + BitmovinApi.ENDPOINT_CREATE_ENCODING, HttpMethod.POST, request, PostEncodingResponseModel.class);

        if (response.getStatusCode() == HttpStatus.CREATED && response.getBody().getStatus().equals(RESPONSE_STATUS_SUCCESS)) {
            return response.getBody();
        } else {
            return null;
        }
    }

    public PostStreamsResponseModel createStreams(String encodingId, String inputId, String inputPath, String codecConfigId) {
        PostStreamsRequestModel.StreamInput streamInput = new PostStreamsRequestModel.StreamInput(inputId, inputPath, "AUTO");

        ArrayList<PostStreamsRequestModel.StreamInput> streamInputArrayList = new ArrayList<>();
        streamInputArrayList.add(streamInput);

        PostStreamsRequestModel body = new PostStreamsRequestModel(codecConfigId, streamInputArrayList);
        HttpHeaders header = getHeaders();

        String url = String.format(BitmovinApi.BASE_URL + BitmovinApi.ENDPOINT_CREATE_STREAMS, encodingId);

        HttpEntity<PostStreamsRequestModel> request = new HttpEntity<>(body, header);


        ResponseEntity<PostStreamsResponseModel> response = restTemplate.exchange(url, HttpMethod.POST, request, PostStreamsResponseModel.class);

        if (response.getStatusCode() == HttpStatus.CREATED && response.getBody().getStatus().equals(RESPONSE_STATUS_SUCCESS)) {
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
