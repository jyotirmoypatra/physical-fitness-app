package com.ashysystem.mbhq.listener;

import com.ashysystem.mbhq.roomDatabase.entity.UploadEntity;

import java.util.List;

public interface UploadDatabaseCallback {
    void loadInCompletedList(List<UploadEntity> uploadEntities);
    void onInsertCompleted();
}
