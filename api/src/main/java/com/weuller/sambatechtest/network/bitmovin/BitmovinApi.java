package com.weuller.sambatechtest.network.bitmovin;

import com.weuller.sambatechtest.network.bitmovin.models.AclEntryModel;
import com.weuller.sambatechtest.network.bitmovin.models.EncodingOutputModel;
import com.weuller.sambatechtest.network.bitmovin.models.ManifestResourceModel;
import com.weuller.sambatechtest.network.bitmovin.models.MuxingStreamModel;
import com.weuller.sambatechtest.network.bitmovin.models.dash.*;
import com.weuller.sambatechtest.network.bitmovin.models.encoding.PostEncodingRequestModel;
import com.weuller.sambatechtest.network.bitmovin.models.encoding.PostEncodingResponseModel;
import com.weuller.sambatechtest.network.bitmovin.models.encoding.PostStartEncodingRequestModel;
import com.weuller.sambatechtest.network.bitmovin.models.encoding.PostStartEncodingResponse;
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



    public static final String RESPONSE_STATUS_SUCCESS = "SUCCESS";
    public static final String RESPONSE_STATUS_ERROR = "ERROR";

    public static final String ENCODING_NAME = "SambaTech Vídeo Encoder";
    public static final String ENCODER_VERSION = "2.22.0";

    public static final String BASE_URL = "https://api.bitmovin.com/v1";
    public static final String ENDPOINT_CREATE_ENCODING = "/encoding/encodings";
    public static final String ENDPOINT_CREATE_STREAMS = "/encoding/encodings/%s/streams";
    public static final String ENDPOINT_CREATE_MUXING_FMP4 = "/encoding/encodings/%s/muxings/fmp4";
    public static final String ENDPOINT_CREATE_MANIFEST = "/encoding/manifests/dash";
    public static final String ENDPOINT_CREATE_PERIOD = "/encoding/manifests/dash/%s/periods";
    public static final String ENDPOINT_CREATE_AUDIO_ADAPTATION_SET = "/encoding/manifests/dash/%s/periods/%s/adaptationsets/audio";
    public static final String ENDPOINT_CREATE_VIDEO_ADAPTATION_SET = "/encoding/manifests/dash/%s/periods/%s/adaptationsets/video";
    public static final String ENDPOINT_CREATE_REPRESENTATION = "/encoding/manifests/dash/%s/periods/%s/adaptationsets/%s/representations/fmp4";
    public static final String ENDPOINT_START_ENCODING = "/encoding/encodings/%s/start";


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

    public PostStreamsResponseModel createStreams(String inputId, String encodingId, String inputPath, String codecConfigId) {
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

    public PostMuxingFM4ResponseModel createMuxingFM4(String outputId, String encodingId, String streamId, String outputPath) {
        ArrayList<AclEntryModel> aclEntries = new ArrayList<>();
        aclEntries.add(new AclEntryModel());

        ArrayList<EncodingOutputModel> encodingOutputs = new ArrayList<>();
        encodingOutputs.add(new EncodingOutputModel(outputId, outputPath, aclEntries));

        ArrayList<MuxingStreamModel> muxingStreams = new ArrayList<>();
        muxingStreams.add(new MuxingStreamModel(streamId));

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

    public PostManifestResponseModel createManifest(String outputId, String outputPath) {
        ArrayList<AclEntryModel> aclEntries = new ArrayList<>();
        aclEntries.add(new AclEntryModel());

        EncodingOutputModel encodingOutput = new EncodingOutputModel(outputId, outputPath, aclEntries);

        PostManifestRequestModel body = new PostManifestRequestModel("Manifest", "manifest.mpd", encodingOutput);

        HttpHeaders header = getHeaders();

        String url = BitmovinApi.BASE_URL + BitmovinApi.ENDPOINT_CREATE_MANIFEST;

        HttpEntity<PostManifestRequestModel> request = new HttpEntity<>(body, header);

        ResponseEntity<PostManifestResponseModel> response = restTemplate.exchange(url, HttpMethod.POST, request, PostManifestResponseModel.class);

        if (response.getStatusCode() == HttpStatus.CREATED && response.getBody().getStatus().equals(RESPONSE_STATUS_SUCCESS)) {
            return response.getBody();
        } else {
            return null;
        }
    }

    public PostPeriodResponseModel createPeriod(String manifestId) {
        HttpHeaders header = getHeaders();

        String url = String.format(BitmovinApi.BASE_URL + BitmovinApi.ENDPOINT_CREATE_PERIOD, manifestId);

        HttpEntity request = new HttpEntity<>(header);

        ResponseEntity<PostPeriodResponseModel> response = restTemplate.exchange(url, HttpMethod.POST, request, PostPeriodResponseModel.class);

        if (response.getStatusCode() == HttpStatus.CREATED && response.getBody().getStatus().equals(RESPONSE_STATUS_SUCCESS)) {
            return response.getBody();
        } else {
            return null;
        }
    }

    public PostAudioAdaptationSetResponseModel createAudioAdaptationSet(String manifestId, String periodId) {
        PostAudioAdaptationSetRequestModel body = new PostAudioAdaptationSetRequestModel("pt");
        HttpHeaders header = getHeaders();

        String url = String.format(BitmovinApi.BASE_URL + BitmovinApi.ENDPOINT_CREATE_AUDIO_ADAPTATION_SET, manifestId, periodId);

        HttpEntity<PostAudioAdaptationSetRequestModel> request = new HttpEntity<>(body, header);

        ResponseEntity<PostAudioAdaptationSetResponseModel> response = restTemplate.exchange(url, HttpMethod.POST, request, PostAudioAdaptationSetResponseModel.class);

        if (response.getStatusCode() == HttpStatus.CREATED && response.getBody().getStatus().equals(RESPONSE_STATUS_SUCCESS)) {
            return response.getBody();
        } else {
            return null;
        }
    }

    public PostVideoAdaptationSetResponseModel createVideoAdaptationSet(String manifestId, String periodId) {
        HttpHeaders header = getHeaders();

        String url = String.format(BitmovinApi.BASE_URL + BitmovinApi.ENDPOINT_CREATE_VIDEO_ADAPTATION_SET, manifestId, periodId);

        HttpEntity request = new HttpEntity(header);

        ResponseEntity<PostVideoAdaptationSetResponseModel> response = restTemplate.exchange(url, HttpMethod.POST, request, PostVideoAdaptationSetResponseModel.class);

        if (response.getStatusCode() == HttpStatus.CREATED && response.getBody().getStatus().equals(RESPONSE_STATUS_SUCCESS)) {
            return response.getBody();
        } else {
            return null;
        }
    }

    public PostRepresentationResponseModel createRepresentation(String manifestId, String periodId, String adaptationSetId, String encodingId, String muxingId, String segmentPath) {
        PostRepresentationRequestModel body = new PostRepresentationRequestModel("TEMPLATE", encodingId, muxingId, segmentPath);
        HttpHeaders header = getHeaders();

        String url = String.format(BitmovinApi.BASE_URL + BitmovinApi.ENDPOINT_CREATE_REPRESENTATION, manifestId, periodId, adaptationSetId);

        HttpEntity<PostRepresentationRequestModel> request = new HttpEntity<>(body, header);

        ResponseEntity<PostRepresentationResponseModel> response = restTemplate.exchange(url, HttpMethod.POST, request, PostRepresentationResponseModel.class);

        if (response.getStatusCode() == HttpStatus.CREATED && response.getBody().getStatus().equals(RESPONSE_STATUS_SUCCESS)) {
            return response.getBody();
        } else {
            return null;
        }
    }

    public PostStartEncodingResponse startEncoding(String encodingId, String manifestId) {
        ManifestResourceModel manifestResource = new ManifestResourceModel(manifestId);
        ArrayList<ManifestResourceModel> manifestResourceArrayList = new ArrayList<>();
        manifestResourceArrayList.add(manifestResource);

        PostStartEncodingRequestModel body = new PostStartEncodingRequestModel(manifestResourceArrayList, "TWO_PASS");
        HttpHeaders header = getHeaders();

        String url = String.format(BitmovinApi.BASE_URL + BitmovinApi.ENDPOINT_START_ENCODING, encodingId);

        HttpEntity<PostStartEncodingRequestModel> request = new HttpEntity<>(body, header);

        ResponseEntity<PostStartEncodingResponse> response = restTemplate.exchange(url, HttpMethod.POST, request, PostStartEncodingResponse.class);

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
