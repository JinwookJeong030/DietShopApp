package com.cookandroid.dietcontrol.util;

import android.text.InputFilter;
import android.text.Spanned;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import java.util.Base64;
import java.util.regex.Pattern;

public class Formatting  {
// 영문만 허용 (숫자 포함)


    public static final String pattern1 = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,20}$";




    public static String SHA512(String password, String hash) { String salt = hash+password;


    String hex = null;
    try { MessageDigest msg = MessageDigest.getInstance("SHA-512");


            msg.update(salt.getBytes()); hex = String.format("%128x", new BigInteger(1, msg.digest()));

    } catch (NoSuchAlgorithmException e){
        e.printStackTrace();
    }

    return hex;
    }

    public static String Salt() {

        String salt="";
        try { SecureRandom random = SecureRandom.getInstance("SHA1PRNG");

            byte[] bytes = new byte[16];

            random.nextBytes(bytes);

            salt = new String(Base64.getEncoder().encode(bytes));

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace(); } return salt;


    }





    public static InputFilter filter= new InputFilter() {

        public CharSequence filter(CharSequence source, int start, int end,

            Spanned dest, int dstart, int dend) {



            Pattern ps = Pattern.compile("^[a-zA-Z0-9]+$");

            if (!ps.matcher(source).matches()) {

                return "";

            }

            return null;

        }

    };

    public static InputFilter filterKor = new InputFilter() {

        public CharSequence filter(CharSequence source, int start, int end,

                                   Spanned dest, int dstart, int dend) {



            Pattern ps = Pattern.compile("^[ㄱ-가-힣]+$");

            if (!ps.matcher(source).matches()) {

                return "";

            }

            return null;

        }

    };










}
