package com.weuller.sambatechtest.network.bitmovin.models;

import java.util.List;

public class EncodingOutputModel {

    private String outputId;
    private String outputPath;
    private List<AclEntryModel> acl;

    public EncodingOutputModel(String outputId, String outputPath, List<AclEntryModel> acl) {
        this.outputId = outputId;
        this.outputPath = outputPath;
        this.acl = acl;
    }

    public EncodingOutputModel() {
    }

    public String getOutputId() {
        return outputId;
    }

    public void setOutputId(String outputId) {
        this.outputId = outputId;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    public List<AclEntryModel> getAcl() {
        return acl;
    }

    public void setAcl(List<AclEntryModel> acl) {
        this.acl = acl;
    }
}

