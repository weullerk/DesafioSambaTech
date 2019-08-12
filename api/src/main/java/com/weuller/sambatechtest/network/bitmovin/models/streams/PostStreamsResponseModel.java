package com.weuller.sambatechtest.network.bitmovin.models.streams;

import java.util.List;

public class PostStreamsResponseModel {

    String requestId;
    String status;
    ResultWrapper data;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ResultWrapper getData() {
        return data;
    }

    public void setData(ResultWrapper data) {
        this.data = data;
    }

    public static class ResultWrapper {

        Stream result;

        public Stream getResult() {
            return result;
        }

        public void setResult(Stream result) {
            this.result = result;
        }

        public static class Stream {

            String id;
            String codecConfigId;
            List<StreamInput> inputStreams;

            public Stream(String id, String codecConfigId, List<StreamInput> inputStreams) {
                this.id = id;
                this.codecConfigId = codecConfigId;
                this.inputStreams = inputStreams;
            }

            public Stream() {}

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
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

                public StreamInput() {}

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
    }

}
