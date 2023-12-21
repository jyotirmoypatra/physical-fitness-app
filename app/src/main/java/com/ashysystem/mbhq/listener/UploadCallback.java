package com.ashysystem.mbhq.listener;

import com.ashysystem.mbhq.roomDatabase.entity.UploadEntity;

public interface UploadCallback {
    void startUpload();
    void addInQueue(UploadEntity uploadEntity);
}
