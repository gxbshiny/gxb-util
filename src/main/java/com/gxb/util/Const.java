package com.gxb.util;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

/**
 * @Author: gxb
 * @Date: 2019/11/16 14:34
 * @Description:
 */
public final class Const {

    private Const() {

    }

    public static final Charset GBK = Charset.forName("GBK");
    public static final Charset UTF_8 = StandardCharsets.UTF_8;
    public static final Charset ISO_8859_1 = StandardCharsets.ISO_8859_1;
    public static final Charset DEFAULT_CHARSET = Charset.defaultCharset();


    public static final Pattern PATTERN_NUM = Pattern.compile("[0-9]*");

    public static final Pattern PATTERN_IPV4 = Pattern.compile("^(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1," +
            "2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])$");


}
