package rapanui.runner.impl;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.springframework.stereotype.Component;
import rapanui.jobs.CritterJob;
import rapanui.jobs.CritterJobContext;

@Component
public class DefaultRapanuiRunner extends AbstractRapanuiRunner {

    @Override
    protected JobDetail getJob() {
        JobDataMap jm = new JobDataMap();
        jm.put("jobContext", this);
        JobDetail job = JobBuilder.newJob(CritterJob.class)
                .withIdentity("critterJob", "critters")
                .usingJobData(jm)
                .build();
        return job;
    }

}