package com.gxb.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @Author: gxb
 * @Time: 2019/12/25 11:38
 * @Description: 时间工具
 */
public final class DateUtil {
    private static final Logger log = LoggerFactory.getLogger(DateUtil.class);

    private DateUtil() {

    }

    public static final DateTimeFormatter YMDHMS = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    public static final DateTimeFormatter Y_M_D_H_M_S = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter Y_M_D_T_H_M_S = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    public static final DateTimeFormatter YMD = DateTimeFormatter.ofPattern("yyyyMMdd");
    public static final DateTimeFormatter Y_M_D = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter HMS = DateTimeFormatter.ofPattern("HHmmss");
    public static final DateTimeFormatter H_M_S = DateTimeFormatter.ofPattern("HH:mm:ss");
    public static final DateTimeFormatter YMDHMSS = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
    public static final DateTimeFormatter Y_M_D_H_M_S_S = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    /**
     * @return 当前时间 yyyy-MM-dd HH:mm:ss
     */
    public static String defaultNowString() {
        return LocalDateTime.now().format(Y_M_D_H_M_S);
    }

    /**
     * @return 当前时间 yyyyMMddHHmmss
     */
    public static String defaultNowNum() {
        return LocalDateTime.now().format(YMDHMS);
    }

    /**
     * @return 今天的开始时间 0时0分0秒
     */
    public static LocalDateTime todayStart() {
        return LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
    }

    /**
     * @return 今天的结束时间 23时59分59秒
     */
    public static LocalDateTime todayEnd() {
        return LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
    }

}
