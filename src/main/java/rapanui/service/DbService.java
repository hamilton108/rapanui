package rapanui.service;

import critterrepos.beans.critters.CritterBean;
import critterrepos.beans.options.OptionPurchaseBean;
import critterrepos.beans.options.OptionSaleBean;
import critterrepos.models.mybatis.CritterMapper;
import critterrepos.utils.MyBatisUtils;
import oahu.financial.DerivativePrice;
import oahu.financial.OptionPurchase;
import oahu.financial.critters.Critter;

import java.util.Optional;

public class DbService {
    public void registerCritterSale(OptionPurchase purchase, Critter critter) {
        Optional<DerivativePrice> price = purchase.getDerivativePrice();
        if (price.isPresent())  {
            OptionSaleBean sale = new OptionSaleBean(
                    purchase.getOid(),
                    price.get().getBuy(),
                    critter.getSellVolume());
            MyBatisUtils.withSessionConsumer((session) -> {
                CritterMapper mapper = session.getMapper(CritterMapper.class);
                mapper.insertCritterSale(sale);
                CritterBean critterBean = (CritterBean)critter;
                critterBean.setSaleId(sale.getOid());
                mapper.registerCritterClosedWithSale(critterBean);
                critter.setStatus(9);
                if (purchase.isFullySold()) {
                    mapper.registerPurchaseFullySold(purchase);
                }
            });
        }
    }
}

/*
(defn register-critter-sale [^OptionPurchaseBean purchase, ^CritterBean critter]
        (let [^DerivativePrice d (.getDerivativePrice purchase)
        ^OptionSaleBean sale-bean (OptionSaleBean. (.getOid purchase) (-> d .get .getBuy) (.getSellVolume critter))]
        (.addSale purchase sale-bean)
        (with-session CritterMapper
        (do
        (.insertCritterSale it sale-bean)
        (.setSaleId critter (.getOid sale-bean))
        (.registerCritterClosedWithSale it critter)
        (.setStatus critter 9)
        (if (= (.isFullySold purchase) true)
        (.registerPurchaseFullySold it purchase))))))
*/
