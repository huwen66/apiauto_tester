package com.qa.testcase.testDome;

import org.testng.Assert;
import org.testng.annotations.Test;

public class AssertCase {

    /**
     * case 执行失败，预期值为true，实际为false
     */
    @Test
    public void assertTrueTest() {
        // 校验值是否为true.
        Assert.assertTrue(false);
    }

    /**
     * case 执行成功,预期值和实际值都是false
     */
    @Test
    public void assertFalseTest() {
        // 判断值是否为不为false
        Assert.assertFalse(false);
    }

    /**
     * case 执行成功，预期值和实际值都为null
     */
    @Test
    public void assertNullTest() {
        // 判断值是否为为null
        Assert.assertNull(null);
    }

    /**
     * case 执行失败 预期值是非空，实际为null
     */
    @Test
    public void assertNotNullTest() {
        // 判断值是否为不为null
        Assert.assertNotNull(null);
    }

    /**
     * casse 执行失败，预期值(a)不等于实际值(b)
     */
    @Test
    public void assertEqualsTest() {

        // 判断两个值是否相等
        Assert.assertEquals("a", "b", "校验a,b值是否相等");
    }

    /**
     * casse 执行成功，预期值(a)不等于实际值(b)
     */
    @Test
    public void assertNotEqualsTest() {
        // 判断两个值是否不相等
        Assert.assertNotEquals("a", "b", "校验a,b值是否不相等");
    }

}
