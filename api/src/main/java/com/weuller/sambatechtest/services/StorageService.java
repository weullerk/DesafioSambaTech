package com.weuller.sambatechtest.services;

import com.microsoft.azure.storage.blob.*;
import com.microsoft.rest.v2.RestResponse;
import io.reactivex.Single;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.FileChannel;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service
public class StorageService {

    @Autowired
    ContainerURL container;

    @Autowired
    ServiceURL service;

    public StorageService() {
    }

    public String upload(MultipartFile file) {
        try {
            String[] fileName = file.getOriginalFilename().split(".");
            fileName[0] = fileName[0] + UUID.randomUUID().toString();
            File tmpFile = File.createTempFile(fileName[0], fileName[1]);

            BlockBlobURL blob = container.createBlockBlobURL(String.join(".", fileName[0], fileName[1]));
            AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(tmpFile.toPath());

            Single<CommonRestResponse> uploadObserver = TransferManager.uploadFileToBlockBlob(fileChannel, blob, 8*1024*1024, null);
            CommonRestResponse response = uploadObserver.toFuture().get();

            return response.statusCode() >= 200 && response.statusCode() < 300 ? String.join(".", fileName[0], fileName[1]) : null;
        } catch (IOException | InterruptedException | ExecutionException e) {
            return null;
        }
    }
}
