package de.apollon.newmedia.newsquare.live.importer.aaz.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class NegateServiceUnitTestCase {

    private NegateFlowService negateFlowService;

    @Before
    public void before() {
        negateFlowService = new NegateFlowService();
    }

    @Test
    public void testIsNegate() {
        Assert.assertEquals(false, negateFlowService.isNegative(10));
        Assert.assertEquals(true, negateFlowService.isNegative(-10));
        Assert.assertEquals(false, negateFlowService.isNegative(0));
    }

    @Test
    public void testNegate() {
        Assert.assertEquals(-10, negateFlowService.negate(10));
        Assert.assertEquals(10, negateFlowService.negate(-10));
        Assert.assertEquals(0, negateFlowService.negate(0));
    }

    @Test
    public void testStr() {
        Assert.assertEquals("10", negateFlowService.str(10));
        Assert.assertEquals("-10", negateFlowService.str(-10));
        Assert.assertEquals("0", negateFlowService.str(0));
        Assert.assertEquals("1000", negateFlowService.str(1000));
    }
}
