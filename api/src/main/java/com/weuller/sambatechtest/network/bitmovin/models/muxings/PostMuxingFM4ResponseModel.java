package com.weuller.sambatechtest.network.bitmovin.models.muxings;



public class PostMuxingFM4ResponseModel {

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
        Fmp4Muxing result;

        public Fmp4Muxing getResult() {
            return result;
        }

        public void setResult(Fmp4Muxing result) {
            this.result = result;
        }

        public static class Fmp4Muxing {
            String id;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }
        }
    }
}
