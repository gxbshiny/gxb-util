package com.gxb.util;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Author: gxb
 * @Date: 2019/11/16 16:21
 * @Description:
 */
public final class EncryUtil {

    private EncryUtil() {

    }

    // MD5加密
    public static String getMD5(String s, Charset cs) throws NoSuchAlgorithmException {
        StringBuilder builder = new StringBuilder();
        MessageDigest digest = MessageDigest.getInstance("MD5");
        byte[] bytes = digest.digest(s.getBytes(cs));
        for (byte b : bytes) {
            int num = b & 0xFF;
            String hex = Integer.toHexString(num);
            if (hex.length() == 1) {
                builder.append("0");
            }
            builder.append(hex);
        }
        return builder.toString();
    }

    // MD5加密
    public static String getMD5(String s) throws NoSuchAlgorithmException {
        return getMD5(s, Charset.defaultCharset());
    }

    // 异或校验
    public static byte xor(byte[] bytes) {
        return xor(bytes, 0, bytes.length);
    }

    // 异或校验
    public static byte xor(byte[] bytes, int from, int to) {
        byte check = to - from > 1 ? (byte) (bytes[from] ^ bytes[from + 1]) : 0x00;
        for (int i = from + 2; i < to; i++) {
            check = (byte) (check ^ bytes[i]);
        }
        return check;
    }

}
