package com.yoogurt.kettle.service;

import com.yoogurt.kettle.form.SpoonForm;
import com.yoogurt.kettle.vo.ResponseObj;
import org.pentaho.di.job.JobMeta;
import org.springframework.core.io.Resource;

public interface KettleService {

    JobMeta getJobMeta(Resource resource);

    JobMeta getJobMeta(String filePath);

    ResponseObj sync(SpoonForm form);
}
