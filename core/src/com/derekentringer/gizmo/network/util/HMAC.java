package com.derekentringer.gizmo.network.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class HMAC {

    private static final String TAG = HMAC.class.getSimpleName();

    public static String hmacWithKey(final String key, final byte[] data) {
        String hash = "";
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            hash = Base64.encodeToString(sha256_HMAC.doFinal(data), Base64.NO_WRAP);
        }
        catch (NoSuchAlgorithmException e) {
        }
        catch (InvalidKeyException e) {
        }
        catch (UnsupportedEncodingException e) {
        }

        return hash;
    }

}