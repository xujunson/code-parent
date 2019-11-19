package com.atu.util;


import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.IOException;
import java.security.SecureRandom;

public class DesUtil {


    private final static String DES = "DES";

    // 默认密钥，DES加密和解密过程中，密钥长度都必须是8的倍数
    public static String DEFAULT_KEY = "DJ!+LC$_Q.7T*&K~";

    /**
     * Description 根据键值进行加密
     *
     * @param data
     * @param key
     *            加密键byte数组
     * @return
     * @throws Exception
     */
    public static String encrypt(String data, String key) {
        try {
            // data.getBytes()使用平台的默认字符集将此 String 编码为 byte 序列，
            // 并将结果存储到一个新的 byte 数组中。
            byte[] bt = encrypt(data.getBytes(), key.getBytes());
            String strs = new String(Base64.encodeBase64(bt));
//            String strs = new Base64Encoder().encode(bt);
            return strs;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Description 根据键值进行解密
     *
     * @param data
     * @param key
     *            加密键byte数组
     * @return
     * @throws IOException
     * @throws Exception
     */
    public static String decrypt(String data, String key) {
        if (data == null)
            return null;
        try {
            byte[] buf = Base64.decodeBase64(data);
            byte[] bt = decrypt(buf, key.getBytes());
            return new String(bt);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Description 根据键值进行加密
     *
     * @param data
     * @param key
     *            加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();

        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);

        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象，负责保存对称密钥
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);

        // Cipher对象实际完成加密操作,指定其支持DES算法
        Cipher cipher = Cipher.getInstance(DES);

        // 用密钥初始化Cipher对象,ENCRYPT_MODE表示加密模式
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);

        // 返回密文
        return cipher.doFinal(data);
    }

    /**
     * Description 根据键值进行解密
     *
     * @param data
     * @param key
     *            加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();

        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);

        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象，负责保存对称密钥
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);

        // Cipher对象实际完成解密操作,指定其支持DES算法
        Cipher cipher = Cipher.getInstance(DES);

        // 用密钥初始化Cipher对象，DECRYPT_MODE表示解密模式
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);

        // 返回明文
        return cipher.doFinal(data);
    }

    public static void main(String[] args) throws Exception {
        String str = "123";
        System.out.println("明文是："+str);
        String enc = encrypt(str, "9aaa0e89-f1c4-4c99-8eb7-b4daf652c759");
        System.out.println("密文是："+enc);
        String dec = decrypt(enc, "9aaa0e89-f1c4-4c99-8eb7-b4daf652c759");
        System.out.println("解密后的结果是："+dec);

    }

}
