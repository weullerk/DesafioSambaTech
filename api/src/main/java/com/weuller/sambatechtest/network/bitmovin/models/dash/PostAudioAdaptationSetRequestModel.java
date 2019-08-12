package com.weuller.sambatechtest.network.bitmovin.models.dash;

public class PostAudioAdaptationSetRequestModel {
    String lang;

    public PostAudioAdaptationSetRequestModel(String lang) {
        this.lang = lang;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
}
