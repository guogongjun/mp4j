package com.yeapoo.odaesan.common.util;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

public class StringUtil {

    public static String filterUTF8MB4(String str) {
        byte[] bytes = new byte[]{};
        try {
            bytes = str.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {}
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
            }
            else if ((b ^ 0xE0) >> 4 == 0) {
                buffer.put(bytes, i, 3);
                i += 3;
            }
            else if ((b ^ 0xF0) >> 4 == 0) {
                i += 4;
            }
        }
        buffer.flip();
        try {
            return new String(buffer.array(), "utf-8");
        } catch (UnsupportedEncodingException e) {}
        return null;
    }

}
