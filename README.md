# web加密


随机生成3des密钥，3des加密明文，rsa加密3des的密钥，
后台先rsa解密钥再通过密钥解密文


3des

前台js使用google的[crypto-js](https://github.com/brix/crypto-js) ,[crypto的API](https://code.google.com/archive/p/crypto-js/)

~~~js
var encrypted = CryptoJS.DES.encrypt("Message", "Secret Passphrase");
var decrypted = CryptoJS.DES.decrypt(encrypted, "Secret Passphrase");

var encrypted = CryptoJS.TripleDES.encrypt("Message", "Secret Passphrase");
var decrypted = CryptoJS.TripleDES.decrypt(encrypted, "Secret Passphrase");
~~~


这里使用Triple DES

~~~js
// js 通过base64加密  key是密钥字符串
var base64 =  CryptoJS.enc.Utf8.parse(key)
key = CryptoJS.enc.Base64.stringify(base64)
// console.log('base64:', key)
var encrypted = CryptoJS.TripleDES.encrypt(s, base64, {    
	iv:CryptoJS.enc.Utf8.parse('01234567'),    
	mode: CryptoJS.mode.CBC,  //ECB  
	padding: CryptoJS.pad.Pkcs7}
);
console.log('加密：',encrypted.toString());

var Dencrypted = CryptoJS.TripleDES.decrypt(encrypted, base64, {    
	iv:CryptoJS.enc.Utf8.parse('01234567'),    
	mode: CryptoJS.mode.CBC,    
	padding: CryptoJS.pad.Pkcs7}
);
console.log('解密：',Dencrypted.toString(CryptoJS.enc.Utf8));
~~~

key可以随机生成
~~~
// 生成3des密钥
var letter = 'abcdefghijkllmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ'
var key = ''	
for(var i=0 ;i<24; i++ ){
    key += letter.charAt(parseInt(Math.random()*letter.length, 10))
}
~~~


RSA加密
使用 [jsencrypt](https://github.com/travist/jsencrypt)

使用openssl生成公钥私钥
~~~sh
// 生成私钥
openssl genrsa -out rsa_1024_priv.pem 1024
// 查看私钥
cat rsa_1024_priv.pem
// 生成公钥
openssl rsa -pubout -in rsa_1024_priv.pem -out rsa_1024_pub.pem
//　查看公钥
cat rsa_1024_pub.pem
~~~

~~~js
var key = '待加密字符串'
var publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDQxIs7OcaCMWQnhp/75UV1j6NQhDOygTL3y+Fw0xhYf8ezLG7mQFr4MSvb4/8prnAni1XfOiV2sqpoLfLvQeMfRrlci9HemL4Xvq1bz3M40DJwWGd9xmsEHJ/4VFl3IXLd65t6/aQ+FTaWcu7WlR63uAOm3/9S7XJA1yHeQmlk/QIDAQAB"

var encrypt = new JSEncrypt()
encrypt.setPublicKey(publicKey)
var rsaKey = encrypt.encrypt(key)
console.log(rsaKey)

var privateKey = `MIICXgIBAAKBgQDQxIs7OcaCMWQnhp/75UV1j6NQhDOygTL3y+Fw0xhYf8ezLG7m
QFr4MSvb4/8prnAni1XfOiV2sqpoLfLvQeMfRrlci9HemL4Xvq1bz3M40DJwWGd9
xmsEHJ/4VFl3IXLd65t6/aQ+FTaWcu7WlR63uAOm3/9S7XJA1yHeQmlk/QIDAQAB
AoGBAIPL8RI80UOUJuSFKy79eTJUsge8zl5dDm49ul9eBTqpwMsuu/yEmGmjsUqq
z4Xi6E8uudGphck/1jDbAUlm0RjjORjz38y81XYj4x2v2wTAXlVCGMwfckD9V+FK
qsiMlEfAYenvaulDpFVgaXbWDBH3a/MamLeubt8ZCBlHkaFJAkEA9gVPnYWatlno
nlIuqHq/OBMVw4IYmiHheKo+8owWkfRSYCrXU0WUXuR54a5orKO8AP7SMjpWkQfy
PPpsmhQUmwJBANk8ZXR8pbdvz3wSQOvdFeSt7Xgi7AVgaOuORzt9MUrSHGOYRjOt
OiLwG7il8gnGlOHpLpRHOui7J8IEx6cm6kcCQGfRhe9rWgZpmoh1FctzQQ5gv2Zp
mAgzE/rfnQCtrTiaaci7S3KQgjQpJmXeO73euo+Q+RLJG47IU6j35KyAnNECQQDM
9ikbMQgB/SKcg3RxldU0P84QTZK2t/IGXeVjgYlieL+EUIXb+BMkJ7tgCmJFSvHp
/GKQUGyKbRrj+jKNyS+9AkEAifdlEVgXwOBf5+r56Jm5fbwXB+aONqVq3a4zO3Nf
DjneeJZp+8N0UxXdZiOKobrSWFZZRucifjzrAd7PM0TRdw==`

decrypt.setPrivateKey(privateKey);
var uncrypted = encrypt.decrypt(encrypted);
console.log(uncrypted)
~~~


前台先随机生成一个24位key，js的3des加密的密钥是key的base64 32位值base64Key，通过base64Key对明文加密，当然这里的明文是密码可以先md5或者sha，然后使用rsa的公钥对key进行加密，将加密的key和密文发送到后台，将key，base64Key设置null。
接下来就是后台java拿到明文通过3des加密的密文，和通过rsa加密随机生成的3des密钥的密文进行解密。

java 3des解密
~~~
/**
 * 解密
 * @param str    待解密字符串
 * @param key    密钥(base64）
 * @return
 */
public static String decrypt(String str, String key) {
    String returnStr = "";
    try {
        // key转换
        DESedeKeySpec deSedeKeySpec = new DESedeKeySpec(Base64.decodeBase64(key));
        SecretKeyFactory factory = SecretKeyFactory.getInstance("DESede");
        Key convertSecretKey = factory.generateSecret(deSedeKeySpec);
        Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        // 用密匙初始化Cipher对象
        IvParameterSpec param = new IvParameterSpec("01234567".getBytes());
        cipher.init(Cipher.DECRYPT_MODE, convertSecretKey,param);
        byte[] result = cipher.doFinal(Base64.decodeBase64(str));
        System.out.println("decrypt : " + new String(result));
    } catch (Exception e) {
        e.printStackTrace();
    }
    return returnStr;
}
~~~

ras解密
~~~
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
~~~

注意，这里的私钥需要转换
~~~
openssl pkcs8 -topk8 -in rsa_1024_priv.pem -out pkcs8_rsa_1024_priv.pem -nocrypt
cat pkcs8_rsa_1024_priv.pem
~~~


demo
~~~
TT4BQAwFKTuWQAxjQCjl/5CO6drLBwiCUT45DObj+rCx/6xCIxhH6+s2uFCpT8nH/lw21m+YPQxiWrOLxkkflxXAblZKMcBsx4CK63Iy8VHcNcLD6qkym3EKrJrEpchFT5hmEqkyBxzogkCRlUKSCqeOIBx5rytED7xCw116AhI= 

VSKLlt0FudIywhe6/Zl47A==

java解密内容
decrypt : 加密！！！
~~~