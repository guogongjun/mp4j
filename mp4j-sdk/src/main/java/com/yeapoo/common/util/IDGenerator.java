package com.yeapoo.common.util;

import java.util.UUID;

/**
 * 数据库ID生成类，独立成类便于以后扩展。
 * 
 * @author Simon
 *
 */
public class IDGenerator {

    private IDGenerator() {}

    /**
     * 生成ID
     * 
     * @param clazz 要生成ID的表对应的实体类的class
     * @return ID
     */
    public static String generate(Class<?> clazz) {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
