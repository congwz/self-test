package com.viverselftest.util;


import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Haoxu.Mu on 2017/7/24.
 */
public class StrUtils extends StringUtils {

    public static boolean isEmpty(Object obj) {
        if (obj instanceof List) {
            return ((List)obj).isEmpty();
        } else if (obj instanceof Set) {
            return ((Set)obj).isEmpty();
        } else if (obj instanceof Map) {
            return ((Map)obj).isEmpty();
        }
        return obj == null || obj.toString().isEmpty();
    }

    public static boolean isEmpty(String str) {
        return str == null || str.toString().isEmpty();
    }

    public static String trim(Object str) {
        return str == null ? "": str.toString().trim();
    }

    public static String trim(String str) {
        return str == null ? "": str.toString().trim();
    }


    /*public static String randomString(int length)
    {
        if (length < 1)
        {
            return null;
        }
        Random randGen = new Random();
        char[] numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz"
                + "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
        char[] randBuffer = new char[length];
        for (int i = 0; i < randBuffer.length; i++)
        {
            randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];
            // randBuffer[i] = numbersAndLetters[randGen.nextInt(35)];
        }
        return new String(randBuffer);
    }*/

}
