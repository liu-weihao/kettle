package com.yoogurt.kettle.repository;

import com.yoogurt.kettle.beans.SyncRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SyncRecordRepository extends JpaRepository<SyncRecord, String> {
}
