package com.qa.util;

public class StringUtil {

    /**
     * 判断一个字符串是不是为空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        /**
         * null
         * 空字符串--》""
         * 空格字符串 -->"    "
         */
        //以下的情况都是空字符串
        //		boolean flag1 = (str == null);
        //		boolean flag2 = (str == "");
        //		boolean flag3 = (str.replace(" ", "") == "");
        //		return flag1 || flag2 || flag3;

        return ((str == null) || ("".equals(str.replace(" ", ""))));
    }

    public static void main(String[] args) {
        System.out.println(isEmpty(null));
        System.out.println(isEmpty(""));
        System.out.println(isEmpty("    "));
        System.out.println(isEmpty(" hello    "));

//		System.out.println("    ".replace(" ", "") .equals(""));
    }

}
