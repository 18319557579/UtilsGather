package com.example.utilsuser.cryptology;

import android.os.Bundle;
import android.util.Base64;

import androidx.appcompat.app.AppCompatActivity;

import com.example.utilsgather.list_guide.GuideItemEntity;
import com.example.utilsgather.list_guide.GuideSettings;
import com.example.utilsgather.logcat.LogUtil;
import com.example.utilsuser.R;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class CryptologyActivity extends AppCompatActivity {
    String plainText = "abcd123黄绍飞";

    String originKey = "11525949";  // 提供原始秘钥:长度64位,8字节
    String base64Encipher = null;

    public static String RSA_ALGORITHM = "RSA";
    byte[] RSA_publicKey = null;
    byte[] RSA_privateKey = null;
    byte[] encryptedData = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cryptology);

        GuideSettings.set(findViewById(R.id.lv_container), new GuideItemEntity[]{
                new GuideItemEntity("DES 加密", new Runnable() {
                    @Override
                    public void run() {
                        LogUtil.d("明文；" + plainText + ", 密钥: " + originKey);

                        SecretKeySpec key = new SecretKeySpec(originKey.getBytes(), "DES");

                        try {
                            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
                            cipher.init(Cipher.ENCRYPT_MODE, key);
                            byte[] encipherByte = cipher.doFinal(plainText.getBytes());

                            // 防止乱码，使用Base64编码
                            base64Encipher = Base64.encodeToString(encipherByte, Base64.DEFAULT);

                            LogUtil.d("DES 加密后（base64编码）: " + base64Encipher);

                        } catch (NoSuchAlgorithmException e) {
                            throw new RuntimeException(e);
                        } catch (NoSuchPaddingException e) {
                            throw new RuntimeException(e);
                        } catch (InvalidKeyException e) {
                            throw new RuntimeException(e);
                        } catch (IllegalBlockSizeException e) {
                            throw new RuntimeException(e);
                        } catch (BadPaddingException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }),
                new GuideItemEntity("DES 解密", new Runnable() {
                    @Override
                    public void run() {
                        SecretKeySpec key = new SecretKeySpec(originKey.getBytes(), "DES");
                        try {
                            //------------------------解密---------------------------------
                            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
                            cipher.init(Cipher.DECRYPT_MODE, key);
                            byte[] b = Base64.decode(base64Encipher, Base64.DEFAULT);
                            byte[] decipherByte = cipher.doFinal(b);
                            String decipherText = new String(decipherByte);
                            LogUtil.d("DES 解密后: " + decipherText);

                        } catch (NoSuchPaddingException e) {
                            throw new RuntimeException(e);
                        } catch (IllegalBlockSizeException e) {
                            throw new RuntimeException(e);
                        } catch (NoSuchAlgorithmException e) {
                            throw new RuntimeException(e);
                        } catch (BadPaddingException e) {
                            throw new RuntimeException(e);
                        } catch (InvalidKeyException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }),
                new GuideItemEntity("RSA 生成密钥", new Runnable() {
                    @Override
                    public void run() {
                        try {
                            createKeys();
                        } catch (NoSuchAlgorithmException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }),
                new GuideItemEntity("RSA 私钥加密（一）", new Runnable() {
                    @Override
                    public void run() {
                        try {
                            encryptedData = encryptByPrivateKey(plainText.getBytes(), RSA_privateKey);
                            LogUtil.d("RSA 私钥加密: " + Base64.encodeToString(encryptedData, Base64.DEFAULT));

                        } catch (NoSuchAlgorithmException e) {
                            throw new RuntimeException(e);
                        } catch (InvalidKeySpecException e) {
                            throw new RuntimeException(e);
                        } catch (NoSuchPaddingException e) {
                            throw new RuntimeException(e);
                        } catch (InvalidKeyException e) {
                            throw new RuntimeException(e);
                        } catch (IllegalBlockSizeException e) {
                            throw new RuntimeException(e);
                        } catch (BadPaddingException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }),
                new GuideItemEntity("RSA 公钥解密（一）", new Runnable() {
                    @Override
                    public void run() {
                        try {
                            byte[] plaintext = decryptByPublicKey(encryptedData, RSA_publicKey);
                            LogUtil.d("RSA 公钥解密: " + new String(plaintext));


                        } catch (NoSuchAlgorithmException e) {
                            throw new RuntimeException(e);
                        } catch (InvalidKeySpecException e) {
                            throw new RuntimeException(e);
                        } catch (IllegalBlockSizeException e) {
                            throw new RuntimeException(e);
                        } catch (BadPaddingException e) {
                            throw new RuntimeException(e);
                        } catch (NoSuchPaddingException e) {
                            throw new RuntimeException(e);
                        } catch (InvalidKeyException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }),
                new GuideItemEntity("RSA 公钥加密（二）", new Runnable() {
                    @Override
                    public void run() {
                        try {
                            encryptedData = encryptByPublicKey(plainText.getBytes(), RSA_publicKey);
                            LogUtil.d("RSA 公钥加密: " + Base64.encodeToString(encryptedData, Base64.DEFAULT));

                        } catch (NoSuchAlgorithmException e) {
                            throw new RuntimeException(e);
                        } catch (InvalidKeySpecException e) {
                            throw new RuntimeException(e);
                        } catch (NoSuchPaddingException e) {
                            throw new RuntimeException(e);
                        } catch (InvalidKeyException e) {
                            throw new RuntimeException(e);
                        } catch (IllegalBlockSizeException e) {
                            throw new RuntimeException(e);
                        } catch (BadPaddingException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }),
                new GuideItemEntity("RSA 私钥解密（二）", new Runnable() {
                    @Override
                    public void run() {
                        try {
                            byte[] plaintext = decryptByPrivateKey(encryptedData, RSA_privateKey);
                            LogUtil.d("RSA 私钥解密: " + new String(plaintext));


                        } catch (NoSuchAlgorithmException e) {
                            throw new RuntimeException(e);
                        } catch (InvalidKeySpecException e) {
                            throw new RuntimeException(e);
                        } catch (IllegalBlockSizeException e) {
                            throw new RuntimeException(e);
                        } catch (BadPaddingException e) {
                            throw new RuntimeException(e);
                        } catch (NoSuchPaddingException e) {
                            throw new RuntimeException(e);
                        } catch (InvalidKeyException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }),
                new GuideItemEntity("base64 编码，剩余字节数的三种情况", new Runnable() {
                    @Override
                    public void run() {
                        byte[] data_3_byte = { (byte) 0xfa, (byte) 0x7b, (byte) 0x12 };
                        LogUtil.d("三字节: " + Base64.encodeToString(data_3_byte, Base64.DEFAULT));

                        byte[] data_2_byte = { (byte) 0xF3, (byte) 0x27};
                        LogUtil.d("2字节: " + Base64.encodeToString(data_2_byte, Base64.DEFAULT));

                        byte[] data_1_byte = { (byte) 0xF3};
                        LogUtil.d("1字节: " + Base64.encodeToString(data_1_byte, Base64.DEFAULT));
                    }
                }),
        });
    }

    private void createKeys() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA_ALGORITHM);
        keyPairGenerator.initialize(1024);  //1024位的密钥长度

        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

        //使用字节数组 byte[] 的形式来存储密钥
        RSA_publicKey = publicKey.getEncoded();
        RSA_privateKey = privateKey.getEncoded();

        LogUtil.d("公钥(Base64编码): " + Base64.encodeToString(RSA_publicKey, Base64.DEFAULT));
        LogUtil.d("私钥(Base64编码): " + Base64.encodeToString(RSA_privateKey, Base64.DEFAULT));
    }

    /**
     * 私钥加密
     */
    public static byte[] encryptByPrivateKey(byte[] data, byte[] key) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        /*PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(key);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        //生成私钥
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        //数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return cipher.doFinal(data);*/

        Key theKey = getKeyFromByteArray(key, 1);
        return encryptionOrDecryption(data, theKey, 0);
    }

    /**
     * 公钥解密
     */
    public static byte[] decryptByPublicKey(byte[] data, byte[] key) throws NoSuchAlgorithmException, InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, InvalidKeyException {
        /*KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(key);
        //产生公钥
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        //数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        return cipher.doFinal(data);*/

        Key theKey = getKeyFromByteArray(key, 0);
        return encryptionOrDecryption(data, theKey, 1);
    }

    /**
     * 公钥加密
     */
    public static byte[] encryptByPublicKey(byte[] data, byte[] key) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        /*KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(key);
        //产生公钥
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);

        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(data);*/

        Key theKey = getKeyFromByteArray(key, 0);
        return encryptionOrDecryption(data, theKey, 0);
    }

    /**
     * 私钥解密
     */
    public static byte[] decryptByPrivateKey(byte[] data,byte[] key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException {
        /*PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(key);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        //生成私钥
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        //数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(data);*/

        Key theKey = getKeyFromByteArray(key, 1);
        return encryptionOrDecryption(data, theKey, 1);
    }

    /**
     * 将字节数组转为密钥
     * @param key 密钥的 byte[] 形式
     * @param keyType 0代表公钥，1代表私钥
     * @return
     */
    public static Key getKeyFromByteArray(byte[] key, int keyType) throws InvalidKeySpecException, NoSuchAlgorithmException {
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        Key theKey = null;

        switch (keyType) {
            case 0:
                X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(key);
                //产生公钥
                PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
                theKey = publicKey;
                break;

            case 1:
                PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(key);
                PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
                theKey = privateKey;
                break;
        }

        return theKey;
    }

    /**
     * 加密或解密
     * @param data 如果是加密的，就是明文；如果是解密的h话，就是密文
     * @param key 公钥或者私钥
     * @param type 0代表加密，1代表解密
     * @return
     */
    public static byte[] encryptionOrDecryption(byte[] data, Key key, int type) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        //数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());

        switch (type) {
            case 0:
                cipher.init(Cipher.ENCRYPT_MODE, key);
                break;
            case 1:
                cipher.init(Cipher.DECRYPT_MODE, key);
                break;
        }

        return cipher.doFinal(data);
    }
}