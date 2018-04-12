package rapanui.jobs;

import critterrepos.beans.options.OptionPurchaseBean;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.List;

public class HelloJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("Hello from Job!");
        JobDataMap jm = context.getMergedJobDataMap();
        System.out.println(jm.get("critterRepos"));
        //List<OptionPurchaseBean> fetchCritters =
    }
}
