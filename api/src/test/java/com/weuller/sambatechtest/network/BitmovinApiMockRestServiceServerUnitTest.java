package com.weuller.sambatechtest.network;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weuller.sambatechtest.SpringTestConfig;
import com.weuller.sambatechtest.network.bitmovin.BitmovinApi;
import com.weuller.sambatechtest.network.bitmovin.models.encoding.PostEncodingResponseModel;
import com.weuller.sambatechtest.network.bitmovin.models.muxings.PostMuxingFM4ResponseModel;
import com.weuller.sambatechtest.network.bitmovin.models.streams.PostStreamsRequestModel;
import com.weuller.sambatechtest.network.bitmovin.models.streams.PostStreamsResponseModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;

import static com.weuller.sambatechtest.network.bitmovin.BitmovinApi.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = SpringTestConfig.class)
public class BitmovinApiMockRestServiceServerUnitTest {

    @Autowired
    private BitmovinApi bitmovinApi;

    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void init() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void Given_MockingByMockRestServiceServer_When_CreateEncodingIsCalledAndResponseStatusIsSuccess_Then_EncodingWillBeCreated() throws Exception {
        PostEncodingResponseModel.ResultWrapper.Encoding encoding = new PostEncodingResponseModel.ResultWrapper.Encoding();
        PostEncodingResponseModel.ResultWrapper resultWrapper = new PostEncodingResponseModel.ResultWrapper();
        PostEncodingResponseModel response = new PostEncodingResponseModel();

        encoding.setId("cb90b80c-8867-4e3b-8479-174aa2843f62");
        encoding.setName(ENCODING_NAME);
        encoding.setEncoderVersion(ENCODER_VERSION);

        resultWrapper.setResult(encoding);

        response.setData(resultWrapper);
        response.setStatus(RESPONSE_STATUS_SUCCESS);

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(BitmovinApi.BASE_URL + BitmovinApi.ENDPOINT_CREATE_ENCODING)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(
                        withStatus(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(response))
                );

        PostEncodingResponseModel encodingResponse = bitmovinApi.createEncoding();

        Assert.assertNotNull(encodingResponse);
        Assert.assertNotNull(encodingResponse.getData().getResult().getId());
    }

    @Test
    public void Given_MockingRequestAndResult_When_CreateEncodingIsCalledAndRequestIsOkAndResponseStatusIsError_Then_EncodingWillNotBeCreated() throws Exception {
        PostEncodingResponseModel.ResultWrapper.Encoding encoding = new PostEncodingResponseModel.ResultWrapper.Encoding();
        PostEncodingResponseModel.ResultWrapper resultWrapper = new PostEncodingResponseModel.ResultWrapper();
        PostEncodingResponseModel response = new PostEncodingResponseModel();

        encoding.setId("");
        encoding.setName(ENCODING_NAME);
        encoding.setEncoderVersion(ENCODER_VERSION);

        resultWrapper.setResult(encoding);

        response.setData(resultWrapper);
        response.setStatus(RESPONSE_STATUS_ERROR);

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(BitmovinApi.BASE_URL + BitmovinApi.ENDPOINT_CREATE_ENCODING)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(
                        withStatus(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(response))
                );

        PostEncodingResponseModel encodingResponse = bitmovinApi.createEncoding();

        Assert.assertNull(encodingResponse);
    }

    @Test(expected = HttpClientErrorException.class)
    public void Given_MockingRequestAndResult_When_CreateEncodingIsCalledAndRequestIsErrorAndResponseStatusIsError_Then_ExceptionIsThrown() throws Exception {
        PostEncodingResponseModel.ResultWrapper.Encoding encoding = new PostEncodingResponseModel.ResultWrapper.Encoding();
        PostEncodingResponseModel.ResultWrapper resultWrapper = new PostEncodingResponseModel.ResultWrapper();
        PostEncodingResponseModel response = new PostEncodingResponseModel();

        encoding.setId("");
        encoding.setName(ENCODING_NAME);
        encoding.setEncoderVersion(ENCODER_VERSION);

        resultWrapper.setResult(encoding);

        response.setData(resultWrapper);
        response.setStatus(RESPONSE_STATUS_ERROR);

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(BitmovinApi.BASE_URL + BitmovinApi.ENDPOINT_CREATE_ENCODING)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(
                        withStatus(HttpStatus.BAD_REQUEST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(response))
                );

        bitmovinApi.createEncoding();
    }

