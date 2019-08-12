package com.weuller.sambatechtest.network.bitmovin.models;

public class MuxingStreamModel {

    private String streamId;

    public MuxingStreamModel(String streamId) {
        this.streamId = streamId;
    }

    public MuxingStreamModel() {
    }

    public String getStreamId() {
        return streamId;
    }

    public void setStreamId(String streamId) {
        this.streamId = streamId;
    }
}
