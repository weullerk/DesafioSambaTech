package com.weuller.sambatechtest.network.bitmovin.models.muxings;

import com.weuller.sambatechtest.network.bitmovin.models.EncodingOutputModel;
import com.weuller.sambatechtest.network.bitmovin.models.MuxingStreamModel;

import java.util.List;

public class PostMuxingFM4RequestModel {

    private Integer segmentLength;
    private String segmentNaming;
    private String initSegmentName;
    private List<MuxingStreamModel> streams;
    private List<EncodingOutputModel> outputs;

    public PostMuxingFM4RequestModel() {
        this.segmentLength = 4;
        this.segmentNaming = "seg_%number%.m4s";
        this.initSegmentName = "init.mp4";
    }

    public Integer getSegmentLength() {
        return segmentLength;
    }

    public void setSegmentLength(Integer segmentLength) {
        this.segmentLength = segmentLength;
    }

    public String getSegmentNaming() {
        return segmentNaming;
    }

    public void setSegmentNaming(String segmentNaming) {
        this.segmentNaming = segmentNaming;
    }

    public String getInitSegmentName() {
        return initSegmentName;
    }

    public void setInitSegmentName(String initSegmentName) {
        this.initSegmentName = initSegmentName;
    }

    public List<MuxingStreamModel> getStreams() {
        return streams;
    }

    public void setStreams(List<MuxingStreamModel> streams) {
        this.streams = streams;
    }

    public List<EncodingOutputModel> getOutputs() {
        return outputs;
    }

    public void setOutputs(List<EncodingOutputModel> outputs) {
        this.outputs = outputs;
    }


}
