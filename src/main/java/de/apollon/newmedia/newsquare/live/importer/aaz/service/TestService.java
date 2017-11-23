package de.apollon.newmedia.newsquare.live.importer.aaz.service;

import de.apollon.newmedia.newsquare.live.importer.aaz.configuration.ApplicationProperties;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    private final ApplicationProperties configuration;
    private final TestService2 testService2;

    public TestService(
            ApplicationProperties configuration,
            TestService2 testService2) {
        this.configuration = configuration;
        this.testService2 = testService2;
    }

    public String foo() {
        return configuration.getConfigString() + "/" + testService2.boo();
    }
}
