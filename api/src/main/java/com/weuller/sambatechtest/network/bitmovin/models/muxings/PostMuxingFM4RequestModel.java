package com.weuller.sambatechtest.network.bitmovin.models.muxings;

import java.util.List;

public class PostMuxingFM4RequestModel {

    Integer segmentLength;
    String segmentNaming;
    String initSegmentName;
    List<MuxingStream> streams;
    List<EncodingOutput> outputs;

    public PostMuxingFM4RequestModel() {
        this.segmentLength = 4;
        this.segmentNaming = "seg_%number%.m4s";
        this.initSegmentName = "init.mp4";
    }

    public Integer getSegmentLength() {
        return segmentLength;
    }

    public void setSegmentLength(Integer segmentLength) {
        this.segmentLength = segmentLength;
    }

    public String getSegmentNaming() {
        return segmentNaming;
    }

    public void setSegmentNaming(String segmentNaming) {
        this.segmentNaming = segmentNaming;
    }

    public String getInitSegmentName() {
        return initSegmentName;
    }

    public void setInitSegmentName(String initSegmentName) {
        this.initSegmentName = initSegmentName;
    }

    public List<MuxingStream> getStreams() {
        return streams;
    }

    public void setStreams(List<MuxingStream> streams) {
        this.streams = streams;
    }

    public List<EncodingOutput> getOutputs() {
        return outputs;
    }

    public void setOutputs(List<EncodingOutput> outputs) {
        this.outputs = outputs;
    }

    public static class MuxingStream {
        String streamId;

        public MuxingStream(String streamId) {
            this.streamId = streamId;
        }

        public MuxingStream() {
        }

        public String getStreamId() {
            return streamId;
        }

        public void setStreamId(String streamId) {
            this.streamId = streamId;
        }
    }

    public static class EncodingOutput {
        String outputId;
        String outputPath;
        List<AclEntry> acl;

        public EncodingOutput(String outputId, String outputPath, List<AclEntry> acl) {
            this.outputId = outputId;
            this.outputPath = outputPath;
            this.acl = acl;
        }

        public EncodingOutput() {
        }

        public String getOutputId() {
            return outputId;
        }

        public void setOutputId(String outputId) {
            this.outputId = outputId;
        }

        public String getOutputPath() {
            return outputPath;
        }

        public void setOutputPath(String outputPath) {
            this.outputPath = outputPath;
        }

        public List<AclEntry> getAcl() {
            return acl;
        }

        public void setAcl(List<AclEntry> acl) {
            this.acl = acl;
        }

        public static class AclEntry {

            String permission;

            public AclEntry(String permission) {
                this.permission = permission;
            }

            public AclEntry() {
                this.permission = "PUBLIC_READ";
            }

            public String getPermission() {
                return permission;
            }

            public void setPermission(String permission) {
                this.permission = permission;
            }
        }
    }
}
