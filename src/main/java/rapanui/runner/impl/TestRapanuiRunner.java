package rapanui.runner.impl;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import rapanui.jobs.CritterJob;
import rapanui.jobs.CritterJobContext;

public class TestRapanuiRunner extends AbstractRapanuiRunner {

    @Override
    protected JobDetail getJob() {
        JobDataMap jm = new JobDataMap();
        jm.put("jobContext", this);
        JobDetail job = JobBuilder.newJob(CritterJob.class)
                .withIdentity("testCritterJob", "critters")
                .usingJobData(jm)
                .build();
        return job;
    }
}

