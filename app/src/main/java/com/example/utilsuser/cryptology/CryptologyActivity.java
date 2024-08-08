package com.example.utilsuser.cryptology;

import android.os.Bundle;
import android.util.Base64;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.utilsgather.list_guide.GuideItemEntity;
import com.example.utilsgather.list_guide.GuideSettings;
import com.example.utilsgather.logcat.LogUtil;
import com.example.utilsuser.R;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class CryptologyActivity extends AppCompatActivity {
    String plainText = "abcd123黄绍飞";
    String originKey = "11525949";  // 提供原始秘钥:长度64位,8字节
    String base64Encipher = null;

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
                new GuideItemEntity("DES加密", new Runnable() {
                    @Override
                    public void run() {

                    }
                }),
                new GuideItemEntity("DES加密", new Runnable() {
                    @Override
                    public void run() {

                    }
                }),
                new GuideItemEntity("DES加密", new Runnable() {
                    @Override
                    public void run() {

                    }
                }),
                new GuideItemEntity("DES加密", new Runnable() {
                    @Override
                    public void run() {

                    }
                }),
                new GuideItemEntity("DES加密", new Runnable() {
                    @Override
                    public void run() {

                    }
                }),
                new GuideItemEntity("DES加密", new Runnable() {
                    @Override
                    public void run() {

                    }
                }),
        });
    }
}