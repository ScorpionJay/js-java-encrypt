package org.demo.util;
import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

/**
 *
 * @author jay
 * @time Dec 29, 20154:20:53 PM
 *
 */
public class ThreeDES {

    private static String str = "中国";

    public static void main(String[] args) {
        jdk3DES();
    }

    public static void jdk3DES() {

        try {
            // 生成key
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DESede");
            keyGenerator.init(new SecureRandom());
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] bytesKey = secretKey.getEncoded();

//             System.out.println("密钥 ： " + new String(bytesKey) );
            System.out.println("key : " + Base64.encodeBase64String(bytesKey));

            // key转换
            DESedeKeySpec deSedeKeySpec = new DESedeKeySpec(Base64.encodeBase64String(bytesKey).getBytes());
            SecretKeyFactory factory = SecretKeyFactory.getInstance("DESede");
            Key convertSecretKey = factory.generateSecret(deSedeKeySpec);

            // 加密
            Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, convertSecretKey);
            byte[] result = cipher.doFinal(str.getBytes());
            // System.out.println("encrpt : " + Hex.encodeHexString(result));
            System.out.println("encrpt : " + Base64.encodeBase64String(result));

            // 解密
            cipher.init(Cipher.DECRYPT_MODE, convertSecretKey);
            result = cipher.doFinal(Base64.decodeBase64(Base64.encodeBase64String(result).getBytes()));
            System.out.println("decrypt : " + new String(result));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}