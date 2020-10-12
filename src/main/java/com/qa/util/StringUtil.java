package com.qa.util;

public class StringUtil {

    /**
     * 判断一个字符串是不是为空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return ((str == null) || ("".equals(str.replace(" ", ""))));
    }

    public static void main(String[] args) {
        System.out.println(isEmpty(null));
        System.out.println(isEmpty(""));
        System.out.println(isEmpty("    "));
        System.out.println(isEmpty(" hello    "));
    }

}
