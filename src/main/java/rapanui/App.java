package rapanui;


import critterrepos.beans.options.OptionPurchaseBean;
import org.apache.log4j.PropertyConfigurator;
import org.kohsuke.args4j.CmdLineException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import rapanui.repos.CritterRepos;
import rapanui.runner.RapanuiRunner;
import rapanui.service.CmdLineValues;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class App {
    private static Logger log = LoggerFactory.getLogger("rapanui");

    public static void main(String[] args) {
        try {
            boolean DEBUG = false;
            if (DEBUG) {
                mainDebug(args);
            } else {
                mainProd(args);
            }
        }
        catch (CmdLineException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
    private static void mainProd(String[] args) throws CmdLineException {
        initLog4j();
        CmdLineValues opts = new CmdLineValues(args);

        log.info(opts.toString());

        ApplicationContext factory = new ClassPathXmlApplicationContext(opts.getXml());

        if (opts.isCritterInfo()) {
            critterInfo(factory, opts.getPurchaseType());
        } else {
            RapanuiRunner runner;
            runner = factory.getBean("runner", RapanuiRunner.class);
            //runner.setPurchaseType(opts.getPurchaseType());
            runner.runWith(opts);
        }
    }

    private static void mainDebug(String[] args) throws CmdLineException {
        //initLog4j();
        ApplicationContext factory = new ClassPathXmlApplicationContext("rapanui.xml");
        critterInfo(factory, 11);
    }

    private static void critterInfo(ApplicationContext factory, int purchaseType) {
        System.out.println("\n\n******************** Critter Info ******************** ");
        CritterRepos critters = factory.getBean("critterRepos", CritterRepos.class);
        List<OptionPurchaseBean> purchases = critters.fetchCritters(purchaseType);
        System.out.println(String.format("Number of purchases: %d", purchases.size()));
        for (OptionPurchaseBean p : purchases) {
            p.inspect();
        }
        System.out.println("\n\n******************** Critters End ******************** ");
    }


    private static void initLog4j() {
        Properties props = new Properties();
        try {
            props.load(App.class.getResourceAsStream("/log4j.xml"));
            PropertyConfigurator.configure(props);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
