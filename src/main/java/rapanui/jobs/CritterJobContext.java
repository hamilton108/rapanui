package rapanui.jobs;

import oahu.financial.html.ETransaction;
import oahu.financial.repository.ChachedEtradeRepository;
import rapanui.repos.CritterRepos;

public class CritterJobContext {

    private final boolean isTest;
    private final CritterRepos critterRepos;
    private final ChachedEtradeRepository etradeRepository;
    private final int purchaseType;
    private final ETransaction etransaction;
    public CritterJobContext(CritterRepos critterRepos,
                             ChachedEtradeRepository etradeRepository,
                             ETransaction etransaction,
                             int purchaseType,
                             boolean isTest) {
        this.critterRepos = critterRepos;
        this.etradeRepository = etradeRepository;
        this.etransaction = etransaction;
        this.purchaseType = purchaseType;
        this.isTest = isTest;
    }

    public CritterRepos getCritterRepos() {
        return critterRepos;
    }

    public ChachedEtradeRepository getEtradeRepository() {
        return etradeRepository;
    }

    public int getPurchaseType() {
        return purchaseType;
    }

    public ETransaction getEtransaction() {
        return etransaction;
    }
}
