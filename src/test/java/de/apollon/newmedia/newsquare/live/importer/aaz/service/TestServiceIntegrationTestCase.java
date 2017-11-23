package de.apollon.newmedia.newsquare.live.importer.aaz.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestServiceIntegrationTestCase {

    @Autowired
    private TestService testService;

    @MockBean
    private TestService2 testService2;

    @Test
    public void testSomething() {
        Mockito.when(testService2.boo()).thenReturn("XXX");

        Assert.assertEquals("YYY/XXX", testService.foo());
    }
}
