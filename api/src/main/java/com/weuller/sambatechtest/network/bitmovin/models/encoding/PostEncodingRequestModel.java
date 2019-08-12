package com.weuller.sambatechtest.network.bitmovin.models;

public class PostEncodingRequestModel {

    String name;
    String encoderVersion;

    public PostEncodingRequestModel(String name, String encoderVersion) {
        this.name = name;
        this.encoderVersion = encoderVersion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEncoderVersion() {
        return encoderVersion;
    }

    public void setEncoderVersion(String encoderVersion) {
        this.encoderVersion = encoderVersion;
    }
}
