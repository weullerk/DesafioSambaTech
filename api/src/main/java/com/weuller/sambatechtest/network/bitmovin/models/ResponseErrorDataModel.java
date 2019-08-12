package com.weuller.sambatechtest.network.bitmovin.models;

import java.util.List;

public class ResponseErrorDataModel {
    private String description;
    private Integer code;
    private String message;
    private String developerMessage;
    private List<LinkModel> links;
    private List<MessageModel> details;
}
