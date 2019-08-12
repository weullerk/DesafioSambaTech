package com.weuller.sambatechtest.network;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weuller.sambatechtest.SpringTestConfig;
import com.weuller.sambatechtest.network.bitmovin.BitmovinApi;
import com.weuller.sambatechtest.network.bitmovin.models.PostEncodingResponseModel;
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
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;
import static com.weuller.sambatechtest.network.bitmovin.BitmovinApi.ENCODER_VERSION;
import static com.weuller.sambatechtest.network.bitmovin.BitmovinApi.ENCODING_NAME;

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

        resultWrapper.seResult(encoding);

        response.setData(resultWrapper);
        response.setStatus("SUCCESS");

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(BitmovinApi.BASE_URL + BitmovinApi.ENDPOINT_CREATE_ENCODING)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(
                        withStatus(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(response))
                );

        PostEncodingResponseModel encodingResponse = bitmovinApi.createEncoding();

        //Assert.assertEquals(response, encodingResponse);
        Assert.assertNotNull(encodingResponse);
        Assert.assertNotNull(encodingResponse.getData().getResult().getId());
    }

}
