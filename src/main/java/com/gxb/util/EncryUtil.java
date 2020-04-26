package com.gxb.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @Author: gxb
 * @Date: 2019/11/16 16:21
 * @Description:
 */
public final class EncryUtil {

    private EncryUtil() {

    }

    /**
     * MD5加密
     *
     * @param s 要加密的字符串
     * @return 密文
     * @throws NoSuchAlgorithmException 无该算法异常
     */
    public static String encryMD5(String s) throws NoSuchAlgorithmException {
        StringBuilder builder = new StringBuilder();
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] bytes = md.digest(s.getBytes());
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

    /**
     * 异或校验
     *
     * @param bytes 要校验的byte[]
     * @return 校验结果
     */
    public static byte xor(byte[] bytes) {
        return xor(bytes, 0, bytes.length);
    }

    /**
     * 异或校验
     *
     * @param bytes 要校验的byte[]
     * @param from  起始位
     * @param to    结束位（不包含）
     * @return 校验结果
     */
    public static byte xor(byte[] bytes, int from, int to) {
        byte check = to - from > 1 ? (byte) (bytes[from] ^ bytes[from + 1]) : 0x00;
        for (int i = from + 2; i < to; i++) {
            check = (byte) (check ^ bytes[i]);
        }
        return check;
    }

    /**
     * 将字符串进行base64编码
     *
     * @param content 要编码的内容
     * @return base64编码
     */
    public static String encodeBase64(String content) {
        return encodeBase64(content.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 将byte[]进行base64编码
     *
     * @param bytes 要编码的byte[]
     * @return base64编码
     */
    public static String encodeBase64(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * 进行base64解码
     *
     * @param content 经过base64编码的内容
     * @return base64解码后的原内容
     */
    public static byte[] decodeBase64(String content) {
        return decodeBase64(content.getBytes());
    }

    /**
     * 进行base64解码
     *
     * @param bytes 经过base64编码的内容
     * @return base64解码后的原内容
     */
    public static byte[] decodeBase64(byte[] bytes) {
        return Base64.getDecoder().decode(bytes);
    }

    public static byte[] entryByteAes(byte[] content, String secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, getAesKey(secretKey));
        return cipher.doFinal(content);
    }

    public static byte[] decryByteAes(byte[] content, String secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, getAesKey(secretKey));
        return cipher.doFinal(content);
    }

    public static String encryAes(String content, String secretKey) throws Exception {
        return ByteUtil.bytesToHex(entryByteAes(content.getBytes(StandardCharsets.UTF_8), secretKey));
    }

    public static String decryAes(String content, String secretKey) throws Exception {
        return new String(decryByteAes(ByteUtil.hexToBytes(content), secretKey));
    }

    private static Key getAesKey(String secretKey) throws NoSuchAlgorithmException {
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(secretKey.getBytes(StandardCharsets.UTF_8));
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128, secureRandom);
        return new SecretKeySpec(kgen.generateKey().getEncoded(), "AES");
    }

    public static String gzip(String content) throws IOException {
        if (content == null || content.length() == 0) {
            return content;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip = null;
        try {
            gzip = new GZIPOutputStream(out);
            gzip.write(content.getBytes(StandardCharsets.UTF_8));
        } finally {
            if (gzip != null) {
                gzip.close();
            }
        }
        return encodeBase64(out.toByteArray());
    }

    public static String unGzip(String gzipString) throws IOException {
        if (gzipString == null) {
            return null;
        }
        ByteArrayOutputStream out = null;
        ByteArrayInputStream in = null;
        GZIPInputStream gis = null;
        String docompressed;
        try {
            out = new ByteArrayOutputStream();
            byte[] compressed = Base64.getDecoder().decode(gzipString);
            in = new ByteArrayInputStream(compressed);
            gis = new GZIPInputStream(in);
            byte[] buffer = new byte[1024];
            int offset;
            while ((offset = gis.read(buffer)) != -1) {
                out.write(buffer, 0, offset);
            }
            docompressed = out.toString();
        } finally {
            if (gis != null) {
                gis.close();
            }
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
        return docompressed;
    }

    public static byte[] encryByteSha1(byte[] content) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        digest.update(content);
        return digest.digest();
    }

    public static String encrySha1(String content) throws NoSuchAlgorithmException {
        return ByteUtil.bytesToHex(encryByteSha1(content.getBytes()));
    }

    public static String encrySha1(String content, Charset charset) throws NoSuchAlgorithmException {
        return ByteUtil.bytesToHex(encryByteSha1(content.getBytes(charset)));
    }

    public static String encry3DesCbcP5(String content, String secretKey) throws Exception {
        Key key = SecretKeyFactory.getInstance("desede").generateSecret(new DESedeKeySpec(secretKey.getBytes()));
        Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec("01234567".getBytes()));
        return encodeBase64(cipher.doFinal(content.getBytes(StandardCharsets.UTF_8)));
    }

    public static String decry3DesCbcP5(String content, String secretKey) throws Exception {
        Key key = SecretKeyFactory.getInstance("desede").generateSecret(new DESedeKeySpec(secretKey.getBytes()));
        Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec("01234567".getBytes()));
        return new String(cipher.doFinal(decodeBase64(content)), StandardCharsets.UTF_8);
    }

    public static String encryDesCbcP5(String content, String secretKey) throws Exception {
        Key key = SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(secretKey.getBytes()));
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec("12345678".getBytes()));
        return encodeBase64(cipher.doFinal(content.getBytes(StandardCharsets.UTF_8)));
    }

    public static String decryDesCbcP5(String content, String secretKey) throws Exception {
        Key key = SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(secretKey.getBytes()));
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec("12345678".getBytes()));
        return encodeBase64(cipher.doFinal(content.getBytes(StandardCharsets.UTF_8)));
    }


}
