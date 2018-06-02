package com.yoogurt.kettle.utils;

import org.joda.time.DateTime;

import java.util.Random;
import java.util.UUID;

/**
 * 随机数生成工具类
 *
 * @author Eric Lau
 * @Date 2017年8月11日
 */
public class RandomUtils {

    public static String[] letters = new String[]{"a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"};


    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String getRandomLetters(int num) {
        if (num <= 0) return "";
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < num; i++) {
            Random r = new Random();
            builder.append(letters[r.nextInt(letters.length)]);
        }
        return builder.toString();
    }

    public static String getFixLenthString(int strLength) {

        Random rm = new Random();
        // 获得随机数
        double val = (1 + rm.nextDouble()) * Math.pow(10, strLength);
        // 将获得的获得随机数转化为字符串
        String fix = String.valueOf(val);
        // 返回固定的长度的随机数
        return fix.substring(1, strLength + 1);
    }


    /**
     * 获取指定位数的随机数
     *
     * @param charCount 指定多少位
     * @return 指定位数的随机数
     */
    public static String getRandNum(int charCount) {
        StringBuilder charValue = new StringBuilder();
        Random r = new Random();
        for (int i = 0; i < charCount; i++) {
            // 取大于等于0小于10的整数
            charValue.append(r.nextInt(10));
        }
        return charValue.toString();
    }

    /**
     * 生成主键ID
     *
     * @return 主键ID，String类型
     */
    public static String getPrimaryKey() {
        DateTime dateTime = new DateTime();
        return dateTime.toString("yyMMddHHmmss") + RandomUtils.getRandNum(3);
    }

}