    @Test
    public void Given_MockingByMockRestServiceServer_When_CreateStreamsIsCalledAndResponseStatusIsSuccess_Then_StreamsWillBeCreated() throws Exception {
        String encodingId = "f3177c2e-0000-4ba6-bd20-1dee353d8a72";
        String inputId = "47c3e8a3-ab76-46f5-8b07-cd2e2b0c3728";
        String codecConfigId = "d09c1a8a-4c56-4392-94d8-81712118aae0";
        String inputPath = "/videos/movie1.mp4";


        PostStreamsResponseModel.ResultWrapper.Stream.StreamInput streamInput = new PostStreamsResponseModel.ResultWrapper.Stream.StreamInput(inputId, inputPath, "AUTO");
        ArrayList<PostStreamsResponseModel.ResultWrapper.Stream.StreamInput> streamInputArrayList = new ArrayList<>();
        streamInputArrayList.add(streamInput);

        PostStreamsResponseModel.ResultWrapper.Stream stream = new PostStreamsResponseModel.ResultWrapper.Stream();
        PostStreamsResponseModel.ResultWrapper resultWrapper = new PostStreamsResponseModel.ResultWrapper();
        PostStreamsResponseModel response = new PostStreamsResponseModel();

        stream.setId("6d84e126-d10c-4e52-bbfb-bd4c92bc8333");
        stream.setCodecConfigId(codecConfigId);
        stream.setInputStreams(streamInputArrayList);

        resultWrapper.setResult(stream);

        response.setData(resultWrapper);
        response.setStatus(RESPONSE_STATUS_SUCCESS);

        String url = String.format(BitmovinApi.BASE_URL + BitmovinApi.ENDPOINT_CREATE_STREAMS, encodingId);

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(url)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(
                        withStatus(HttpStatus.CREATED)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(mapper.writeValueAsString(response))
                );

        PostStreamsResponseModel streamsResponse = bitmovinApi.createStreams(encodingId, inputPath, codecConfigId);

        Assert.assertNotNull(streamsResponse);
        Assert.assertNotNull(streamsResponse.getData().getResult().getId());
    }

    @Test
    public void Given_MockingRequestAndResult_When_CreateStreamsIsCalledAndRequestIsOkAndResponseStatusIsError_Then_StreamsWillNotBeCreated() throws Exception {
        String encodingId = "f3177c2e-0000-4ba6-bd20-1dee353d8a72";
        String inputId = "47c3e8a3-ab76-46f5-8b07-cd2e2b0c3728";
        String codecConfigId = "d09c1a8a-4c56-4392-94d8-81712118aae0";
        String inputPath = "/videos/movie1.mp4";


        PostStreamsResponseModel.ResultWrapper.Stream.StreamInput streamInput = new PostStreamsResponseModel.ResultWrapper.Stream.StreamInput(inputId, inputPath, "AUTO");
        ArrayList<PostStreamsResponseModel.ResultWrapper.Stream.StreamInput> streamInputArrayList = new ArrayList<>();
        streamInputArrayList.add(streamInput);

        PostStreamsResponseModel.ResultWrapper.Stream stream = new PostStreamsResponseModel.ResultWrapper.Stream();
        PostStreamsResponseModel.ResultWrapper resultWrapper = new PostStreamsResponseModel.ResultWrapper();
        PostStreamsResponseModel response = new PostStreamsResponseModel();

        stream.setId("");
        stream.setCodecConfigId(codecConfigId);
        stream.setInputStreams(streamInputArrayList);

        resultWrapper.setResult(stream);

        response.setData(resultWrapper);
        response.setStatus(RESPONSE_STATUS_ERROR);

        String url = String.format(BitmovinApi.BASE_URL + BitmovinApi.ENDPOINT_CREATE_STREAMS, encodingId);

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(url)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(
                        withStatus(HttpStatus.CREATED)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(mapper.writeValueAsString(response))
                );

        PostStreamsResponseModel streamsResponse = bitmovinApi.createStreams(encodingId, inputPath, codecConfigId);

        Assert.assertNull(streamsResponse);
    }

