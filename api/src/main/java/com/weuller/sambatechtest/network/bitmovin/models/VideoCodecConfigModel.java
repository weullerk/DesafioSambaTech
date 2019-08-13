package com.weuller.sambatechtest.network.bitmovin.models;

public class VideoCodecConfigModel {

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

        private Result result;

        public Result getResult() {
            return result;
        }

        public void setResult(Result result) {
            this.result = result;
        }

        public static class Result {

            String id;
            String name;
            Integer bitrate;
            Integer width;
            String profile;

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

            public Integer getBitrate() {
                return bitrate;
            }

            public void setBitrate(Integer bitrate) {
                this.bitrate = bitrate;
            }

            public Integer getWidth() {
                return width;
            }

            public void setWidth(Integer width) {
                this.width = width;
            }

            public String getProfile() {
                return profile;
            }

            public void setProfile(String profile) {
                this.profile = profile;
            }
        }
    }
}


