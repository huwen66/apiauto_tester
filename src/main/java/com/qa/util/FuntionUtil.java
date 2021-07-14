package com.qa.util;

import java.util.Random;

/**
 * @ClassName FuntionUtil
 * @author: vinson.hu
 * @Description TODO
 * @date 2021/1/15 17:18
 * @Version 1.0版本
 */
public class FuntionUtil {
    public static final String ALLCHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String PHONE[] ={"133","137","138","155","158"};
    /**
     * 生成3位随机字符串
     * @return
     */
    public static String RandomStr(){
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            sb.append(ALLCHAR.charAt(random.nextInt(ALLCHAR.length())));
        }
        return sb.toString();
    }

    /**
     * 随机生成手机号
     * @return
     */
    public static String RandomPhone(){
        StringBuffer sb = new StringBuffer();
        int size = PHONE.length;
        sb.append(PHONE[(int) (Math.random()*size)]);
        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            sb.append(random.nextInt(8));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(RandomPhone());
    }
}
