package com.yeapoo.odaesan.sdk.util;

import java.util.Date;

public class MediaExpireChecker {

    private static final int EXPIRES_IN = 3 * 24 * 3600;

    public static boolean check(int createTime) {
        long now = new Date().getTime() / 1000;
        return now - createTime < EXPIRES_IN;
    }
}
