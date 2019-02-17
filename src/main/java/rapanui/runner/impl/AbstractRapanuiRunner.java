package rapanui.runner.impl;

import oahu.financial.html.ETransaction;
import oahu.financial.repository.ChachedEtradeRepository;
import oahu.financial.repository.EtradeRepository;
import org.quartz.*;
import rapanui.repos.CritterRepos;
import rapanui.runner.RapanuiRunner;
import rapanui.runner.ShutDownScheduler;
import rapanui.service.CmdLineValues;
import rapanui.service.DbService;
import rapanui.service.MailService;

import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public abstract  class AbstractRapanuiRunner implements RapanuiRunner {
    private int purchaseType;
    private boolean isTest;
    private boolean sendMail;
    private MailService mail;
    private DbService dbService;

    private ETransaction etransaction;

    private CritterRepos critterRepos;

    private EtradeRepository etradeRepository;

    private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("H:mm");

    public void setCritterRepos(CritterRepos critterRepos) {
        this.critterRepos = critterRepos;
    }

    @Override
    public CritterRepos getCritterRepos() {
        return critterRepos;
    }

    @Override
    public EtradeRepository getEtradeRepository() {
        return etradeRepository;
    }

    public void setEtradeRepository(EtradeRepository repos) {
        this.etradeRepository = repos;
    }

    static Date toDate(String arg) {
        LocalTime lt = LocalTime.from(dtf.parse(arg));
        return toDate(lt);
    }
    private static Date toDate(LocalTime localTime) {
        Instant instant = localTime.atDate(LocalDate.now())
                .atZone(ZoneId.systemDefault()).toInstant();
        return toDate(instant);
    }

    private static Date toDate(Instant instant) {
        BigInteger milis = BigInteger.valueOf(instant.getEpochSecond()).multiply(
                BigInteger.valueOf(1000));
        milis = milis.add(BigInteger.valueOf(instant.getNano()).divide(
                BigInteger.valueOf(1_000_000)));
        return new Date(milis.longValue());
    }
    @Override
    public void runWith(CmdLineValues opts) {
        try {
            setTest(opts.isTest());
            setSendMail(opts.isMail());
            setPurchaseType(opts.getPurchaseType());
            Date fromTm = toDate(opts.getOpen());
            Date toTm = toDate(opts.getClose());

            SchedulerFactory factory = new org.quartz.impl.StdSchedulerFactory();
            Scheduler scheduler = factory.getScheduler();

            SimpleTrigger trg = TriggerBuilder.newTrigger()
                    .withIdentity("critterTrigger","critters")
                    .startAt(fromTm)
                    .endAt(toTm)
                    .withSchedule(
                            SimpleScheduleBuilder
                                    .simpleSchedule()
                                    .withIntervalInMinutes(opts.getInterval())
                                    .repeatForever())
                    .build();

            scheduler.scheduleJob(getJob(), trg);
            scheduler.getListenerManager().addSchedulerListener(new ShutDownScheduler(scheduler));
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
    protected abstract JobDetail getJob();

    @Override
    public ETransaction getEtransaction() {
        return etransaction;
    }

    public void setEtransaction(ETransaction etransaction) {
        this.etransaction = etransaction;
    }

    @Override
    public int getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(int purchaseType) {
        this.purchaseType = purchaseType;
    }

    @Override
    public boolean isTest() {
        return isTest;
    }

    public void setTest(boolean test) {
        isTest = test;
    }

    @Override
    public MailService getMail() {
        return mail;
    }

    public void setMail(MailService mail) {
        this.mail = mail;
    }

    @Override
    public DbService getDbService() {
        return dbService;
    }

    public void setDbService(DbService dbService) {
        this.dbService = dbService;
    }

    @Override
    public boolean isSendMail() {
        return sendMail;
    }

    public void setSendMail(boolean sendMail) {
        this.sendMail = sendMail;
    }
}

