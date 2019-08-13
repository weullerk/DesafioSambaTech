package com.weuller.sambatechtest.network.bitmovin.models;

import java.util.List;

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

    public class ResponseErrorDataModel {
        private String description;
        private Integer code;
        private String message;
        private String developerMessage;
        private List<LinkModel> links;
        private List<MessageModel> details;
    }
}
