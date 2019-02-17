package rapanui.repos;

import critterrepos.beans.options.OptionPurchaseBean;
import critterrepos.models.mybatis.CritterMapper;
import critterrepos.utils.MyBatisUtils;
import oahu.financial.repository.EtradeRepository;

import java.util.List;

public class CritterRepos {
    private EtradeRepository etradeRepository;

    List<OptionPurchaseBean> critters;

    public List<OptionPurchaseBean> fetchCritters(int purchaseType) {
        if (critters == null) {
            List<OptionPurchaseBean> result = MyBatisUtils.withSession((session) ->
                    session.getMapper(CritterMapper.class).activePurchasesAll(purchaseType));

            for (OptionPurchaseBean critter : result) {
                critter.setRepository(etradeRepository);
            }
            critters = result;
        }
        return critters;
    }


    public void setEtradeRepository(EtradeRepository etradeRepository) {
        this.etradeRepository = etradeRepository;
    }
}
