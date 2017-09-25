package org.demo.util;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author jay
 * @time Dec 29, 20154:59:56 PM
 *
 */
public class Encrypt3DESUtil {

    /**
     * 生成密钥
     * 
     * @return
     */
    public static String generageKey() {
        String key = "";
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DESede");
            keyGenerator.init(new SecureRandom());
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] bytesKey = secretKey.getEncoded();
            key = Base64.encodeBase64String(bytesKey);
            System.out.println("key : " + key);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return key;
    }

    /**
     * 加密
     * @param str    待加密字符串
     * @param key    密钥
     * @return
     */
    public static String encrypt(String str, String key) {
        String returnStr = "";
        try {
            // key转换
            DESedeKeySpec deSedeKeySpec = new DESedeKeySpec(Base64.decodeBase64(key));
            SecretKeyFactory factory = SecretKeyFactory.getInstance("DESede");
            Key convertSecretKey = factory.generateSecret(deSedeKeySpec);
            // 加密
            Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, convertSecretKey);
            byte[] result = cipher.doFinal(str.getBytes());
            System.out.println("encrpt : " + Base64.encodeBase64String(result));
            returnStr = Base64.encodeBase64String(result);
        } catch (Exception e) {
        	System.out.println("eee");
        }
        return returnStr;
    }

    /**
     * 解密
     * @param str    待解密字符串
     * @param key    密钥
     * @return
     */
    public static String decrypt(String str, String key) {
        String returnStr = "";
        try {
            // key转换
            DESedeKeySpec deSedeKeySpec = new DESedeKeySpec(Base64.decodeBase64(key));
            SecretKeyFactory factory = SecretKeyFactory.getInstance("DESede");
            Key convertSecretKey = factory.generateSecret(deSedeKeySpec);
            Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, convertSecretKey);
            byte[] result = cipher.doFinal(Base64.decodeBase64(str));
            System.out.println("decrypt : " + new String(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnStr;
    }

    public static void main(String[] args) {

//        String str = "hello 1111111111";
//        String key = generageKey();
//        String encryptStr = encrypt("王杰","YXNkZmFkZHdyZXdyYXNkZmFkc2Zhc2RmYXNkZg==");
//        System.out.println(encryptStr);
//        decrypt(encryptStr, key);

//        decrypt("njMj1tVuRha5ElrbAJ3CVm1QbZVBZM", "hmRSaD5MFs1ncJ30x2hDiiNRT6eXFc5D");
    	decrypt("Zjb/rTs/jy4=","RGxMdmJsbHRSbE5GT0VyY1dLc2FJY1Fl");
    }

}