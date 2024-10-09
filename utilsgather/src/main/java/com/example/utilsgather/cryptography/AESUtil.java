package com.example.utilsgather.cryptography;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESUtil {
    /**
     * AES加密,返回BASE64编码后的加密字符串
     *
     * @param sSrc           -- 待加密内容
     * @param encodingFormat -- 字符串编码方式
     * @param algorithm      -- 使用的算法 算法/模式/补码方式, 目前支持ECB和CBC模式
     * @param sKey           -- 加密密钥
     * @param ivParameter    -- 偏移量,CBC模式时需要
     * @return Base64编码后的字符串
     * @throws Exception
     */
    public static String aesEncrypt(String sSrc, String encodingFormat, String algorithm, String sKey, String ivParameter) throws Exception {
        Cipher cipher = Cipher.getInstance(algorithm);
        byte[] raw = sKey.getBytes(encodingFormat);
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        //使用CBC模式，需要一个向量iv，可增加加密算法的强度
        IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes(encodingFormat));
        if (algorithm.contains("CBC")) {
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        } else {
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        }
        byte[] encrypted = cipher.doFinal(sSrc.getBytes(encodingFormat));
        //此处使用BASE64做转码。
        return Base64.encodeToString(encrypted, Base64.DEFAULT);
    }

    /**
     * AES解密
     * @param sSrc -- 待解密Base64字符串
     * @param encodingFormat -- 字符串编码方式
     * @param algorithm -- 使用的算法 算法/模式/补码方式, 目前支持ECB和CBC模式
     * @param sKey -- 加密密钥
     * @param ivParameter -- 偏移量,CBC模式时需要
     * @return 解密后的字符串
     * @throws Exception
     */
    public static String aesDecrypt(String sSrc, String encodingFormat, String algorithm, String sKey, String ivParameter) throws Exception {
        byte[] raw = sKey.getBytes(encodingFormat);
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance(algorithm);
        IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes(encodingFormat));
        if (algorithm.contains("CBC")) {
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
        } else {//ECB模式
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        }
        //先用base64解密
        byte[] encrypted1 = Base64.decode(sSrc, Base64.DEFAULT);
        byte[] original = cipher.doFinal(encrypted1);
        return new String(original, encodingFormat);
    }

}
