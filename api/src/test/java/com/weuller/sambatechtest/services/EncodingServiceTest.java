package com.weuller.sambatechtest.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EncodingServiceTest {

    @Autowired
    EncodingService encodingService;

    @Before
    public void init() {}

    @Test
    public void Given_FileInStorageAndConfigurations_When_EncodingIsCalled_Then_FileIsEncodedWithSuccess() throws Exception {
        String inputFile = "teste.mkv";
        String folder = "test";
        String audioCodecId = "8161a341-2f1f-4a91-a454-a2a64d6246de";
        String videoCodecId = "3770cc0a-de10-4387-9891-db0c65a23344";

        String encodingId = encodingService.encode(inputFile, folder, audioCodecId, videoCodecId);

        Assert.assertNotNull(encodingId);
    }

}
