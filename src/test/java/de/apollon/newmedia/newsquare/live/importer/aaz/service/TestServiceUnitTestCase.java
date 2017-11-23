package de.apollon.newmedia.newsquare.live.importer.aaz.service;

import de.apollon.newmedia.newsquare.live.importer.aaz.configuration.ApplicationProperties;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

@RunWith(JUnit4.class)
public class TestServiceUnitTestCase {

    @Mock
    private TestService2 testService2;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);

        Mockito.when(testService2.boo())
                .thenReturn("XXX");
    }

    @Test
    public void testSomething() {
        ApplicationProperties properties = new ApplicationProperties();
        properties.setConfigString("foo");

        TestService testService = new TestService(
                properties,
                testService2);

        Assert.assertEquals("foo/XXX", testService.foo());

        Mockito.verify(testService2).boo();
        Mockito.verifyNoMoreInteractions(testService2);
    }
}
