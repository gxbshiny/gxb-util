package com.gxb.util;


import java.io.ByteArrayOutputStream;

/**
 * @Author: gxb
 * @Date: 2019/11/16 16:46
 * @Description:
 */
public final class ByteUtil {

    private ByteUtil() {

    }

    public static String bcdToString(byte[] bytes, int from, int to) {
        StringBuilder temp = new StringBuilder((to - from) * 2);
        while (from < to) {
            temp.append((bytes[from] & 0xf0) >>> 4);
            temp.append(bytes[from] & 0x0f);
            from++;
        }
        return temp.toString();
    }

    public static String bcdToString(byte[] bytes) {
        return bcdToString(bytes, 0, bytes.length);
    }

    public static byte[] stringToBcd(String s) {
        if ((s.length() & 1) == 1) s = "0" + s;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        char[] cs = s.toCharArray();
        for (int i = 0; i < cs.length; i += 2) {
            int high = cs[i] - 48;
            int low = cs[i + 1] - 48;
            baos.write(high << 4 | low);
        }
        return baos.toByteArray();
    }

    // 大端模式
    public static double bytesToDouble(byte[] bytes, int from) {
        long l = 0;
        int to = from + 8;
        while (from < to) {
            l = (l << 8) + (bytes[from] & 0xff);
            from++;
        }
        return Double.longBitsToDouble(l);
    }

    // 大端模式
    public static double bytesToDouble(byte[] bytes) {
        return bytesToDouble(bytes, 0);
    }

    // 大端模式
    public static byte[] doubleToBytes(double d) {
        byte[] b = new byte[8];
        long l = Double.doubleToRawLongBits(d);
        for (int i = 0; i < 8; i++) {
            b[i] = (byte) ((l >> (7 - i) * 8) & 0xff);
        }
        return b;
    }

    // 小端模式
    public static double bytesToDouble2(byte[] bytes, int from) {
        long l = 0;
        int to = from + 8;
        while (from < to) {
            l = (l << 8) + (bytes[7 - from] & 0xff);
            from++;
        }
        return Double.longBitsToDouble(l);
    }

    // 小端模式
    public static double bytesToDouble2(byte[] bytes) {
        return bytesToDouble2(bytes, 0);
    }

    // 小端模式
    public static byte[] doubleToBytes2(double d) {
        long l = Double.doubleToRawLongBits(d);
        byte[] b = new byte[8];
        for (int i = 0; i < 8; i++) {
            b[i] = (byte) ((l >> 8 * i) & 0xff);
        }
        return b;
    }

    public static byte[] intToBytes(int i, int n) {
        byte[] bytes = new byte[n];
        for (int j = 0; j < n; j++) {
            bytes[j] = (byte) ((i >>> ((n - 1 - j) * 8)) & 0xff);
        }
        return bytes;
    }

    public static byte[] intToBytes(int i) {
        return intToBytes(i, 4);
    }

    public static int bytesToInt(byte[] bytes, int from, int to) {
        int i = 0;
        int len = to - from;
        int start = from;
        while (from < to) {
            i += (bytes[from] & 0xff) << ((len - 1 - (from - start)) * 8);
            from++;
        }
        return i;
    }

    public static int bytesToInt(byte[] bytes) {
        return bytesToInt(bytes, 0, bytes.length);
    }

    public static long bytesToUnsignLong(byte[] bytes, int from, int to) {
        long l = 0L;
        while (from < to) {
            l = (l << 8) + (bytes[from] & 0xff);
            from++;
        }
        return l;
    }

    public static long bytesToUnsignLong(byte[] bytes) {
        return bytesToUnsignLong(bytes, 0, bytes.length);
    }

    public static byte[] hexToBytes(String hex) {
        if ((hex.length() & 1) == 1) hex = "0" + hex;
        byte[] ret = new byte[hex.length() / 2];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = (byte) ((Byte.decode("0x" + hex.charAt(i * 2)) << 4) ^ (Byte.decode("0x" + hex.charAt(i * 2 + 1))));
        }
        return ret;
    }

    public static String byteToHex(byte temp) {
        return Integer.toHexString((temp & 255) + 256).substring(1).toUpperCase();
    }

    public static String bytesToHex(byte[] bytes, int from, int to) {
        StringBuilder sb = new StringBuilder();
        while (from < to) {
            sb.append(byteToHex(bytes[from]));
            from++;
        }
        return sb.toString();
    }

    public static String bytesToHex(byte[] bytes) {
        return bytesToHex(bytes, 0, bytes.length);
    }

    public static boolean compareBytes(byte[] bytes1, byte[] bytes2) {
        if (bytes1 == null || bytes2 == null) return false;
        if (bytes1 == bytes2) return true;
        if (bytes1.length != bytes2.length) return false;
        for (int i = 0; i < bytes1.length; i++) {
            if (bytes1[i] != bytes2[i]) return false;
        }
        return true;
    }

    public static String byteToBit(byte b) {
        StringBuilder sb = new StringBuilder("00000000");
        for (int bit = 0; bit < 8; bit++) {
            if (((b >> bit) & 0x1) > 0) {
                sb.setCharAt(7 - bit, '1');
            }
        }
        return sb.toString();
    }

    public static String bytesToBit(byte[] bytes, int from, int to) {
        StringBuilder builder = new StringBuilder((to - from) * 8);
        while (from < to) {
            builder.append(byteToBit(bytes[from]));
            from++;
        }
        return builder.toString();
    }

    public static String bytesToBit(byte[] bytes) {
        return bytesToBit(bytes, 0, bytes.length);
    }

}
