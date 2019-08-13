package com.weuller.sambatechtest.services;

import com.weuller.sambatechtest.network.bitmovin.BitmovinApi;
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

    public void encode(String inputPath, String audioCodecConfigId, String videoCodecConfig) {
        String customPath = String.join("/", "encoding", audioCodecConfigId, videoCodecConfig, Long.toString(System.currentTimeMillis()));

        String encodingId = bitmovin.createEncoding();

        // TODO: 12/08/2019 carregar as informações do codec para montar o output do codec de audio e video

        String videoStreamId = bitmovin.createStreams(inputId, encodingId, inputPath, videoCodecConfig);

        String audioStreamId = bitmovin.createStreams(inputId, encodingId, inputPath, audioCodecConfigId);

        String videoMuxingPath = "";
        String videoMuxingId = bitmovin.createMuxingFM4(outputId, encodingId, videoStreamId, videoMuxingPath);

        String audioMuxingPath = "";
        String audioMuxingId = bitmovin.createMuxingFM4(outputId, encodingId, audioStreamId, audioMuxingPath);

        String manifestId = bitmovin.createManifest(outputId, customPath);

        String periodId = bitmovin.createPeriod(manifestId);

        String videoAdaptationSetId = bitmovin.createVideoAdaptationSet(manifestId, periodId);

        String audioAdaptationSetId = bitmovin.createAudioAdaptationSet(manifestId, periodId);

        String videoSegmentPath = "";
        String videoRepresentationId = bitmovin.createRepresentation(manifestId, periodId, videoAdaptationSetId, encodingId, videoMuxingId, videoSegmentPath);

        String audioSegmentPath = "";
        String audioRepresentationId = bitmovin.createRepresentation(manifestId, periodId, audioAdaptationSetId, encodingId, audioMuxingId, audioSegmentPath);

        bitmovin.startEncoding(encodingId, manifestId);
    }
}
