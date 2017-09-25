package org.demo.util;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author jay
 * @time Dec 29, 20152:45:57 PM
 *
 */
public class EncryptRSAUtil {

    /**
     * 生成密钥对
     * @return
     */
    public static Map<String, String> generateKeyPair(){
        Map<String, String> map = new HashMap<String, String>();

        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(512);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();

            String publicKey = Base64.encodeBase64String(rsaPublicKey.getEncoded());
            String privateKey = Base64.encodeBase64String(rsaPrivateKey.getEncoded());

//            System.out.println( "public key : " + publicKey );
//            System.out.println( "private key : " + privateKey );

            map.put("publicKey", publicKey );
            map.put("privateKey", privateKey );
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return map;
    }

    /**
     * 通过公钥加密
     * @param str
     * @param publicKey
     * @return
     */
    public static String encrypt(String str,String rsaPublicKey){
        String returnStr = "";

        try {
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec( Base64.decodeBase64(rsaPublicKey) );
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] result = cipher.doFinal(str.getBytes());
            returnStr = Base64.encodeBase64String(result);
        } catch (Exception e) {
            e.printStackTrace();
        } 

        return returnStr;
    }

    /**
     * 通过密钥解密
     * @param str
     * @param rsaPrivateKey
     * @return
     */
    public static String decrypt(String str,String rsaPrivateKey){
        String returnStr = "";

        try {
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec( Base64.decodeBase64(rsaPrivateKey) );
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] result = cipher.doFinal( Base64.decodeBase64(str) );
            returnStr = new String(result);
        } catch (Exception e) {
            e.printStackTrace();
        } 

        return returnStr;
    }

    public static void main(String[] args) {
//         Map<String, String> map = generateKeyPair();

//         String publicKey =  map.get("publicKey");
//         String privateKey = map.get("privateKey");
//
//         System.out.println( "Public Key : " + publicKey );
//         System.out.println( "Private Key : " + privateKey );
//
//
//         String str = "hello world";
//
//         String encryptStr = encrypt(str, publicKey);
//
//         String dencryptStr = decrypt(encryptStr, privateKey);
//
//         System.out.println( "Encrypt : " + encryptStr );
//         System.out.println( "Dencrypt : " + dencryptStr );
//
//         String dencryptStr2 = decrypt("dDPyb7f6h0ZN/t1jbdk5BpjF6b7bCgAJXr0phSJ7facP08dXRfXiZDgnDjQ5mivOwV/NcPIo7eqr4ycunWQx4g==",
//                 "MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAg8oxhsh8ZvH1ryojXI5gSk6ny0TKUVJibq7a2HuxtIH8cLPVZZD9zRCbon1uMDy6rEHlRS+7skkyoVWtU1Yx7wIDAQABAkBJ6+fLyji9JYrBLcmX6ORpyaSkh3lUE/nSS5HFS9xpepABiAUUoAv/U0w/a/ljiMUntxl9k5iPfDxm6xAnThEBAiEA0G/48jh9yxQQUL5A3Pssx1lFTifflev412cc5u7noT8CIQCh3MuOIEf0KWDt41f45GAPguz7ee2Miw7PpSNjQ0uTUQIgWvoUobywgzwdpvBzpUBomhZH3sYem/RkJ7iU7+Mk7McCIEgAcOgzQe7B7WVFCWAlLvoXcpXXQi2PujHyMtC4IclRAiEApFFDNUqmNrwIbBg0BhYFOOi4u3DoVCWPPUwptJ5eVQ0=");
//
//
//         System.out.println( "通过密钥解密 ： " + dencryptStr2);
//    		System.out.println(decrypt("IlaLUKXm8Uf5mpxZkifzVkqJu1nxq6DxOSx/JPtPSu84dwrA818ReReG/WycrezVT1C8naPd/3UStKgNLigy4g==",
//    				"MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAm1ntK9vt6eeV0okN55j+sVXysND1EO9hP1sEBkygFPpSak4V0GDTobLz/IDxWdCU7Mz1fa6XxbqeN/JvyhmwnQIDAQABAkA0qpX3SoYryDFIwOIP5BRkdmjifNS4Ow9S9T42DIAw2+wKYRueTLGoZ/VYReWRAgAxOlHPHYkfNn7nfQ7fNvdZAiEA4mRFHK5pN83AFPq32fJO92fslbSH1G81RgppNfq8niMCIQCvqy5OGqiXC4lC+IfJFs9UhfoInYk0tSslu+GwZKuCPwIgStb85eNeUx1IT2cmmfz5T95iHodEk9yDjvjoGPkfm2MCIBeqaxGQm9fFcRd5nelkTkgnyHhyqLJWGdr6hbwaw9RHAiEAtgAJilB5Iyaf1qggBsxywDIVqjMOmCibWvgUcykH2DA="));
    	//String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDQxIs7OcaCMWQnhp/75UV1j6NQhDOygTL3y+Fw0xhYf8ezLG7mQFr4MSvb4/8prnAni1XfOiV2sqpoLfLvQeMfRrlci9HemL4Xvq1bz3M40DJwWGd9xmsEHJ/4VFl3IXLd65t6/aQ+FTaWcu7WlR63uAOm3/9S7XJA1yHeQmlk/QIDAQAB";
         //String privateKey = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBANDEizs5xoIxZCeGn/vlRXWPo1CEM7KBMvfL4XDTGFh/x7MsbuZAWvgxK9vj/ymucCeLVd86JXayqmgt8u9B4x9GuVyL0d6Yvhe+rVvPczjQMnBYZ33GawQcn/hUWXchct3rm3r9pD4VNpZy7taVHre4A6bf/1LtckDXId5CaWT9AgMBAAECgYEAg8vxEjzRQ5Qm5IUrLv15MlSyB7zOXl0Obj26X14FOqnAyy67/ISYaaOxSqrPheLoTy650amFyT/WMNsBSWbRGOM5GPPfzLzVdiPjHa/bBMBeVUIYzB9yQP1X4UqqyIyUR8Bh6e9q6UOkVWBpdtYMEfdr8xqYt65u3xkIGUeRoUkCQQD2BU+dhZq2WeieUi6oer84ExXDghiaIeF4qj7yjBaR9FJgKtdTRZRe5Hnhrmiso7wA/tIyOlaRB/I8+myaFBSbAkEA2TxldHylt2/PfBJA690V5K3teCLsBWBo645HO30xStIcY5hGM606IvAbuKXyCcaU4ekulEc66LsnwgTHpybqRwJAZ9GF72taBmmaiHUVy3NBDmC/ZmmYCDMT+t+dAK2tOJppyLtLcpCCNCkmZd47vd66j5D5EskbjshTqPfkrICc0QJBAMz2KRsxCAH9IpyDdHGV1TQ/zhBNkra38gZd5WOBiWJ4v4RQhdv4EyQnu2AKYkVK8en8YpBQbIptGuP6Mo3JL70CQQCJ92URWBfA4F/n6vnombl9vBcH5o42pWrdrjM7c18OOd54lmn7w3RTFd1mI4qhutJYVllG5yJ+POsB3s8zRNF3";
    	//String privateKey = "MIICXgIBAAKBgQDQxIs7OcaCMWQnhp/75UV1j6NQhDOygTL3y+Fw0xhYf8ezLG7mQFr4MSvb4/8prnAni1XfOiV2sqpoLfLvQeMfRrlci9HemL4Xvq1bz3M40DJwWGd9xmsEHJ/4VFl3IXLd65t6/aQ+FTaWcu7WlR63uAOm3/9S7XJA1yHeQmlk/QIDAQABAoGBAIPL8RI80UOUJuSFKy79eTJUsge8zl5dDm49ul9eBTqpwMsuu/yEmGmjsUqqz4Xi6E8uudGphck/1jDbAUlm0RjjORjz38y81XYj4x2v2wTAXlVCGMwfckD9V+FKqsiMlEfAYenvaulDpFVgaXbWDBH3a/MamLeubt8ZCBlHkaFJAkEA9gVPnYWatlnonlIuqHq/OBMVw4IYmiHheKo+8owWkfRSYCrXU0WUXuR54a5orKO8AP7SMjpWkQfyPPpsmhQUmwJBANk8ZXR8pbdvz3wSQOvdFeSt7Xgi7AVgaOuORzt9MUrSHGOYRjOtOiLwG7il8gnGlOHpLpRHOui7J8IEx6cm6kcCQGfRhe9rWgZpmoh1FctzQQ5gv2ZpmAgzE/rfnQCtrTiaaci7S3KQgjQpJmXeO73euo+Q+RLJG47IU6j35KyAnNECQQDM9ikbMQgB/SKcg3RxldU0P84QTZK2t/IGXeVjgYlieL+EUIXb+BMkJ7tgCmJFSvHp/GKQUGyKbRrj+jKNyS+9AkEAifdlEVgXwOBf5+r56Jm5fbwXB+aONqVq3a4zO3NfDjneeJZp+8N0UxXdZiOKobrSWFZZRucifjzrAd7PM0TRdw==";
    	
    	//String pri = "MIICXgIBAAKBgQDQxIs7OcaCMWQnhp/75UV1j6NQhDOygTL3y+Fw0xhYf8ezLG7mQFr4MSvb4/8prnAni1XfOiV2sqpoLfLvQeMfRrlci9HemL4Xvq1bz3M40DJwWGd9xmsEHJ/4VFl3IXLd65t6/aQ+FTaWcu7WlR63uAOm3/9S7XJA1yHeQmlk/QIDAQABAoGBAIPL8RI80UOUJuSFKy79eTJUsge8zl5dDm49ul9eBTqpwMsuu/yEmGmjsUqqz4Xi6E8uudGphck/1jDbAUlm0RjjORjz38y81XYj4x2v2wTAXlVCGMwfckD9V+FKqsiMlEfAYenvaulDpFVgaXbWDBH3a/MamLeubt8ZCBlHkaFJAkEA9gVPnYWatlnonlIuqHq/OBMVw4IYmiHheKo+8owWkfRSYCrXU0WUXuR54a5orKO8AP7SMjpWkQfyPPpsmhQUmwJBANk8ZXR8pbdvz3wSQOvdFeSt7Xgi7AVgaOuORzt9MUrSHGOYRjOtOiLwG7il8gnGlOHpLpRHOui7J8IEx6cm6kcCQGfRhe9rWgZpmoh1FctzQQ5gv2ZpmAgzE/rfnQCtrTiaaci7S3KQgjQpJmXeO73euo+Q+RLJG47IU6j35KyAnNECQQDM9ikbMQgB/SKcg3RxldU0P84QTZK2t/IGXeVjgYlieL+EUIXb+BMkJ7tgCmJFSvHp/GKQUGyKbRrj+jKNyS+9AkEAifdlEVgXwOBf5+r56Jm5fbwXB+aONqVq3a4zO3NfDjneeJZp+8N0UxXdZiOKobrSWFZZRucifjzrAd7PM0TRdw==";
    	//String a = encrypt("www",  publicKey);
    	//System.out.println(a);
    	String praivateKey = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBANDEizs5xoIxZCeGn/vlRXWPo1CEM7KBMvfL4XDTGFh/x7MsbuZAWvgxK9vj/ymucCeLVd86JXayqmgt8u9B4x9GuVyL0d6Yvhe+rVvPczjQMnBYZ33GawQcn/hUWXchct3rm3r9pD4VNpZy7taVHre4A6bf/1LtckDXId5CaWT9AgMBAAECgYEAg8vxEjzRQ5Qm5IUrLv15MlSyB7zOXl0Obj26X14FOqnAyy67/ISYaaOxSqrPheLoTy650amFyT/WMNsBSWbRGOM5GPPfzLzVdiPjHa/bBMBeVUIYzB9yQP1X4UqqyIyUR8Bh6e9q6UOkVWBpdtYMEfdr8xqYt65u3xkIGUeRoUkCQQD2BU+dhZq2WeieUi6oer84ExXDghiaIeF4qj7yjBaR9FJgKtdTRZRe5Hnhrmiso7wA/tIyOlaRB/I8+myaFBSbAkEA2TxldHylt2/PfBJA690V5K3teCLsBWBo645HO30xStIcY5hGM606IvAbuKXyCcaU4ekulEc66LsnwgTHpybqRwJAZ9GF72taBmmaiHUVy3NBDmC/ZmmYCDMT+t+dAK2tOJppyLtLcpCCNCkmZd47vd66j5D5EskbjshTqPfkrICc0QJBAMz2KRsxCAH9IpyDdHGV1TQ/zhBNkra38gZd5WOBiWJ4v4RQhdv4EyQnu2AKYkVK8en8YpBQbIptGuP6Mo3JL70CQQCJ92URWBfA4F/n6vnombl9vBcH5o42pWrdrjM7c18OOd54lmn7w3RTFd1mI4qhutJYVllG5yJ+POsB3s8zRNF3";
    	String str = "0LNNGqbfpUcyl1/DYmQMZ+Lbwp01akoD/s01HZCGvoa9iMlrebLHn+Hm9asHSQHtf1amsRbBDY2kuyNL4lopyzDH5UpvPlJQ13xMrYM7PFnAuL/w4g38FeLBjYSo3Ky3UigVj6XRs6fR1OKnRm6g09LSRagEo7AInKQFgKaxHRw=";
    	String b = decrypt(str,praivateKey );
    	System.out.println(b);
    	
    	


    }


}