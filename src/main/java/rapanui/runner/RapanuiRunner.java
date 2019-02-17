package rapanui.runner;

import oahu.financial.html.ETransaction;
import oahu.financial.repository.ChachedEtradeRepository;
import oahu.financial.repository.EtradeRepository;
import rapanui.repos.CritterRepos;
import rapanui.service.CmdLineValues;
import rapanui.service.DbService;
import rapanui.service.MailService;

public interface RapanuiRunner {
    void runWith(CmdLineValues opts);
    CritterRepos getCritterRepos();
    EtradeRepository getEtradeRepository();
    int getPurchaseType();
    ETransaction getEtransaction();
    MailService getMail();
    DbService getDbService();
    boolean isTest();
    boolean isSendMail();
}