    @Test(expected = HttpClientErrorException.class)
    public void Given_MockingRequestAndResult_When_CreateStreamsIsCalledAndRequestIsErrorAndResponseStatusIsError_Then_ExceptionIsThrown() throws Exception {
        String encodingId = "f3177c2e-0000-4ba6-bd20-1dee353d8a72";
        String inputId = "47c3e8a3-ab76-46f5-8b07-cd2e2b0c3728";
        String codecConfigId = "d09c1a8a-4c56-4392-94d8-81712118aae0";
        String inputPath = "/videos/movie1.mp4";


        PostStreamsResponseModel.ResultWrapper.Stream.StreamInput streamInput = new PostStreamsResponseModel.ResultWrapper.Stream.StreamInput(inputId, inputPath, "AUTO");
        ArrayList<PostStreamsResponseModel.ResultWrapper.Stream.StreamInput> streamInputArrayList = new ArrayList<>();
        streamInputArrayList.add(streamInput);

        PostStreamsResponseModel.ResultWrapper.Stream stream = new PostStreamsResponseModel.ResultWrapper.Stream();
        PostStreamsResponseModel.ResultWrapper resultWrapper = new PostStreamsResponseModel.ResultWrapper();
        PostStreamsResponseModel response = new PostStreamsResponseModel();

        stream.setId("");
        stream.setCodecConfigId(codecConfigId);
        stream.setInputStreams(streamInputArrayList);

        resultWrapper.setResult(stream);

        response.setData(resultWrapper);
        response.setStatus(RESPONSE_STATUS_ERROR);

        String url = String.format(BitmovinApi.BASE_URL + BitmovinApi.ENDPOINT_CREATE_STREAMS, encodingId);

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(url)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(
                        withStatus(HttpStatus.BAD_REQUEST)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(mapper.writeValueAsString(response))
                );

        bitmovinApi.createStreams(encodingId, inputPath, codecConfigId);
    }

    @Test
    public void Given_MockingByMockRestServiceServer_When_CreateMuxingFmp4IsCalledAndResponseStatusIsSuccess_Then_MuxingFmp4WillBeCreated() throws Exception {
        String encodingId = "f3177c2e-0000-4ba6-bd20-1dee353d8a72";
        String streamId = "d09c1a8a-4c56-4392-94d8-81712118aae0";
        String outputPath = "/encodings/movies/movie-1/video_720/";

        PostMuxingFM4ResponseModel.ResultWrapper.Fmp4Muxing fmp4Muxing = new PostMuxingFM4ResponseModel.ResultWrapper.Fmp4Muxing();
        PostMuxingFM4ResponseModel.ResultWrapper resultWrapper = new PostMuxingFM4ResponseModel.ResultWrapper();
        PostMuxingFM4ResponseModel response = new PostMuxingFM4ResponseModel();

        fmp4Muxing.setId("cb90b80c-8867-4e3b-8479-174aa2843f62");
        
        resultWrapper.setResult(fmp4Muxing);

        response.setData(resultWrapper);
        response.setStatus(RESPONSE_STATUS_SUCCESS);

        String url = String.format(BitmovinApi.BASE_URL + ENDPOINT_CREATE_MUXING_FMP4, encodingId);

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(url)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(
                        withStatus(HttpStatus.CREATED)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(mapper.writeValueAsString(response))
                );

        PostMuxingFM4ResponseModel fm4Response = bitmovinApi.createMuxingFM4(encodingId, streamId, outputPath);

        Assert.assertNotNull(fm4Response);
        Assert.assertNotNull(fm4Response.getData().getResult().getId());
    }

}
