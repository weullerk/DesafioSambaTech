package com.weuller.sambatechtest.network.bitmovin.models;

import java.util.List;

public class MessageModel {

    private String id;
    private String type;
    private String text;
    private String field;
    private List<LinkModel> links;
    private FieldMoreModel more;
    private String date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public List<LinkModel> getLinks() {
        return links;
    }

    public void setLinks(List<LinkModel> links) {
        this.links = links;
    }

    public FieldMoreModel getMore() {
        return more;
    }

    public void setMore(FieldMoreModel more) {
        this.more = more;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
