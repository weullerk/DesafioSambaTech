package com.weuller.sambatechtest.network.bitmovin.models;

import java.util.List;

public class ResponseErrorDataModel {
    String description;
    Integer code;
    String message;
    String developerMessage;
    List<LinkModel> links;
    List<MessageModel> details;
}
