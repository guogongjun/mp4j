package com.yeapoo.odaesan.framework.resolver;

import java.util.List;
import java.util.Map;

import com.yeapoo.odaesan.sdk.model.message.Message;

public interface Resolver<T extends Message> {

    Message resolve(T input, Map<String, Object> params);

    void addResolver(Resolver<T> resolver);

    List<Resolver<T>> listResolvers();
}