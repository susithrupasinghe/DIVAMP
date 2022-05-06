package com.mad.divamp.utils;

import android.widget.Toast;

import java.nio.charset.*;
import java.security.*;

import es.dmoral.toasty.Toasty;

public class SHA256 {

    public static String getHash(String word) {
        try {

            MessageDigest msg = MessageDigest.getInstance("SHA-256");
            byte[] hash = msg.digest(word.getBytes(StandardCharsets.UTF_8));
            // convert bytes to hexadecimal
            StringBuilder s = new StringBuilder();
            for (byte b : hash) {
                s.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }

            return s.toString();
        }
        catch (Exception ex){
            System.out.println(ex.toString());

            return "";
        }

    }


}