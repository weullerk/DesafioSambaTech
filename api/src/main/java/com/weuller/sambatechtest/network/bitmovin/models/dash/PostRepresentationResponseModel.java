package com.weuller.sambatechtest.network.bitmovin.models.dash;

public class PostRepresentationResponseModel {

    private String requestId;
    private String status;
    private ResultWrapper data;

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

        private DashFmp4Representation result;

        public DashFmp4Representation getResult() {
            return result;
        }

        public void setResult(DashFmp4Representation result) {
            this.result = result;
        }

        public static class DashFmp4Representation {

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
