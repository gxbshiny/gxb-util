package com.gxb.util;

import java.text.DecimalFormat;

/**
 * @Author: gxb
 * @Date: 2019/11/16 16:05
 * @Description:
 */
public final class MyUtil {

    private MyUtil() {

    }

    /**
     * 把八大基本类型的包装器类型转成基本类型 若为null 则返回对应基本类型的默认值
     */
    public static byte byteValue(Byte b) {
        return b == null ? 0 : b;
    }

    public static short shortValue(Short s) {
        return s == null ? 0 : s;
    }

    public static int intValue(Integer i) {
        return i == null ? 0 : i;
    }

    public static long longValue(Long l) {
        return l == null ? 0 : l;
    }

    public static boolean booleanValue(Boolean b) {
        return b != null && b;
    }

    public static float floatValue(Float f) {
        return f == null ? 0 : f;
    }

    public static double doubleValue(Double d) {
        return d == null ? 0 : d;
    }

    public static char charValue(Character c) {
        return c == null ? '\u0000' : c;
    }

    /**
     * 检测字符串是否是纯数字
     *
     * @param s 目标字符串
     * @return true 是  false 不是
     */
    public static boolean isNumeric(String s) {
        return Const.PATTERN_NUM.matcher(s).matches();
    }

    /**
     * 检测字符串是否是ipv4格式
     *
     * @param s 目标字符串
     * @return true 是  false 不是
     */
    public static boolean isIPv4(String s) {
        return Const.PATTERN_IPV4.matcher(s).matches();
    }

    /**
     * 字符串左补零
     *
     * @param s   目标字符串
     * @param len 需要补到多长 若字符串长度大于或等于这个值 则不补零
     * @return 补零后的字符串
     */
    public static String addZero(String s, int len) {
        int length = s.length();
        int num = len - length;
        if (num <= 0) return s;
        StringBuilder builder = new StringBuilder(Math.max(length, len));
        while (num > 0) {
            builder.append('0');
            num--;
        }
        builder.append(s);
        return builder.toString();
    }

    /**
     * 格式化double 控制小数的精度
     */
    private static DecimalFormat df1 = new DecimalFormat("#.0");
    private static DecimalFormat df2 = new DecimalFormat("#.00");
    private static DecimalFormat df3 = new DecimalFormat("#.000");
    private static DecimalFormat df4 = new DecimalFormat("#.0000");
    private static DecimalFormat df5 = new DecimalFormat("#.00000");
    private static DecimalFormat df6 = new DecimalFormat("#.000000");

    public static String doubleFormat1(double d) {
        String format = df1.format(d);
        return format.charAt(0) == '.' ? ('0' + format) : format;
    }

    public static String doubleFormat2(double d) {
        String format = df2.format(d);
        return format.charAt(0) == '.' ? ('0' + format) : format;
    }

    public static String doubleFormat3(double d) {
        String format = df3.format(d);
        return format.charAt(0) == '.' ? ('0' + format) : format;
    }

    public static String doubleFormat4(double d) {
        String format = df4.format(d);
        return format.charAt(0) == '.' ? ('0' + format) : format;
    }

    public static String doubleFormat5(double d) {
        String format = df5.format(d);
        return format.charAt(0) == '.' ? ('0' + format) : format;
    }

    public static String doubleFormat6(double d) {
        String format = df6.format(d);
        return format.charAt(0) == '.' ? ('0' + format) : format;
    }

    /**
     * 把方向数字转成文字 0-360    超出范围的视为正北
     *
     * @param drct 方向 0-360
     * @return 方向的名称
     */
    public static String drctToString(int drct) {
        if (drct >= 23 && drct < 68) {
            return "东北";
        }
        if (drct >= 68 && drct < 113) {
            return "正东";
        }
        if (drct >= 113 && drct < 158) {
            return "东南";
        }
        if (drct >= 158 && drct < 203) {
            return "正南";
        }
        if (drct >= 203 && drct < 248) {
            return "西南";
        }
        if (drct >= 248 && drct < 293) {
            return "正西";
        }
        if (drct >= 293 && drct < 338) {
            return "西北";
        }
        return "正北";
    }

}


























