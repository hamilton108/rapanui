package rapanui.service;

import com.gargoylesoftware.htmlunit.Page;
import oahu.functional.Procedure2;

import java.util.function.Consumer;

public class HtmlPageConsumer implements Consumer<Page>,Procedure2<Page,String> {
    @Override
    public void apply(Page page, String ticker) {
        System.out.println("Storing page for ticker: " + ticker);
    }

    @Override
    public void accept(Page page) {
        System.out.println("Storing page");
    }
}
