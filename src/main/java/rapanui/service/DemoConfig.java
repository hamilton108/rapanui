package rapanui.service;

import com.gargoylesoftware.htmlunit.Page;
import rapanui.service.DemoDownloader;
import oahu.financial.html.EtradeDownloader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.Serializable;

@Configuration
@Profile("demo")
public class DemoConfig {

    @Bean
    public EtradeDownloader<Page, Serializable> downloader() {
        return new DemoDownloader();
    }

}
