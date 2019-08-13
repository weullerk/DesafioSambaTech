package com.weuller.sambatechtest.network.bitmovin.models;

public class ManifestResourceModel {

    private String manifestId;

    public ManifestResourceModel(String manifestId) {
        this.manifestId = manifestId;
    }

    public ManifestResourceModel() {
    }

    public String getManifestId() {
        return manifestId;
    }

    public void setManifestId(String manifestId) {
        this.manifestId = manifestId;
    }
}
