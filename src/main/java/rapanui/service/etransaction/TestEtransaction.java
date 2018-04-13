package rapanui.service.etransaction;

import oahu.financial.OptionPurchase;
import oahu.financial.critters.Critter;
import oahu.financial.html.ETransaction;

public class TestEtransaction extends AbstractEtransaction implements ETransaction {

    @Override
    public void sellPurchase(OptionPurchase purchase, Critter critter, boolean isTestRun) {
    }

}
