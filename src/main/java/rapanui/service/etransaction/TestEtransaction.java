package rapanui.service.etransaction;

import com.gargoylesoftware.htmlunit.Page;
import oahu.financial.OptionPurchase;
import oahu.financial.critters.Critter;
import oahu.financial.html.ETransaction;

import java.util.Optional;

public class TestEtransaction extends AbstractEtransaction implements ETransaction {

    @Override
    public Optional<Page> sellPurchase(OptionPurchase purchase, Critter critter, boolean isTestRun) {
        return Optional.empty();
    }

}
