package com.yoogurt.kettle;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.job.Job;
import org.pentaho.di.job.JobMeta;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KettleSyncBootstrapTests {

    private final static String FROM_DB_NAME = "RDS_USER";

    private final static String TO_DB_NAME = "LOCAL_USER";

    private final static String FROM_HOST = "rm-bp1cj68xrmcrqk78c.mysql.rds.aliyuncs.com";

    private final static String FROM_DB = "taxi-user";

    private final static String FROM_DB_USER = "yogurt";

    private final static String FROM_DB_PASSWORD = "taxi123!@#";

    private final static String TO_HOST = "192.168.0.222";

    private final static String TO_DB = "taxi-user";

    private final static String TO_DB_USER = "root";

    private final static String TO_DB_PASSWORD = "taxi123456";


    @Test
    public void syncTest() throws Exception {
        KettleEnvironment.init();
        Resource resource = new ClassPathResource("kettle/entrypoint.kjb");
        if (!resource.exists()) {
            throw new Exception("作业文件不存在");
        }
        JobMeta jobMeta = new JobMeta(resource.getFile().getAbsolutePath(), null);
        Job job = new Job(null, jobMeta);
        job.setVariable("TO_HOST", "192.168.0.222");
        job.setVariable("TO_DB", "taxi-user");
        job.setVariable("TO_USER", "root");
        job.setVariable("TO_PASSWORD", "taxi123456");

        job.setVariable("FROM_HOST", "rm-bp1cj68xrmcrqk78c.mysql.rds.aliyuncs.com");
        job.setVariable("FROM_DB", "taxi-user");
        job.setVariable("FROM_USER", "yoogurt");
        job.setVariable("FROM_PASSWORD", "taxi123!@#");
        job.start();
        job.waitUntilFinished();
        if (job.getErrors() > 0) {
            throw new Exception("There are errors during job exception!(执行job发生异常)");
        }
    }

}
