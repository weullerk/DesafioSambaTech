package com.weuller.sambatechtest.network.bitmovin.models.streams;

import java.util.List;

public class PostStreamsRequestModel {

    String codecConfigId;
    List<StreamInput> inputStreams;

    public PostStreamsRequestModel(String codecConfigId, List<StreamInput> inputStreams) {
        this.codecConfigId = codecConfigId;
        this.inputStreams = inputStreams;
    }

    public String getCodecConfigId() {
        return codecConfigId;
    }

    public void setCodecConfigId(String codecConfigId) {
        this.codecConfigId = codecConfigId;
    }

    public List<StreamInput> getInputStreams() {
        return inputStreams;
    }

    public void setInputStreams(List<StreamInput> inputStreams) {
        this.inputStreams = inputStreams;
    }

    public static class StreamInput {

        String inputId;
        String inputPath;
        String selectionMode;

        public StreamInput(String inputId, String inputPath, String selectionMode) {
            this.inputId = inputId;
            this.inputPath = inputPath;
            this.selectionMode = selectionMode;
        }

        public String getInputId() {
            return inputId;
        }

        public void setInputId(String inputId) {
            this.inputId = inputId;
        }

        public String getInputPath() {
            return inputPath;
        }

        public void setInputPath(String inputPath) {
            this.inputPath = inputPath;
        }

        public String getSelectionMode() {
            return selectionMode;
        }

        public void setSelectionMode(String selectionMode) {
            this.selectionMode = selectionMode;
        }
    }
}