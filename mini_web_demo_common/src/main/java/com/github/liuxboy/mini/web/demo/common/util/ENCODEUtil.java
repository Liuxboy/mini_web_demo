package com.github.liuxboy.mini.web.demo.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class ENCODEUtil {

    public static String md5(String inputText)
    {
        return encrypt(inputText, "md5");
    }

    public static String sha(String inputText)
    {
        return encrypt(inputText, "sha-1");
    }

    private static String encrypt(String inputText, String algorithmName)
    {
        if ((inputText == null) || ("".equals(inputText.trim())))
            throw new IllegalArgumentException("请输入要加密的内容");

        if ((algorithmName == null) || ("".equals(algorithmName.trim())))
            algorithmName = "md5";

        String encryptText = null;
        try {
            MessageDigest m = MessageDigest.getInstance(algorithmName);
            m.update(inputText.getBytes("UTF8"));
            byte[] s = m.digest();

            return hex(s);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();

            return encryptText;
        }
        return encryptText;
    }

    private static String hex(byte[] arr)
    {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < arr.length; ++i)
            sb.append(Integer.toHexString(arr[i] & 0xFF | 0x100).substring(1, 3));

        return sb.toString();
    }

    public static void main(String[] args) {
        BufferedReader is = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("enter user name,and press enter:");
            String user = is.readLine();
            System.out.println("Password 123,in database is[" + sha(new StringBuilder("123{").append(user).append("}").toString()) + "]");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
