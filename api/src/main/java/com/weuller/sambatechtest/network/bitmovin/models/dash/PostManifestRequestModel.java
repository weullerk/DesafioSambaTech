package com.weuller.sambatechtest.network.bitmovin.models.dash;

import com.weuller.sambatechtest.network.bitmovin.models.EncodingOutputModel;

public class PostManifestRequestModel {

    private String name;
    private String manifestName;
    private EncodingOutputModel outputs;

    public PostManifestRequestModel(String name, String manifestName, EncodingOutputModel outputs) {
        this.name = name;
        this.manifestName = manifestName;
        this.outputs = outputs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManifestName() {
        return manifestName;
    }

    public void setManifestName(String manifestName) {
        this.manifestName = manifestName;
    }

    public EncodingOutputModel getOutputs() {
        return outputs;
    }

    public void setOutputs(EncodingOutputModel outputs) {
        this.outputs = outputs;
    }
}
