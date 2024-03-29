package com.weuller.sambatechtest.network;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weuller.sambatechtest.network.bitmovin.BitmovinApi;
import com.weuller.sambatechtest.network.bitmovin.models.dash.*;
import com.weuller.sambatechtest.network.bitmovin.models.encoding.PostEncodingResponseModel;
import com.weuller.sambatechtest.network.bitmovin.models.encoding.PostStartEncodingResponse;
import com.weuller.sambatechtest.network.bitmovin.models.muxings.PostMuxingFM4ResponseModel;
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
import sun.plugin.dom.exception.InvalidStateException;

import java.net.URI;
import java.util.ArrayList;

import static com.weuller.sambatechtest.network.bitmovin.BitmovinApi.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

@RunWith(SpringRunner.class)
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

        String encodingResponse = bitmovinApi.createEncoding();

        Assert.assertNotNull(encodingResponse);
    }

    @Test(expected = InvalidStateException.class)
    public void Given_MockingByMockRestServiceServer_When_CreateEncodingIsCalledAndResponseStatusIsError_Then_ExceptionIsThrown() throws Exception {
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

        bitmovinApi.createEncoding();
    }

    @Test(expected = HttpClientErrorException.class)
    public void Given_MockingRequestAndResult_When_CreateEncodingIsCalledAndRequestIsError_Then_ExceptionIsThrown() throws Exception {
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(BitmovinApi.BASE_URL + BitmovinApi.ENDPOINT_CREATE_ENCODING)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.BAD_REQUEST));

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

        String streamsResponse = bitmovinApi.createStreams(inputId, encodingId, inputPath, codecConfigId);

        Assert.assertNotNull(streamsResponse);
    }

    @Test(expected = InvalidStateException.class)
    public void Given_MockingByMockRestServiceServer_When_CreateStreamsIsCalledAndResponseStatusIsError_Then_ExceptionIsThrown() throws Exception {
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

        bitmovinApi.createStreams(inputId, encodingId, inputPath, codecConfigId);
    }

    @Test(expected = HttpClientErrorException.class)
    public void Given_MockingRequestAndResult_When_CreateStreamsIsCalledAndRequestIsError_Then_ExceptionIsThrow() throws Exception {
        String encodingId = "f3177c2e-0000-4ba6-bd20-1dee353d8a72";
        String inputId = "47c3e8a3-ab76-46f5-8b07-cd2e2b0c3728";
        String codecConfigId = "d09c1a8a-4c56-4392-94d8-81712118aae0";
        String inputPath = "/videos/movie1.mp4";

        String url = String.format(BitmovinApi.BASE_URL + BitmovinApi.ENDPOINT_CREATE_STREAMS, encodingId);

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(url)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.BAD_REQUEST));

        bitmovinApi.createStreams(inputId, encodingId, inputPath, codecConfigId);
    }

    @Test
    public void Given_MockingByMockRestServiceServer_When_CreateMuxingFmp4IsCalledAndResponseStatusIsSuccess_Then_MuxingFmp4WillBeCreated() throws Exception {
        String encodingId = "f3177c2e-0000-4ba6-bd20-1dee353d8a72";
        String streamId = "d09c1a8a-4c56-4392-94d8-81712118aae0";
        String outputPath = "/encodings/movies/movie-1/video_720/";
        String outputId = "55354be6-0237-42bb-ae85-a2d4ef1ed19e";

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

        String fm4Response = bitmovinApi.createMuxingFM4(outputId, encodingId, streamId, outputPath);

        Assert.assertNotNull(fm4Response);
    }

    @Test(expected = InvalidStateException.class)
    public void Given_MockingByMockRestServiceServer_When_CreateMuxingFmp4IsCalledAndResponseStatusIsError_Then_ExceptionIsThrown() throws Exception {
        String encodingId = "f3177c2e-0000-4ba6-bd20-1dee353d8a72";
        String streamId = "d09c1a8a-4c56-4392-94d8-81712118aae0";
        String outputPath = "/encodings/movies/movie-1/video_720/";
        String outputId = "55354be6-0237-42bb-ae85-a2d4ef1ed19e";

        PostMuxingFM4ResponseModel.ResultWrapper.Fmp4Muxing fmp4Muxing = new PostMuxingFM4ResponseModel.ResultWrapper.Fmp4Muxing();
        PostMuxingFM4ResponseModel.ResultWrapper resultWrapper = new PostMuxingFM4ResponseModel.ResultWrapper();
        PostMuxingFM4ResponseModel response = new PostMuxingFM4ResponseModel();

        fmp4Muxing.setId("");

        resultWrapper.setResult(fmp4Muxing);

        response.setData(resultWrapper);
        response.setStatus(RESPONSE_STATUS_ERROR);

        String url = String.format(BitmovinApi.BASE_URL + ENDPOINT_CREATE_MUXING_FMP4, encodingId);

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(url)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(
                        withStatus(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(response))
                );

        bitmovinApi.createMuxingFM4(outputId, encodingId, streamId, outputPath);
    }

    @Test(expected = HttpClientErrorException.class)
    public void Given_MockingByMockRestServiceServer_When_CreateMuxingFmp4IsCalledAndRequestIsError_Then_ExceptionIsThrow() throws Exception {
        String encodingId = "f3177c2e-0000-4ba6-bd20-1dee353d8a72";
        String streamId = "d09c1a8a-4c56-4392-94d8-81712118aae0";
        String outputPath = "/encodings/movies/movie-1/video_720/";
        String outputId = "55354be6-0237-42bb-ae85-a2d4ef1ed19e";


        String url = String.format(BitmovinApi.BASE_URL + ENDPOINT_CREATE_MUXING_FMP4, encodingId);

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(url)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.BAD_REQUEST));

        bitmovinApi.createMuxingFM4(outputId, encodingId, streamId, outputPath);
    }

    @Test
    public void Given_MockingByMockRestServiceServer_When_CreateManifestIsCalledAndResponseStatusIsSuccess_Then_ManifestWillBeCreated() throws Exception {
        String outputId = "55354be6-0237-42bb-ae85-a2d4ef1ed19e";
        String outputPath = "/encodings/movies/movie-1/video_720/";

        PostManifestResponseModel.ResultWrapper.DashManifest dashManifest = new PostManifestResponseModel.ResultWrapper.DashManifest();
        PostManifestResponseModel.ResultWrapper resultWrapper = new PostManifestResponseModel.ResultWrapper();
        PostManifestResponseModel response = new PostManifestResponseModel();

        dashManifest.setId("cb90b80c-8867-4e3b-8479-174aa2843f62");

        resultWrapper.setResult(dashManifest);

        response.setData(resultWrapper);
        response.setStatus(RESPONSE_STATUS_SUCCESS);

        String url = BitmovinApi.BASE_URL + ENDPOINT_CREATE_MANIFEST;

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(url)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(
                        withStatus(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(response))
                );

        String manifestResponse = bitmovinApi.createManifest(outputId, outputPath);

        Assert.assertNotNull(manifestResponse);
    }

    @Test(expected = InvalidStateException.class)
    public void Given_MockingByMockRestServiceServer_When_CreateManifestIsCalledAndResponseStatusIsError_Then_ExceptionIsThrown() throws Exception {
        String outputId = "55354be6-0237-42bb-ae85-a2d4ef1ed19e";
        String outputPath = "/encodings/movies/movie-1/video_720/";

        PostManifestResponseModel.ResultWrapper.DashManifest dashManifest = new PostManifestResponseModel.ResultWrapper.DashManifest();
        PostManifestResponseModel.ResultWrapper resultWrapper = new PostManifestResponseModel.ResultWrapper();
        PostManifestResponseModel response = new PostManifestResponseModel();

        dashManifest.setId("");

        resultWrapper.setResult(dashManifest);

        response.setData(resultWrapper);
        response.setStatus(RESPONSE_STATUS_ERROR);

        String url = BitmovinApi.BASE_URL + ENDPOINT_CREATE_MANIFEST;

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(url)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(
                        withStatus(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(response))
                );

        bitmovinApi.createManifest(outputId, outputPath);
    }

    @Test(expected = HttpClientErrorException.class)
    public void Given_MockingByMockRestServiceServer_When_CreateManifestIsCalledAndRequestIsError_Then_ExceptionIsThrow() throws Exception {
        String outputId = "55354be6-0237-42bb-ae85-a2d4ef1ed19e";
        String outputPath = "/encodings/movies/movie-1/video_720/";

        String url = BitmovinApi.BASE_URL + ENDPOINT_CREATE_MANIFEST;

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(url)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.BAD_REQUEST));

        bitmovinApi.createManifest(outputId, outputPath);
    }

    @Test
    public void Given_MockingByMockRestServiceServer_When_CreatePeriodIsCalledAndResponseStatusIsSuccess_Then_PeriodWillBeCreated() throws Exception {
        String manifestId = "45ef21d2-1f62-4004-9166-6d5e248270f6";

        PostPeriodResponseModel.ResultWrapper.Period period = new PostPeriodResponseModel.ResultWrapper.Period();
        PostPeriodResponseModel.ResultWrapper resultWrapper = new PostPeriodResponseModel.ResultWrapper();
        PostPeriodResponseModel response = new PostPeriodResponseModel();

        period.setId("cb90b80c-8867-4e3b-8479-174aa2843f62");

        resultWrapper.setResult(period);

        response.setData(resultWrapper);
        response.setStatus(RESPONSE_STATUS_SUCCESS);

        String url = String.format(BitmovinApi.BASE_URL + ENDPOINT_CREATE_PERIOD, manifestId);

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(url)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(
                        withStatus(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(response))
                );

        String periodResponse = bitmovinApi.createPeriod(manifestId);

        Assert.assertNotNull(periodResponse);
    }

    @Test(expected = InvalidStateException.class)
    public void Given_MockingByMockRestServiceServer_When_CreatePeriodIsCalledAndResponseStatusIsSuccess_Then_ExceptionIsThrown() throws Exception {
        String manifestId = "45ef21d2-1f62-4004-9166-6d5e248270f6";

        PostPeriodResponseModel.ResultWrapper.Period period = new PostPeriodResponseModel.ResultWrapper.Period();
        PostPeriodResponseModel.ResultWrapper resultWrapper = new PostPeriodResponseModel.ResultWrapper();
        PostPeriodResponseModel response = new PostPeriodResponseModel();

        period.setId("");

        resultWrapper.setResult(period);

        response.setData(resultWrapper);
        response.setStatus(RESPONSE_STATUS_ERROR);

        String url = String.format(BitmovinApi.BASE_URL + ENDPOINT_CREATE_PERIOD, manifestId);

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(url)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(
                        withStatus(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(response))
                );

        bitmovinApi.createPeriod(manifestId);
    }

    @Test(expected = HttpClientErrorException.class)
    public void Given_MockingByMockRestServiceServer_When_CreatePeriodIsCalledAndRequestIsError_Then_Exception() throws Exception {
        String manifestId = "45ef21d2-1f62-4004-9166-6d5e248270f6";

        String url = String.format(BitmovinApi.BASE_URL + ENDPOINT_CREATE_PERIOD, manifestId);

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(url)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.BAD_REQUEST));

        bitmovinApi.createPeriod(manifestId);
    }

    @Test
    public void Given_MockingByMockRestServiceServer_When_CreateAudioAdaptationSetIsCalledAndResponseStatusIsSuccess_Then_AudioAdaptationSetWillBeCreated() throws Exception {
        String manifestId = "45ef21d2-1f62-4004-9166-6d5e248270f6";
        String periodId = "cb90b80c-8867-4e3b-8479-174aa2843f62";

        PostAudioAdaptationSetResponseModel.ResultWrapper.AudioAdaptationSet audioAdaptationSet = new PostAudioAdaptationSetResponseModel.ResultWrapper.AudioAdaptationSet();
        PostAudioAdaptationSetResponseModel.ResultWrapper resultWrapper = new PostAudioAdaptationSetResponseModel.ResultWrapper();
        PostAudioAdaptationSetResponseModel response = new PostAudioAdaptationSetResponseModel();

        audioAdaptationSet.setId("cb90b80c-8867-4e3b-8479-174aa2843f62");

        resultWrapper.setResult(audioAdaptationSet);

        response.setData(resultWrapper);
        response.setStatus(RESPONSE_STATUS_SUCCESS);

        String url = String.format(BitmovinApi.BASE_URL + ENDPOINT_CREATE_AUDIO_ADAPTATION_SET, manifestId, periodId);

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(url)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(
                        withStatus(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(response))
                );

        String audioAdaptationSetResponse = bitmovinApi.createAudioAdaptationSet(manifestId, periodId);

        Assert.assertNotNull(audioAdaptationSetResponse);
    }

    @Test(expected = InvalidStateException.class)
    public void Given_MockingByMockRestServiceServer_When_CreateAudioAdaptationSetIsCalledAndResponseStatusIsError_Then_ExceptionIsThrown() throws Exception {
        String manifestId = "45ef21d2-1f62-4004-9166-6d5e248270f6";
        String periodId = "cb90b80c-8867-4e3b-8479-174aa2843f62";

        PostAudioAdaptationSetResponseModel.ResultWrapper.AudioAdaptationSet audioAdaptationSet = new PostAudioAdaptationSetResponseModel.ResultWrapper.AudioAdaptationSet();
        PostAudioAdaptationSetResponseModel.ResultWrapper resultWrapper = new PostAudioAdaptationSetResponseModel.ResultWrapper();
        PostAudioAdaptationSetResponseModel response = new PostAudioAdaptationSetResponseModel();

        audioAdaptationSet.setId("");

        resultWrapper.setResult(audioAdaptationSet);

        response.setData(resultWrapper);
        response.setStatus(RESPONSE_STATUS_ERROR);

        String url = String.format(BitmovinApi.BASE_URL + ENDPOINT_CREATE_AUDIO_ADAPTATION_SET, manifestId, periodId);

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(url)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(
                        withStatus(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(response))
                );

        bitmovinApi.createAudioAdaptationSet(manifestId, periodId);
    }

    @Test(expected = HttpClientErrorException.class)
    public void Given_MockingByMockRestServiceServer_When_CreateAudioAdaptationSetIsCalledAndRequestIsError_Then_ExceptionIsThrown() throws Exception {
        String manifestId = "45ef21d2-1f62-4004-9166-6d5e248270f6";
        String periodId = "cb90b80c-8867-4e3b-8479-174aa2843f62";

        String url = String.format(BitmovinApi.BASE_URL + ENDPOINT_CREATE_AUDIO_ADAPTATION_SET, manifestId, periodId);

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(url)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.BAD_REQUEST));

        bitmovinApi.createAudioAdaptationSet(manifestId, periodId);
    }

    @Test(expected = InvalidStateException.class)
    public void Given_MockingByMockRestServiceServer_When_CreateVideoAdaptationSetIsCalledAndResponseStatusIsError_Then_ExceptionIsThrown() throws Exception {
        String manifestId = "45ef21d2-1f62-4004-9166-6d5e248270f6";
        String periodId = "cb90b80c-8867-4e3b-8479-174aa2843f62";

        PostVideoAdaptationSetResponseModel.ResultWrapper.VideoAdaptationSet videoAdaptationSet = new PostVideoAdaptationSetResponseModel.ResultWrapper.VideoAdaptationSet();
        PostVideoAdaptationSetResponseModel.ResultWrapper resultWrapper = new PostVideoAdaptationSetResponseModel.ResultWrapper();
        PostVideoAdaptationSetResponseModel response = new PostVideoAdaptationSetResponseModel();

        videoAdaptationSet.setId("");

        resultWrapper.setResult(videoAdaptationSet);

        response.setData(resultWrapper);
        response.setStatus(RESPONSE_STATUS_ERROR);

        String url = String.format(BitmovinApi.BASE_URL + ENDPOINT_CREATE_VIDEO_ADAPTATION_SET, manifestId, periodId);

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(url)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(
                        withStatus(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(response))
                );

        bitmovinApi.createVideoAdaptationSet(manifestId, periodId);
    }

    @Test(expected = HttpClientErrorException.class)
    public void Given_MockingByMockRestServiceServer_When_CreateVideoAdaptationSetIsCalledAndRequestIsError_Then_ExceptionIsThrown() throws Exception {
        String manifestId = "45ef21d2-1f62-4004-9166-6d5e248270f6";
        String periodId = "cb90b80c-8867-4e3b-8479-174aa2843f62";

        String url = String.format(BitmovinApi.BASE_URL + ENDPOINT_CREATE_VIDEO_ADAPTATION_SET, manifestId, periodId);

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(url)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.BAD_REQUEST));

        bitmovinApi.createVideoAdaptationSet(manifestId, periodId);
    }

    @Test
    public void Given_MockingByMockRestServiceServer_When_CreateRepresentationIsCalledAndResponseStatusIsSuccess_Then_RepresentationWillBeCreated() throws Exception {
        String manifestId = "45ef21d2-1f62-4004-9166-6d5e248270f6";
        String periodId = "cb90b80c-8867-4e3b-8479-174aa2843f62";
        String adaptationSetId = "ae1c49de-9b7a-4b6f-9d0b-401d7d64e835";
        String encodingId = "f3177c2e-0000-4ba6-bd20-1dee353d8a72";
        String muxingId = "cb90b80c-8867-4e3b-8479-174aa2843f62";
        String segmentPath = "custom/path/h264/384_240000/fmp4";

        PostRepresentationResponseModel.ResultWrapper.DashFmp4Representation dashFmp4Representation = new PostRepresentationResponseModel.ResultWrapper.DashFmp4Representation();
        PostRepresentationResponseModel.ResultWrapper resultWrapper = new PostRepresentationResponseModel.ResultWrapper();
        PostRepresentationResponseModel response = new PostRepresentationResponseModel();

        dashFmp4Representation.setId("cb90b80c-8867-4e3b-8479-174aa2843f62");

        resultWrapper.setResult(dashFmp4Representation);

        response.setData(resultWrapper);
        response.setStatus(RESPONSE_STATUS_SUCCESS);

        String url = String.format(BitmovinApi.BASE_URL + ENDPOINT_CREATE_REPRESENTATION, manifestId, periodId, adaptationSetId);

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(url)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(
                        withStatus(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(response))
                );

        String representationResponse = bitmovinApi.createRepresentation(manifestId, periodId, adaptationSetId, encodingId, muxingId, segmentPath);

        Assert.assertNotNull(representationResponse);
    }

    @Test(expected = InvalidStateException.class)
    public void Given_MockingByMockRestServiceServer_When_CreateRepresentationIsCalled_Then_ExceptionIsThrown() throws Exception {
        String manifestId = "45ef21d2-1f62-4004-9166-6d5e248270f6";
        String periodId = "cb90b80c-8867-4e3b-8479-174aa2843f62";
        String adaptationSetId = "ae1c49de-9b7a-4b6f-9d0b-401d7d64e835";
        String encodingId = "f3177c2e-0000-4ba6-bd20-1dee353d8a72";
        String muxingId = "cb90b80c-8867-4e3b-8479-174aa2843f62";
        String segmentPath = "custom/path/h264/384_240000/fmp4";

        PostRepresentationResponseModel.ResultWrapper.DashFmp4Representation dashFmp4Representation = new PostRepresentationResponseModel.ResultWrapper.DashFmp4Representation();
        PostRepresentationResponseModel.ResultWrapper resultWrapper = new PostRepresentationResponseModel.ResultWrapper();
        PostRepresentationResponseModel response = new PostRepresentationResponseModel();

        dashFmp4Representation.setId("");

        resultWrapper.setResult(dashFmp4Representation);

        response.setData(resultWrapper);
        response.setStatus(RESPONSE_STATUS_ERROR);

        String url = String.format(BitmovinApi.BASE_URL + ENDPOINT_CREATE_REPRESENTATION, manifestId, periodId, adaptationSetId);

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(url)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(
                        withStatus(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(response))
                );

        bitmovinApi.createRepresentation(manifestId, periodId, adaptationSetId, encodingId, muxingId, segmentPath);
    }

    @Test(expected = HttpClientErrorException.class)
    public void Given_MockingByMockRestServiceServer_When_CreateRepresentationIsCalledAndRequestIsError_Then_ExceptionIsThrown() throws Exception {
        String manifestId = "45ef21d2-1f62-4004-9166-6d5e248270f6";
        String periodId = "cb90b80c-8867-4e3b-8479-174aa2843f62";
        String adaptationSetId = "ae1c49de-9b7a-4b6f-9d0b-401d7d64e835";
        String encodingId = "f3177c2e-0000-4ba6-bd20-1dee353d8a72";
        String muxingId = "cb90b80c-8867-4e3b-8479-174aa2843f62";
        String segmentPath = "custom/path/h264/384_240000/fmp4";

        String url = String.format(BitmovinApi.BASE_URL + ENDPOINT_CREATE_REPRESENTATION, manifestId, periodId, adaptationSetId);

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(url)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.BAD_REQUEST));

        bitmovinApi.createRepresentation(manifestId, periodId, adaptationSetId, encodingId, muxingId, segmentPath);
    }

    @Test
    public void Given_MockingByMockRestServiceServer_When_StartEncodingIsCalledAndResponseStatusIsSuccess_Then_EncodingIsMade() throws Exception {
        String manifestId = "45ef21d2-1f62-4004-9166-6d5e248270f6";
        String encodingId = "f3177c2e-0000-4ba6-bd20-1dee353d8a72";

        PostStartEncodingResponse.ResultWrapper.BitmovinResponse bitmovinResponse = new PostStartEncodingResponse.ResultWrapper.BitmovinResponse();
        PostStartEncodingResponse.ResultWrapper resultWrapper = new PostStartEncodingResponse.ResultWrapper();
        PostStartEncodingResponse response = new PostStartEncodingResponse();

        bitmovinResponse.setId("6d84e126-d10c-4e52-bbfb-bd4c92bc8333");

        resultWrapper.setResult(bitmovinResponse);

        response.setData(resultWrapper);
        response.setStatus(RESPONSE_STATUS_SUCCESS);

        String url = String.format(BitmovinApi.BASE_URL + ENDPOINT_START_ENCODING, manifestId);

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(url)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(
                        withStatus(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(response))
                );

        String bitmovinResponseResult = bitmovinApi.startEncoding(manifestId, encodingId);

        Assert.assertNotNull(bitmovinResponseResult);
    }

    @Test(expected = InvalidStateException.class)
    public void Given_MockingByMockRestServiceServer_When_StartEncodingIsCalledAndResponseStatusIsError_Then_ExceptionIsThrown() throws Exception {
        String manifestId = "45ef21d2-1f62-4004-9166-6d5e248270f6";
        String encodingId = "f3177c2e-0000-4ba6-bd20-1dee353d8a72";

        PostStartEncodingResponse.ResultWrapper.BitmovinResponse bitmovinResponse = new PostStartEncodingResponse.ResultWrapper.BitmovinResponse();
        PostStartEncodingResponse.ResultWrapper resultWrapper = new PostStartEncodingResponse.ResultWrapper();
        PostStartEncodingResponse response = new PostStartEncodingResponse();

        bitmovinResponse.setId("");

        resultWrapper.setResult(bitmovinResponse);

        response.setData(resultWrapper);
        response.setStatus(RESPONSE_STATUS_ERROR);

        String url = String.format(BitmovinApi.BASE_URL + ENDPOINT_START_ENCODING, manifestId);

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(url)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(
                        withStatus(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(response))
                );

        bitmovinApi.startEncoding(manifestId, encodingId);
    }

    @Test(expected = HttpClientErrorException.class)
    public void Given_MockingByMockRestServiceServer_When_StartEncodingIsCalledAndRequestIsError_Then_ExceptionIsThrown() throws Exception {
        String manifestId = "45ef21d2-1f62-4004-9166-6d5e248270f6";
        String encodingId = "f3177c2e-0000-4ba6-bd20-1dee353d8a72";

        String url = String.format(BitmovinApi.BASE_URL + ENDPOINT_START_ENCODING, manifestId);

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(url)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.BAD_REQUEST));

         bitmovinApi.startEncoding(manifestId, encodingId);
    }

}
