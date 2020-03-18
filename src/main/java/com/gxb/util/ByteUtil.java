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

    /**
     * 把BCD字节码转成字符串
     *
     * @param bytes 目标字节码
     * @param from  开始
     * @param to    结束（不包含）
     * @return 字符串
     */
    public static String bcdToString(byte[] bytes, int from, int to) {
        StringBuilder temp = new StringBuilder((to - from) * 2);
        while (from < to) {
            temp.append((bytes[from] & 0xf0) >>> 4);
            temp.append(bytes[from] & 0x0f);
            from++;
        }
        return temp.toString();
    }

    /**
     * 把整个BCD字节码转成字符串
     *
     * @param bytes 部标字节码
     * @return 字符串
     */
    public static String bcdToString(byte[] bytes) {
        return bcdToString(bytes, 0, bytes.length);
    }

    /**
     * 把字符串转成BCD字节码
     *
     * @param s 目标字符串
     * @return BCD字节码
     */
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

    /**
     * 把byte数组从指定位置开始的8位转成double
     * 大端模式 高位在前
     *
     * @param bytes 目标byte数组
     * @param from  指定开始位置
     * @return double
     */
    public static double bytesToDouble(byte[] bytes, int from) {
        long l = 0;
        int to = from + 8;
        while (from < to) {
            l = (l << 8) + (bytes[from] & 0xff);
            from++;
        }
        return Double.longBitsToDouble(l);
    }

    /**
     * 把byte数组转成double 数组长度至少为8
     * 大端模式 高位在前
     *
     * @param bytes 目标数组
     * @return double
     */
    public static double bytesToDouble(byte[] bytes) {
        return bytesToDouble(bytes, 0);
    }

    /**
     * 把double转成长度为8的byte数组
     * 大端模式 高位在前
     *
     * @param d 目标double
     * @return byte[8]
     */
    public static byte[] doubleToBytes(double d) {
        byte[] b = new byte[8];
        long l = Double.doubleToRawLongBits(d);
        for (int i = 0; i < 8; i++) {
            b[i] = (byte) ((l >> (7 - i) * 8) & 0xff);
        }
        return b;
    }

    /**
     * 把byte数组从指定位置开始的8位转成double
     * 小端模式 低位在前
     *
     * @param bytes 目标byte数组
     * @param from  指定开始位置
     * @return double
     */
    public static double bytesToDouble2(byte[] bytes, int from) {
        long l = 0;
        int to = from + 8;
        while (from < to) {
            l = (l << 8) + (bytes[7 - from] & 0xff);
            from++;
        }
        return Double.longBitsToDouble(l);
    }

    /**
     * 把byte数组转成double 数组长度至少为8
     * 小端模式 低位在前
     *
     * @param bytes 目标数组
     * @return double
     */
    public static double bytesToDouble2(byte[] bytes) {
        return bytesToDouble2(bytes, 0);
    }

    /**
     * 把double转成长度为8的byte数组
     * 小端模式 低位在前
     *
     * @param d 目标double
     * @return byte[8]
     */
    public static byte[] doubleToBytes2(double d) {
        long l = Double.doubleToRawLongBits(d);
        byte[] b = new byte[8];
        for (int i = 0; i < 8; i++) {
            b[i] = (byte) ((l >> 8 * i) & 0xff);
        }
        return b;
    }

    /**
     * 把int转成byte数组
     *
     * @param i int
     * @param n byte数组的长度
     * @return byte[n]
     */
    public static byte[] intToBytes(int i, int n) {
        byte[] bytes = new byte[n];
        for (int j = 0; j < n; j++) {
            bytes[j] = (byte) ((i >>> ((n - 1 - j) * 8)) & 0xff);
        }
        return bytes;
    }

    /**
     * 把int转成长度为4的byte数组
     *
     * @param i int
     * @return byte[4]
     */
    public static byte[] intToBytes(int i) {
        return intToBytes(i, 4);
    }

    /**
     * 把byte数组转成int
     *
     * @param bytes byte数组
     * @param from  开始位
     * @param to    结束位(不包含)
     * @return
     */
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

    /**
     * 把byte[]转成int
     *
     * @param bytes byte[]
     * @return int
     */
    public static int bytesToInt(byte[] bytes) {
        return bytesToInt(bytes, 0, bytes.length);
    }

    /**
     * 把byte[]转成无符号long
     *
     * @param bytes byte[]
     * @param from  开始位
     * @param to    结束位(不包含)
     * @return 无符号long
     */
    public static long bytesToUnsignLong(byte[] bytes, int from, int to) {
        long l = 0L;
        while (from < to) {
            l = (l << 8) + (bytes[from] & 0xff);
            from++;
        }
        return l;
    }

    /**
     * 把byte[]转成无符号long型
     *
     * @param bytes byte[]
     * @return 无符号long
     */
    public static long bytesToUnsignLong(byte[] bytes) {
        return bytesToUnsignLong(bytes, 0, bytes.length);
    }

    /**
     * 把十六进制字符串转成byte[]
     *
     * @param hex 十六进制字符串
     * @return byte[]
     */
    public static byte[] hexToBytes(String hex) {
        if ((hex.length() & 1) == 1) hex = "0" + hex;
        byte[] ret = new byte[hex.length() / 2];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = (byte) ((Byte.decode("0x" + hex.charAt(i * 2)) << 4) ^ (Byte.decode("0x" + hex.charAt(i * 2 + 1))));
        }
        return ret;
    }

    /**
     * 把byte转成十六进制字符串
     *
     * @param b byte
     * @return 十六进制字符串
     */
    public static String byteToHex(byte b) {
        return Integer.toHexString((b & 255) + 256).substring(1).toUpperCase();
    }

    /**
     * 把byte[]转成十六进制字符串
     *
     * @param bytes byte[]
     * @param from  开始位
     * @param to    结束位(不包含)
     * @return 十六进制字符串
     */
    public static String bytesToHex(byte[] bytes, int from, int to) {
        StringBuilder sb = new StringBuilder();
        while (from < to) {
            sb.append(byteToHex(bytes[from]));
            from++;
        }
        return sb.toString();
    }

    /**
     * 把byte[]转成十六进制字符串
     *
     * @param bytes byte[]
     * @return 十六进制字符串
     */
    public static String bytesToHex(byte[] bytes) {
        return bytesToHex(bytes, 0, bytes.length);
    }

    /**
     * 比较两个byte[]是否相同
     * 若两个都为null也返回false
     *
     * @param bytes1 byte[]
     * @param bytes2 byte[]
     * @return true相同 false不同
     */
    public static boolean compareBytes(byte[] bytes1, byte[] bytes2) {
        if (bytes1 == null || bytes2 == null) return false;
        if (bytes1 == bytes2) return true;
        int length = bytes1.length;
        if (length != bytes2.length) return false;
        for (int i = 0; i < length; i++) {
            if (bytes1[i] != bytes2[i]) return false;
        }
        return true;
    }

    /**
     * 判断一个长的数组中是否包含另一个短的数组 若包含则返回第一个元素的下标位置  不包含则返回-1
     *
     * @param longBytes  较长的数组
     * @param shortBytes 较短的数组
     * @return 第一个元素的下标位置 或者-1
     */
    public static int containBytes(byte[] longBytes, byte[] shortBytes) {
        if (longBytes == null || shortBytes == null) return -1;
        int longLength = longBytes.length;
        int shortLength = shortBytes.length;
        if (longLength < shortLength) return -1;
        outer:
        for (int i = 0; i < longLength - shortLength + 1; i++) {
            for (int j = 0; j < shortLength; j++) {
                if (longBytes[j + i] != shortBytes[j]) {
                    continue outer;
                }
            }
            return i;
        }
        return -1;
    }

    /**
     * 合并两个有序byte[]
     *
     * @param a   byte[]
     * @param b   byte[]
     * @param asc true为升序 false为降序
     * @return 新的有序byte[]
     */
    public static byte[] mergeBytes(byte[] a, byte[] b, boolean asc) {
        int al = a.length;
        int bl = b.length;
        byte[] bytes = new byte[al + bl];
        int i = 0, j = 0, t = 0;
        while (i < al && j < bl) {
            if (a[i] < b[j])
                bytes[t++] = asc ? a[i++] : b[j++];
            else
                bytes[t++] = asc ? b[j++] : a[i++];
        }
        while (i < al) bytes[t++] = a[i++];
        while (j < bl) bytes[t++] = b[j++];
        return bytes;
    }

    /**
     * 反转byte[]
     *
     * @param bytes byte[]
     */
    public static void reverseBytes(byte[] bytes) {
        int l = bytes.length;
        int half = l / 2;
        int i = 0;
        while (i < half) {
            byte temp = bytes[i];
            bytes[i] = bytes[l - i - 1];
            bytes[l - i - 1] = temp;
            i++;
        }
    }

    /**
     * 把byte转成二进制字符串
     *
     * @param b byte
     * @return 二进制字符串 长度为8
     */
    public static String byteToBit(byte b) {
        StringBuilder sb = new StringBuilder("00000000");
        for (int bit = 0; bit < 8; bit++) {
            if (((b >> bit) & 0x1) > 0) {
                sb.setCharAt(7 - bit, '1');
            }
        }
        return sb.toString();
    }

    /**
     * 把byte[]转成二进制字符串
     *
     * @param bytes byte[]
     * @param from  开始位
     * @param to    结束位(不包含)
     * @return 二进制字符串
     */
    public static String bytesToBit(byte[] bytes, int from, int to) {
        StringBuilder builder = new StringBuilder((to - from) * 8);
        while (from < to) {
            builder.append(byteToBit(bytes[from]));
            from++;
        }
        return builder.toString();
    }

    /**
     * 把byte[]转成二进制字符串
     *
     * @param bytes byte[]
     * @return 二进制字符串
     */
    public static String bytesToBit(byte[] bytes) {
        return bytesToBit(bytes, 0, bytes.length);
    }

}
