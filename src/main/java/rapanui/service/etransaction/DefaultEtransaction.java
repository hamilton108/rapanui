package rapanui.service.etransaction;

import oahu.financial.OptionPurchase;
import oahu.financial.critters.Critter;
import oahu.financial.html.ETransaction;
import oahu.financial.html.WebClientManager;

public class DefaultEtransaction extends AbstractEtransaction implements ETransaction {


    // url (str "https://www.netfonds.no/account/order.php?paper=" opname ".OMFE")]

    //region Interface ETransaction
    @Override
    public void sellPurchase(OptionPurchase purchase, Critter critter) {

    }
    //endregion

    //region Local Stuff

    //endregion

    //region Properties

    //endregion Properties

}
