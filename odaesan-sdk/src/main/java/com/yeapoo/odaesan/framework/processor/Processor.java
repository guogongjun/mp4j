package com.yeapoo.odaesan.framework.processor;

import java.util.Map;

import com.yeapoo.odaesan.sdk.model.message.Message;


public interface Processor<T extends Message> {
    Message process(T input, Map<String, Object> params);
}