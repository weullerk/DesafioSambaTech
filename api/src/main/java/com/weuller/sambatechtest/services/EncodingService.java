package com.weuller.sambatechtest.services;

import com.weuller.sambatechtest.network.bitmovin.BitmovinApi;
import com.weuller.sambatechtest.network.bitmovin.models.AudioCodecConfigModel;
import com.weuller.sambatechtest.network.bitmovin.models.VideoCodecConfigModel;
import com.weuller.sambatechtest.network.bitmovin.models.dash.*;
import com.weuller.sambatechtest.network.bitmovin.models.encoding.PostEncodingResponseModel;
import com.weuller.sambatechtest.network.bitmovin.models.muxings.PostMuxingFM4ResponseModel;
import com.weuller.sambatechtest.network.bitmovin.models.streams.PostStreamsResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sun.plugin.dom.exception.InvalidStateException;


@Service
public class EncodingService {

    @Value("bitmovin.input_id")
    private String inputId;

    @Value("bitmovin.output_id")
    private String outputId;

    @Autowired
    BitmovinApi bitmovin;

    Logger log = LoggerFactory.getLogger(EncodingService.class);

    public EncodingService() {

    }

    public void encode(String input, String folder, String audioCodecConfigId, String videoCodecConfig) {
        Integer dotPosition = input.lastIndexOf(".");
        String inputName = dotPosition == -1 ? input : input.substring(0, dotPosition);
        input = inputName.isEmpty() ? input : inputName;

        String customPath = String.format("%s/%s/%s/%s-%s", folder, videoCodecConfig, audioCodecConfigId, inputName, Long.toString(System.currentTimeMillis()));

        String encodingId = bitmovin.createEncoding();
        String videoStreamId = bitmovin.createStreams(inputId, encodingId, input, videoCodecConfig);
        String audioStreamId = bitmovin.createStreams(inputId, encodingId, input, audioCodecConfigId);

        VideoCodecConfigModel.ResultWrapper.Result videocCodec = bitmovin.getVideoCodecConfig(videoCodecConfig);
        AudioCodecConfigModel.ResultWrapper.Result audioCodec = bitmovin.getAudioCodecConfig(audioCodecConfigId);

        String videoMuxingPath = String.format("%s/%s/%s_%s/%s/", customPath, "h264", videocCodec.getWidth().toString(), videocCodec.getBitrate().toString(), "fmp4");
        String audioMuxingPath = String.format("%s/%s/%s/", customPath, "aac", audioCodec.getBitrate().toString(), "fmp4");

        String videoMuxingId = bitmovin.createMuxingFM4(outputId, encodingId, videoStreamId, videoMuxingPath);
        String audioMuxingId = bitmovin.createMuxingFM4(outputId, encodingId, audioStreamId, audioMuxingPath);


        String manifestId = bitmovin.createManifest(outputId, customPath);
        String periodId = bitmovin.createPeriod(manifestId);

        String videoAdaptationSetId = bitmovin.createVideoAdaptationSet(manifestId, periodId);
        String audioAdaptationSetId = bitmovin.createAudioAdaptationSet(manifestId, periodId);

        String videoSegmentPath = String.format("%s/%s/%s_%s/%s", customPath, "h264", videocCodec.getWidth().toString(), videocCodec.getBitrate().toString(), "fmp4");
        String audioSegmentPath = String.format("%s/%s/%s/", customPath, "aac", audioCodec.getBitrate().toString(), "fmp4");

        bitmovin.createRepresentation(manifestId, periodId, videoAdaptationSetId, encodingId, videoMuxingId, videoSegmentPath);
        bitmovin.createRepresentation(manifestId, periodId, audioAdaptationSetId, encodingId, audioMuxingId, audioSegmentPath);
        bitmovin.startEncoding(encodingId, manifestId);
    }
}
