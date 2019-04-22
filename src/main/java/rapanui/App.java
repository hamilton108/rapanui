package rapanui;


import critterrepos.beans.options.OptionPurchaseBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import rapanui.service.CmdLineValues;
import rapanui.service.DbService;

import java.util.List;

@SpringBootApplication
@MapperScan("critterrepos.models.mybatis")
@ComponentScan({
        "critterrepos.models.impl"
        , "rapanui.service"
        , "oahu.properties"
        , "netfondsrepos.repos"
        , "netfondsrepos.downloader"
        , "netfondsrepos.webclient"
        , "vega.financial.calculator"
})
public class App implements CommandLineRunner {
    private static Logger log = LoggerFactory.getLogger("rapanui");
    private ApplicationContext context;

    @Autowired
    public void setContext(ApplicationContext context) {
        this.context = context;
    }

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    private void critterInfo(int purchaseType) {
        System.out.println("\n\n******************** Critter Info ******************** ");
        DbService critters = context.getBean(DbService.class);
        List<OptionPurchaseBean> purchases = critters.fetchCritters(purchaseType);
        System.out.println(String.format("Number of purchases: %d", purchases.size()));
        for (OptionPurchaseBean p : purchases) {
            p.inspect();
        }
        System.out.println("\n\n******************** Critters End ******************** ");
    }



    @Override
    public void run(String... args) throws Exception {
        CmdLineValues opts = new CmdLineValues(args);

        log.info(opts.toString());

        critterInfo(opts.getPurchaseType());

        if (opts.isCritterInfo()) {
            critterInfo(opts.getPurchaseType());
        } else {
            /*
            RapanuiRunner runner;
            runner = factory.getBean("runner", RapanuiRunner.class);
            runner.runWith(opts);

             */
        }

    }

}
