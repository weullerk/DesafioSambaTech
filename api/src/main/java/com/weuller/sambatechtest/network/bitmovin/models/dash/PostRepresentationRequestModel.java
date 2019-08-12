package com.weuller.sambatechtest.network.bitmovin.models.dash;

public class PostRepresentationRequestModel {

    String type;
    String encodingId;
    String muxingId;
    String segmentPath;

    public PostRepresentationRequestModel(String type, String encodingId, String muxingId, String segmentPath) {
        this.type = type;
        this.encodingId = encodingId;
        this.muxingId = muxingId;
        this.segmentPath = segmentPath;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEncodingId() {
        return encodingId;
    }

    public void setEncodingId(String encodingId) {
        this.encodingId = encodingId;
    }

    public String getMuxingId() {
        return muxingId;
    }

    public void setMuxingId(String muxingId) {
        this.muxingId = muxingId;
    }

    public String getSegmentPath() {
        return segmentPath;
    }

    public void setSegmentPath(String segmentPath) {
        this.segmentPath = segmentPath;
    }
}
