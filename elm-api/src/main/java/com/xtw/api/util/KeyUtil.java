package com.xtw.api.util;

import java.util.Random;

/**
 * @author : tianwen.xiao
 * @ClassName : KeyUtil
 * @Description :
 * @date : created in 2019/3/6 3:14 PM
 * @Version : 1.0
 */
public class KeyUtil {

    public static synchronized String getUniquekey() {
        Random random = new Random();

        Integer number = random.nextInt(900000)+100000;

        return System.currentTimeMillis() + String.valueOf(number);

    }
}
