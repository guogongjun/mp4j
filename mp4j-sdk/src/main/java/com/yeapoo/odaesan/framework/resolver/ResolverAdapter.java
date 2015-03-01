package com.yeapoo.odaesan.framework.resolver;

import java.util.ArrayList;
import java.util.List;

import com.yeapoo.odaesan.sdk.model.message.Message;

public abstract class ResolverAdapter<T extends Message> implements Resolver<T> {

    @Override
    public void addResolver(Resolver<T> resolver) {}

    @Override
    public List<Resolver<T>> listResolvers() {
        return new ArrayList<Resolver<T>>(1);
    }

    @Override
    public String toString() {
        return getClass().getName();
    }
}