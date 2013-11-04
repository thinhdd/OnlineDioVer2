package com.qsoft.ondio.util;

import java.security.MessageDigest;

/**
 * Created with IntelliJ IDEA.
 * User: thinhdd
 * Date: 10/30/13
 * Time: 8:53 AM
 * To change this template use File | Settings | File Templates.
 */
public class HashStringToMD5
{
    public String doConvert(String password) throws Exception
    {

        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());

        byte byteData[] = md.digest();

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++)
        {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }
}
