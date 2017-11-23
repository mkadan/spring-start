package de.apollon.newmedia.newsquare.live.importer.aaz.service;

import de.apollon.newmedia.newsquare.live.importer.aaz.configuration.ApplicationProperties;
import de.apollon.newmedia.newsquare.live.importer.aaz.configuration.NegateFlowConfiguration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles({"test", "debug"})
@SpringBootTest
public class NegateFlowIntegrationTestCase {

    @Autowired
    private ApplicationProperties properties;

    @Autowired
    private NegateFlowConfiguration.NegateGateway gateway;

    @MockBean
    private NegateFlowService negateFlowService;

    @MockBean
    private NegateFlowConfiguration.NegateSubflowGateway subflowGateway;

    @Before
    public void before() {
        Mockito.when(negateFlowService.isNegative(Mockito.anyInt()))
                .thenReturn(true);
        Mockito.when(negateFlowService.str(Mockito.anyInt()))
                .thenReturn("-1");

        Mockito.when(subflowGateway.negateSubflow(Mockito.anyInt()))
                .thenReturn(-1);
    }

    @Test
    public void testFlowNegative() {
        Assert.assertEquals("-1", gateway.negate(-10));

        Mockito.verify(negateFlowService).isNegative(-10);
        Mockito.verify(subflowGateway).negateSubflow(-10);
        Mockito.verify(negateFlowService).str(-1);
        Mockito.verifyNoMoreInteractions(negateFlowService);
        Mockito.verifyNoMoreInteractions(subflowGateway);
    }

    @Test
    public void testFlowPositive() {
        Mockito.when(negateFlowService.isNegative(Mockito.anyInt()))
                .thenReturn(false);

        Assert.assertEquals("-1", gateway.negate(10));

        Mockito.verify(negateFlowService).isNegative(10);
        Mockito.verify(negateFlowService).str(10);
        Mockito.verifyNoMoreInteractions(negateFlowService);
        Mockito.verifyNoMoreInteractions(subflowGateway);
    }

    @Test
    public void testFlowNegateFailed() {
        Mockito.when(subflowGateway.negateSubflow(Mockito.anyInt()))
                .thenThrow(new RuntimeException("expected"));

        try {
            gateway.negate(-10);
            Assert.fail();
        } catch (RuntimeException e) {
            Assert.assertEquals("expected", e.getMessage());
        }

        Mockito.verify(negateFlowService).isNegative(-10);
        Mockito.verify(subflowGateway).negateSubflow(-10);
        Mockito.verifyNoMoreInteractions(negateFlowService);
        Mockito.verifyNoMoreInteractions(subflowGateway);
    }

    @Test
    public void testFlowStrFailed() {
        Mockito.when(negateFlowService.str(Mockito.anyInt()))
                .thenThrow(new RuntimeException("expected"));

        try {
            gateway.negate(-10);
            Assert.fail();
        } catch (RuntimeException e) {
            Assert.assertEquals("expected", e.getMessage());
        }

        Mockito.verify(negateFlowService).isNegative(-10);
        Mockito.verify(subflowGateway).negateSubflow(-10);
        Mockito.verify(negateFlowService, Mockito.times(properties.getRetryCount())).str(-1);
        Mockito.verifyNoMoreInteractions(negateFlowService);
        Mockito.verifyNoMoreInteractions(subflowGateway);
    }
}
