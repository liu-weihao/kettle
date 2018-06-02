package com.yoogurt.kettle.service;

import com.yoogurt.kettle.beans.SyncRecord;

public interface SyncRecordService {

    SyncRecord record(String user, String token, String ipv4, String fromDb, String toDb);

    SyncRecord getRecord(String sync);

    void save(SyncRecord record);
}
