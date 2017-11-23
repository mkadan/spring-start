package de.apollon.newmedia.newsquare.live.importer.aaz.service;

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
public class NegateSubflowIntegrationTestCase {

    @Autowired
    private NegateFlowConfiguration.NegateSubflowGateway subflowGateway;

    @MockBean
    private NegateFlowService negateFlowService;

    @Before
    public void before() {
        Mockito.when(negateFlowService.negate(Mockito.anyInt()))
                .thenReturn(-1);
    }

    @Test
    public void testFlow() {
        Assert.assertEquals(-1, subflowGateway.negateSubflow(-10));

        Mockito.verify(negateFlowService).negate(-10);
        Mockito.verifyNoMoreInteractions(negateFlowService);
    }

    @Test
    public void testFlowNegateFailed() {
        Mockito.when(negateFlowService.negate(Mockito.anyInt()))
                .thenThrow(new RuntimeException("expected"));

        try {
            subflowGateway.negateSubflow(-10);
            Assert.fail();
        } catch (RuntimeException e) {
            Assert.assertEquals("expected", e.getMessage());
        }

        Mockito.verify(negateFlowService).negate(-10);
        Mockito.verifyNoMoreInteractions(negateFlowService);
    }
}
