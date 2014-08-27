package com.yeapoo.odaesan.common.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Component;

@Component
public class BeanFactoryUtil implements BeanFactoryAware {

    private static BeanFactory factory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        factory = beanFactory;
    }

    public static BeanFactory getBeanFactory() {
        return factory;
    }

    public static <T> T getBean(Class<T> clazz) {
        return factory.getBean(clazz);
    }
}
