package com.yoogurt.kettle.service.impl;

import com.yoogurt.kettle.beans.AuthorizedUser;
import com.yoogurt.kettle.beans.SyncRecord;
import com.yoogurt.kettle.config.EnvConfig;
import com.yoogurt.kettle.enums.StatusCode;
import com.yoogurt.kettle.form.SpoonForm;
import com.yoogurt.kettle.service.KettleService;
import com.yoogurt.kettle.service.SyncRecordService;
import com.yoogurt.kettle.service.UserService;
import com.yoogurt.kettle.vo.DBSetting;
import com.yoogurt.kettle.vo.ResponseObj;
import lombok.extern.slf4j.Slf4j;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.job.Job;
import org.pentaho.di.job.JobMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class KettleServiceImpl implements KettleService {

    @Autowired
    private SyncRecordService recordService;

    @Autowired
    private UserService userService;

    @Autowired
    private EnvConfig envConfig;

    @PostConstruct
    public void kettleInit() throws KettleException {
        KettleEnvironment.init();
    }

    @Override
    public JobMeta getJobMeta(Resource resource) {
        Assert.notNull(resource, "请指定作业文件");
        Assert.isTrue(resource.exists(), "请指定作业文件");
        try {
            return new JobMeta(resource.getFile().getAbsolutePath(), null, null);
        } catch (Exception e) {
            log.error("获取JobMeta出现异常, {}", e);
            return null;
        }
    }

    @Override
    public JobMeta getJobMeta(String filePath) {
        log.info(filePath);
        try {
            return new JobMeta(filePath, null);
        } catch (Exception e) {
            log.error("获取JobMeta出现异常, {}", e);
            return null;
        }
    }

    @Override
    public ResponseObj sync(final SpoonForm form) {
        AuthorizedUser userInfo = userService.getUserInfo(form.getToken());
        if (userInfo == null) return ResponseObj.fail(StatusCode.BIZ_FAILED, "授权码不存在");
        if (!"A".equals(userInfo.getStatus())) return ResponseObj.fail(StatusCode.NO_AUTHORITY);
        SyncRecord record = recordService.record(userInfo.getUser(), userInfo.getToken(), form.getIpv4(), form.getFrom() + "@" + form.getDb(), form.getTo() + "@" + form.getDb());
        if (record == null) return ResponseObj.fail();
        final String sync = record.getSync();
        CompletableFuture.supplyAsync(() -> {
            DBSetting fromDbSetting = envConfig.getDBConfig(form.getFrom());
            DBSetting toDbSetting = envConfig.getDBConfig(form.getTo());
            if (fromDbSetting == null || toDbSetting == null) return null;
            JobMeta jobMeta = getJobMeta(new FileSystemResource(envConfig.getEntryPoint()).getPath());
            if (jobMeta == null) return null;
            Job job = new Job(null, jobMeta);
            job.setVariable("sync", sync);
            job.setVariable("TO_HOST", toDbSetting.getHost());
            job.setVariable("TO_DB", form.getDb());
            job.setVariable("TO_USER", toDbSetting.getUser());
            job.setVariable("TO_PASSWORD", toDbSetting.getPassword());
            job.setVariable("TO_PORT", toDbSetting.getPort());

            job.setVariable("FROM_HOST", fromDbSetting.getHost());
            job.setVariable("FROM_DB", form.getDb());
            job.setVariable("FROM_USER", fromDbSetting.getUser());
            job.setVariable("FROM_PASSWORD", fromDbSetting.getPassword());
            job.setVariable("FROM_PORT", fromDbSetting.getPort());
            job.start();
            job.waitUntilFinished();
            return job;
        }).thenAccept((job) -> {
            boolean hasError = false;
            if (job == null) {
                log.info("配置异常");
                hasError = true;
            } else {
                log.info("同步结果：" + job.getStatus());
            }
            SyncRecord syncRecord = recordService.getRecord(sync);
            if (syncRecord != null) {
                syncRecord.setStatus(hasError ? "F" : "S");
                recordService.save(syncRecord);
            }
        });
        return ResponseObj.success(sync);
    }
}
