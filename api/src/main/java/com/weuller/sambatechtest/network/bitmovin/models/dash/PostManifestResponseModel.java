package com.weuller.sambatechtest.network.bitmovin.models.dash;

public class PostManifestResponseModel {
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
        private DashManifest result;

        public DashManifest getResult() {
            return result;
        }

        public void setResult(DashManifest result) {
            this.result = result;
        }

        public static class DashManifest {
            private String id;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }
        }
    }
}
