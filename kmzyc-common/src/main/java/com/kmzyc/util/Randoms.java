package com.kmzyc.util;

import org.springframework.util.Assert;

import java.util.Date;
import java.util.Random;

/**
 * Created by min on 2016/11/22.
 */
public class Randoms {

    /**
     * 生成随机数字串
     *
     * @param length 生成长度
     * @return
     */
    public static String randomNumber(int length) {
        Assert.isTrue(length > 0, "Random Number's length must be greater than 0 ");
        Random rnum = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int rint = rnum.nextInt();
            if (Integer.MIN_VALUE == rint) rint = i;
            int index = Math.abs(rint) % 10;
            sb.append(index);
        }
        return sb.toString();
    }


}
