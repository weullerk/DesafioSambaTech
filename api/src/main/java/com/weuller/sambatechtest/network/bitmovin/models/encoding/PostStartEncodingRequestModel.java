package com.weuller.sambatechtest.network.bitmovin.models.encoding;

import com.weuller.sambatechtest.network.bitmovin.models.ManifestResourceModel;

import java.util.List;

public class PostStartEncodingRequestModel {

    List<ManifestResourceModel> vodDashManifests;
    String encodingMode;

    public PostStartEncodingRequestModel(List<ManifestResourceModel> vodDashManifests, String encodingMode) {
        this.vodDashManifests = vodDashManifests;
        this.encodingMode = encodingMode;
    }

    public PostStartEncodingRequestModel() {
    }

    public List<ManifestResourceModel> getVodDashManifests() {
        return vodDashManifests;
    }

    public void setVodDashManifests(List<ManifestResourceModel> vodDashManifests) {
        this.vodDashManifests = vodDashManifests;
    }

    public String getEncodingMode() {
        return encodingMode;
    }

    public void setEncodingMode(String encodingMode) {
        this.encodingMode = encodingMode;
    }
}
