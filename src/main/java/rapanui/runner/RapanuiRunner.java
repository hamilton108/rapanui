package rapanui.runner;

import oahu.financial.html.ETransaction;
import oahu.financial.repository.ChachedEtradeRepository;
import rapanui.repos.CritterRepos;
import rapanui.service.CmdLineValues;
import rapanui.service.DbService;
import rapanui.service.MailService;

public interface RapanuiRunner {
    void runWith(CmdLineValues opts);
    CritterRepos getCritterRepos();
    ChachedEtradeRepository getEtradeRepository();
    int getPurchaseType();
    ETransaction getEtransaction();
    MailService getMail();
    DbService getDbService();
    boolean isTest();
}
