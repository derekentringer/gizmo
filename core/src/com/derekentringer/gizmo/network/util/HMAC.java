package com.derekentringer.gizmo.network.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import okhttp3.RequestBody;

public class HMAC {

    public static String hmacDigest(RequestBody body, String secret) {
        String digest = null;
        try {
            SecretKeySpec key = new SecretKeySpec((secret).getBytes("UTF-8"), "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(key);

            byte[] bytes = mac.doFinal(body.toString().getBytes("ASCII"));

            StringBuffer hash = new StringBuffer();
            for (int i = 0; i < bytes.length; i++) {
                String hex = Integer.toHexString(0xFF & bytes[i]);
                if (hex.length() == 1) {
                    hash.append('0');
                }
                hash.append(hex);
            }
            digest = hash.toString();
        }
        catch (UnsupportedEncodingException e) {
        }
        catch (InvalidKeyException e) {
        }
        catch (NoSuchAlgorithmException e) {
        }
        return digest;
    }

}