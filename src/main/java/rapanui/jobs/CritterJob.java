package rapanui.jobs;

import critterrepos.beans.options.OptionPurchaseBean;
import oahu.financial.critters.Critter;
import oahu.financial.html.ETransaction;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import rapanui.runner.RapanuiRunner;
import rapanui.service.Mail;

import java.util.List;

public class CritterJob implements Job {

    @Override
    @SuppressWarnings("unchecked")
    public void execute(JobExecutionContext context) throws JobExecutionException {

        JobDataMap jm = context.getMergedJobDataMap();

        RapanuiRunner jobContext = (RapanuiRunner)jm.get("jobContext");

        List<OptionPurchaseBean> purchases = jobContext.getCritterRepos().fetchCritters(jobContext.getPurchaseType());

        for (OptionPurchaseBean p : purchases) {
            if (p.getCritters().size() == 0) {
                continue;
            }

            List<Critter> crits = p.acceptedForSale();
            if (crits.size() > 0) {
                ETransaction transaction = jobContext.getEtransaction(); //(ETransaction) jm.get("etransaction");
                for (Critter cr : crits) {

                    transaction.sellPurchase(p,cr,jobContext.isTest());

                    jobContext.getDbService().registerCritterSale(p,cr);

                    String subject = String.format("Option sale %s",p.getOptionName());
                    jobContext.getMail().sendMail(subject,"");
                }
            }
        }

        jobContext.getEtradeRepository().invalidateCache();
    }
}
/*

  (defn test-sell-option [^OptionPurchaseBean opx
                        ^CritterBean critter]
    (let [opname (.getOptionName opx)
        d (.getDerivativePrice opx)
        price (-> d .get .getBuy)
        volume (.getSellVolume critter)]
        ;page (get-check-calc-page opname price volume true)]
    (LOG/warn
      (str "[sell-option] Test run mode, will not execute transaction for ticker: " (.getTicker opx)
        ", " (R/whoami critter)))
    (logout)))

(defn sell-option [^OptionPurchaseBean opx
                   ^CritterBean critter]
  (let [opname (.getOptionName opx)
        d (.getDerivativePrice opx)
        price (-> d .get .getBuy)
        volume (.getSellVolume critter)
        page (get-check-calc-page opname price volume true)]
    (confirm-transaction page)
    (logout)))
*/