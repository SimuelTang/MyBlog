package pers.simuel.blog.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Author simuel_tang
 * @Date 2021/4/26
 * @Time 11:03
 */
public class MD5Util {
    /**
     * MD5加密类
     *
     * @param str 要加密的字符串
     * @return 加密后的字符串
     */
    public static String encrypt(String str) {
        try {
            // 传入的参数指定计算消息摘要所使用的算法，常用的有"MD5"，"SHA"等。
            MessageDigest md = MessageDigest.getInstance("MD5");
            // str为需要计算的字符串，update传入的参数是字节类型或字节类型数组，对于字符串，需要先使用getBytes( )方法生成字符串数组。
            md.update(str.getBytes());
            // 计算信息摘要
            byte[] byteDigest = md.digest();
            int i;
            StringBuilder sb = new StringBuilder("");
            for (byte b : byteDigest) {
                i = b;
                if (i < 0)
                    i += 256;
                if (i < 16)
                    sb.append("0");
                sb.append(Integer.toHexString(i));
            }
            //32位加密
            return sb.toString();
            // 16位的加密
            //return buf.toString().substring(8, 24);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(MD5Util.encrypt("Txm20000115"));
    }
}
