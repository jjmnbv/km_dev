package com.pltfm.app.util;

import org.apache.shiro.crypto.hash.Md5Hash;

public class MD5 {

    public static String md5crypt(String input) {
        return new Md5Hash(input).toString();
    }


}