package rapanui.service;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.time.format.DateTimeFormatter;

public class CmdLineValues {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");

    public CmdLineValues(String... args) throws CmdLineException {
        CmdLineParser parser = new CmdLineParser(this);
        parser.setUsageWidth(80);
        parser.parseArgument(args);

        if (help) {
            parser.printUsage(System.err);
            System.exit(0);
        }
    }

    @Option(name = "-h", aliases = { "--help" }, required = false, usage = "Print usage and quit." )
    private boolean help = false;
    @Option(name = "-x", aliases = { "--xml" }, required = false, usage = "Spring XML file name." )
    private String xml = "rapanui.xml";
    @Option(name = "-c", aliases = { "--close" }, required = false, usage = "Closing time for the market (hh:mm).")
    private String close = "17:20";
    @Option(name = "-o", aliases = { "--open" }, required = false, usage = "Opening time for the market (hh:mm).")
    private String open = "9:30";
    @Option(name = "-T", aliases = { "--test" }, required = false, usage = "Test run." )
    private boolean test = false;
    @Option(name = "-m", aliases = { "--mail" }, required = false, usage = "Email when option sale has been executed." )
    private boolean mail = false;
    @Option(name = "-C", aliases = { "--critter-info" }, required = false, usage = "Print critter info (with purchase type from -p) and quit.")
    private boolean critterInfo = false;
    @Option(name = "-p", aliases = { "--purchase-type" }, required = false, usage = "Purchase type [11: paper, 3: real time].")
    private int purchaseType = 3;
    @Option(name = "-i", aliases = { "--interval" }, required = false, usage = "Check time interval (minutes.")
    private int interval = 10;

    @Override
    public String toString() {
        return String.format(
            "\n*************************************\n-x: %s, -o: %s, -c: %s, -T: %s, -m: %s, -C: %s, -p: %d, -i: %d\n*************************************\n",
                xml, open, close, test, mail, critterInfo, purchaseType, interval
        );
    }

    public String getXml() {
        return xml;
    }

    public String getClose() {
        return close;
    }

    public String getOpen() {
        return open;
    }

    public boolean isTest() {
        return test;
    }

    public boolean isMail() {
        return mail;
    }

    public boolean isCritterInfo() {
        return critterInfo;
    }

    public int getPurchaseType() {
        return purchaseType;
    }

    public int getInterval() {
        return interval;
    }
}

