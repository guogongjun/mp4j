package com.yeapoo.odaesan.common.util;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

public class StringUtil {

    public static String filterUTF8MB4(String str) {
        byte[] bytes = new byte[] {};
        try {
            bytes = str.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
        }
        ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
        int i = 0;
        while (i < bytes.length) {
            short b = bytes[i];
            if (b > 0) {
                buffer.put(bytes[i++]);
                continue;
            }
            b += 256;
            if ((b ^ 0xC0) >> 4 == 0) {
                buffer.put(bytes, i, 2);
                i += 2;
            } else if ((b ^ 0xE0) >> 4 == 0) {
                buffer.put(bytes, i, 3);
                i += 3;
            } else if ((b ^ 0xF0) >> 4 == 0) {
                i += 4;
            } else {
                i++;
            }
        }
        buffer.flip();
        try {
            return new String(buffer.array(), "utf-8");
        } catch (UnsupportedEncodingException e) {
        }
        return null;
    }

    public static String filterUnusualChar(String str) {
        int length = str.length();
        if (length == str.codePointCount(0, length)) {
            return str;
        }
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char c = str.charAt(i);
            if (i < length - 1 && Character.isSurrogatePair(c, str.charAt(i + 1))) {
                i++;
            } else {
                buffer.append(c);
            }
        }
        return buffer.toString();
    }
}
