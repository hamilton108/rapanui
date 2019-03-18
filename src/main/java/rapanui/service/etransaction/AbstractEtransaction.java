package rapanui.service.etransaction;

import com.gargoylesoftware.htmlunit.Page;
import oahu.financial.html.WebClientManager;
import rapanui.service.MailService;

public abstract class AbstractEtransaction {
    WebClientManager<Page> webClientManager;

    //region Properties
    public void setWebClientManager(WebClientManager webClientManager) {
        this.webClientManager = webClientManager;
    }

    /*
    public void setMail(MailService mail) {
        this.mail = mail;
    }

    public MailService getMail() {
        return mail;
    }
    */
    //endregion Properties
}
