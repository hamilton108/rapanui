package rapanui.jobs;

import oahu.financial.html.ETransaction;
import oahu.financial.repository.ChachedEtradeRepository;
import rapanui.service.DbService;

public class CritterJobContext {

    private final boolean isTest;
    private final DbService critterRepos;
    private final ChachedEtradeRepository etradeRepository;
    private final int purchaseType;
    private final ETransaction etransaction;
    public CritterJobContext(DbService critterRepos,
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

    public DbService getCritterRepos() {
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
