package com.yoogurt.kettle.service.impl;

import com.yoogurt.kettle.beans.AuthorizedUser;
import com.yoogurt.kettle.beans.SyncRecord;
import com.yoogurt.kettle.repository.SyncRecordRepository;
import com.yoogurt.kettle.service.SyncRecordService;
import com.yoogurt.kettle.service.UserService;
import com.yoogurt.kettle.utils.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SyncRecordServiceImpl implements SyncRecordService {

    @Autowired
    private SyncRecordRepository repository;

    @Override
    public SyncRecord record(String user, String token, String ipv4, String fromDb, String toDb) {
        SyncRecord record = new SyncRecord();
        record.setSync(RandomUtils.getPrimaryKey());
        record.setUser(user);
        record.setToken(token);
        record.setIpv4(ipv4);
        record.setStatus("P");
        record.setFromDb(fromDb);
        record.setToDb(toDb);
        record.setGmtCreate(new Date());
        record.setGmtModify(new Date());
        return repository.saveAndFlush(record);
    }

    @Override
    public SyncRecord getRecord(String sync) {
        return repository.findById(sync).orElse(null);
    }

    @Override
    public void save(SyncRecord record) {
        if (record != null) {
            repository.saveAndFlush(record);
        }
    }
}
