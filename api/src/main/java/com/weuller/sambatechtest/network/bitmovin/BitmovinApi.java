package com.weuller.sambatechtest.network.bitmovin;

import com.weuller.sambatechtest.network.bitmovin.models.PostEncodingRequestModel;
import com.weuller.sambatechtest.network.bitmovin.models.PostEncodingResponseModel;
import com.weuller.sambatechtest.services.EncodingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class BitmovinApi {

    @Value("bitmovin.api_key")
    private String API_KEY = "";

    private static final String INPUT_ID = "";
    private static final String OUTPUT_ID = "";

    private static final String BASE_URL = "https://api.bitmovin.com/v1";

    private static final String ENDPOINT_CREATE_ENCODING = "/encoding/encodings";

    Logger log = LoggerFactory.getLogger(EncodingService.class);

    public BitmovinApi() {}

    public PostEncodingResponseModel.ResultWrapper.Encoding createEncoding() {
        RestTemplate restTemplate = new RestTemplate();
        PostEncodingRequestModel postBody = new PostEncodingRequestModel("SambaTech Vídeo Encoder", "2.22.0");
        String url = String.format("%s%s", BASE_URL, ENDPOINT_CREATE_ENCODING);

        HttpEntity<PostEncodingRequestModel> request = new HttpEntity<>(postBody);
        request.getHeaders().add("X-Api-Key", API_KEY);

        ResponseEntity<PostEncodingResponseModel> response = restTemplate.exchange(url, HttpMethod.POST, request, PostEncodingResponseModel.class);

        if (response.getStatusCode() == HttpStatus.CREATED && response.getBody().getStatus() == "SUCCESS") {
            return response.getBody().getData().getResult();
        } else {
            return null;
        }
    }
}
