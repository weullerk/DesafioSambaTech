package com.weuller.sambatechtest.network.bitmovin.models;

public class ResponseErrorModel {

    private String requestId;
    private String status;
    private ResponseErrorDataModel data;

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

    public ResponseErrorDataModel getData() {
        return data;
    }

    public void setData(ResponseErrorDataModel data) {
        this.data = data;
    }
}
