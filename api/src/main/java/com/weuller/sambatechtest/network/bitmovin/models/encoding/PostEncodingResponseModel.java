package com.weuller.sambatechtest.network.bitmovin.models.encoding;

public class PostEncodingResponseModel {

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
        Encoding result;

        public Encoding getResult() {
            return result;
        }

        public void setResult(Encoding result) {
            this.result = result;
        }

        public static class Encoding {
            String id;
            String name;
            String encoderVersion;

            public Encoding() {}

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
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
    }
}