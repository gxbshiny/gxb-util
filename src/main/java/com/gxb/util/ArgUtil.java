package com.gxb.util;

/**
 * @Author: gxb
 * @Time: 2020/4/23 15:33
 * @Description: 参数工具
 */
public final class ArgUtil {

    private ArgUtil() {

    }

    public static boolean notNull(Object... args) {
        for (Object arg : args) {
            if (arg == null) {
                return false;
            }
        }
        return true;
    }

    public static boolean notAllNull(Object... args) {
        for (Object arg : args) {
            if (arg != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * 把八大基本类型的包装器类型转成基本类型 若为null 则返回对应基本类型的默认值
     * sub 替补值  如果包装器类型为null  则使用sub替代
     */
    public static byte byteValue(Byte b) {
        return b != null ? b : 0;
    }

    public static byte byteValue(Byte b, byte sub) {
        return b != null ? b : sub;
    }

    public static short shortValue(Short s) {
        return s != null ? s : 0;
    }

    public static short shortValue(Short s, short sub) {
        return s != null ? s : sub;
    }

    public static int intValue(Integer i) {
        return i != null ? i : 0;
    }

    public static int intValue(Integer i, int sub) {
        return i != null ? i : sub;
    }

    public static long longValue(Long l) {
        return l != null ? l : 0;
    }

    public static long longValue(Long l, long sub) {
        return l != null ? l : sub;
    }

    public static boolean booleanValue(Boolean b) {
        return b != null && b;
    }

    public static boolean booleanValue(Boolean b, boolean sub) {
        return b != null ? b : sub;
    }

    public static float floatValue(Float f) {
        return f != null ? f : 0;
    }

    public static float floatValue(Float f, float sub) {
        return f != null ? f : sub;
    }

    public static double doubleValue(Double d) {
        return d != null ? d : 0;
    }

    public static double doubleValue(Double d, double sub) {
        return d != null ? d : sub;
    }

    public static char charValue(Character c) {
        return c != null ? c : '\u0000';
    }

    public static char charValue(Character c, char sub) {
        return c != null ? c : sub;
    }

    public static String stringValue(String s) {
        return s != null ? s : "";
    }

    public static String stringValue(String s, String sub) {
        return s != null ? s : sub;
    }


}
