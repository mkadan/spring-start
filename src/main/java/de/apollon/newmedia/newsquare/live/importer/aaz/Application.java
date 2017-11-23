package de.apollon.newmedia.newsquare.live.importer.aaz;

import de.apollon.newmedia.newsquare.live.importer.aaz.configuration.NegateFlowConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    private static final Log LOG = LogFactory.getLog(Application.class);

    public Application(
            NegateFlowConfiguration.NegateGateway negateGateway) {
        new Thread(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }



            LOG.info(negateGateway.negate(10));




        }).start();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
