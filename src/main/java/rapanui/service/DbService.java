package rapanui.service;

import critterrepos.beans.options.OptionPurchaseBean;
import critterrepos.beans.options.OptionSaleBean;
import critterrepos.mybatis.CritterMapper;
import oahu.financial.DerivativePrice;
import oahu.financial.OptionPurchase;
import oahu.financial.critters.Critter;
import oahu.financial.repository.EtradeRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class DbService {
    private SqlSession session;
    private EtradeRepository etradeRepository;

    List<OptionPurchaseBean> critters;

    @Autowired
    public void setSession(SqlSession session) {
        this.session = session;
    }

    @Autowired
    public void setEtradeRepository(EtradeRepository etradeRepository) {
        this.etradeRepository = etradeRepository;
    }

    public List<OptionPurchaseBean> fetchCritters(int purchaseType) {
        if (critters == null) {
            List<OptionPurchaseBean> result =
                    session.getMapper(CritterMapper.class).activePurchasesAll(purchaseType);

            for (OptionPurchaseBean critter : result) {
                critter.setRepository(etradeRepository);
            }
            critters = result;
        }
        return critters;
    }

    public void registerCritterSale(OptionPurchase purchase, Critter critter) {
        Optional<DerivativePrice> price = purchase.getDerivativePrice();
        if (price.isPresent())  {
            OptionSaleBean sale = new OptionSaleBean(
                    purchase.getOid(),
                    price.get().getBuy(),
                    critter.getSellVolume());
            /*
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

             */
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
