package rapanui.service.etransaction;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.html.*;
import oahu.dto.Tuple4;
import oahu.financial.OptionPurchase;
import oahu.financial.critters.Critter;
import oahu.financial.html.ETransaction;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultEtransaction extends AbstractEtransaction implements ETransaction {


    // url (str "https://www.netfonds.no/account/order.php?paper=" opname ".OMFE")]

    //region Interface ETransaction
    @Override
    public void sellPurchase(OptionPurchase purchase, Critter critter, boolean isTestRun) throws IOException {

    }
    //endregion

    //region Local Stuff

    private Page checkCalcPage(String optionTicker, double price, double volume, boolean isSell) throws IOException {

        Page orderPage = getOrderPage(optionTicker);

        Tuple4<HtmlRadioButtonInput,
                HtmlTextInput,
                HtmlTextInput,
                HtmlSubmitInput> inputs = inputsFor(orderPage,true);

        inputs.item1().setChecked(true);
        inputs.item2().setAttribute("value", volume);
        inputs.item3().setAttribute("value", price);
        Page result = inputs.item4().click();
        return result;
    }

    private Page getOrderPage(String optionTicker) throws IOException {
        String url = urlFor(optionTicker);
        return webClientManager.getPage(url);
    }

    private String urlFor(String optionTicker) {
        //url (str "https://www.netfonds.no/account/order.php?paper=" opname ".OMFE")]
        return String.format("https://www.netfonds.no/account/order.php?paper=%s.OMFE", optionTicker);
    }

    private Tuple4<HtmlRadioButtonInput,
                    HtmlTextInput,
                    HtmlTextInput,
                    HtmlSubmitInput> inputsFor(Page page, boolean isSell) {

        /*
        (defn get-inputs [page is-sell]
        (let [;page (get-order-page client opx)
        form ^HtmlForm (.getElementById page "order_form")
        radio ^HtmlRadioButtonInput
                (first
                        (filter #(= (.getAttribute % "value") "limit")
                            (.getInputsByName form (if is-sell "skind" "bkind"))))
        limit ^HtmlTextInput (.getInputByName form (if is-sell "slimit" "blimit"))
        amount ^HtmlTextInput (.getInputByName form (if is-sell "snumber" "bnumber"))
        button ^HtmlSubmitInput (.getInputByName form (if is-sell "sell" "buy"))]
        {:form form
     :radio radio
     :amount amount
     :limit limit
     :button button}))
        */

        HtmlPage htmlPage = (HtmlPage)page;

        HtmlForm orderForm = (HtmlForm)htmlPage.getElementById("order_form");

        String kindName = isSell ? "skind" : "bkind";

        List<HtmlInput> inputs = orderForm.getInputsByName(kindName);
        List<HtmlInput> valueInputs = inputs.stream().filter(x -> x.getAttribute("value").equals("limit")).collect(Collectors.toList());
        HtmlRadioButtonInput radio = (HtmlRadioButtonInput)valueInputs.get(0);

        String limitName = isSell ? "slimit" : "blimit";
        HtmlTextInput limitInput = orderForm.getInputByName(limitName);

        String amountName = isSell ? "snumber" : "bnumber";
        HtmlTextInput amountInput = orderForm.getInputByName(amountName);

        String submitName = isSell ? "sell" : "buy";
        HtmlSubmitInput submitButton = orderForm.getInputByName(submitName);

        return new Tuple4<>(radio,amountInput,limitInput,submitButton);
    }

    //endregion

    //region Properties

    //endregion Properties

}
