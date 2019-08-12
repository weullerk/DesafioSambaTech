package com.weuller.sambatechtest.network.bitmovin;

import com.weuller.sambatechtest.network.bitmovin.models.encoding.PostEncodingRequestModel;
import com.weuller.sambatechtest.network.bitmovin.models.encoding.PostEncodingResponseModel;
import com.weuller.sambatechtest.network.bitmovin.models.muxings.PostMuxingFM4RequestModel;
import com.weuller.sambatechtest.network.bitmovin.models.muxings.PostMuxingFM4ResponseModel;
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

    @Value("bitmovin.input_id")
    private String INPUT_ID = "";

    @Value("bitmovin.output_id")
    private String OUTPUT_ID = "";

    public static final String RESPONSE_STATUS_SUCCESS = "SUCCESS";
    public static final String RESPONSE_STATUS_ERROR = "ERROR";

    public static final String ENCODING_NAME = "SambaTech Vídeo Encoder";
    public static final String ENCODER_VERSION = "2.22.0";

    public static final String BASE_URL = "https://api.bitmovin.com/v1";
    public static final String ENDPOINT_CREATE_ENCODING = "/encoding/encodings";
    public static final String ENDPOINT_CREATE_STREAMS = "/encoding/encodings/%s/streams";
    public static final String ENDPOINT_CREATE_MUXING_FMP4 = "/encoding/encodings/%s/muxings/fmp4";

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

    public PostStreamsResponseModel createStreams(String encodingId, String inputPath, String codecConfigId) {
        PostStreamsRequestModel.StreamInput streamInput = new PostStreamsRequestModel.StreamInput(INPUT_ID, inputPath, "AUTO");

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

    public PostMuxingFM4ResponseModel createMuxingFM4(String encodingId, String streamId, String outputPath) {
        ArrayList<PostMuxingFM4RequestModel.EncodingOutput.AclEntry> aclEntries = new ArrayList<>();
        aclEntries.add(new PostMuxingFM4RequestModel.EncodingOutput.AclEntry());

        ArrayList<PostMuxingFM4RequestModel.EncodingOutput> encodingOutputs = new ArrayList<>();
        encodingOutputs.add(new PostMuxingFM4RequestModel.EncodingOutput(OUTPUT_ID, outputPath, aclEntries));

        ArrayList<PostMuxingFM4RequestModel.MuxingStream> muxingStreams = new ArrayList<>();
        muxingStreams.add(new PostMuxingFM4RequestModel.MuxingStream(streamId));

        PostMuxingFM4RequestModel body = new PostMuxingFM4RequestModel();
        body.setOutputs(encodingOutputs);
        body.setStreams(muxingStreams);

        HttpHeaders header = getHeaders();

        String url = String.format(BitmovinApi.BASE_URL + BitmovinApi.ENDPOINT_CREATE_MUXING_FMP4, encodingId);

        HttpEntity<PostMuxingFM4RequestModel> request = new HttpEntity<>(body, header);

        ResponseEntity<PostMuxingFM4ResponseModel> response = restTemplate.exchange(url, HttpMethod.POST, request, PostMuxingFM4ResponseModel.class);

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
