package com.yeapoo.odaesan.sdk.client;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public abstract class BaseClient implements BeanFactoryAware, InitializingBean {

    private BeanFactory beanFactory;

    protected RestTemplate template;
    protected ObjectMapper mapper;

    protected HttpHeaders headers;

    public BaseClient() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.template = beanFactory.getBean(RestTemplate.class);
        this.mapper = beanFactory.getBean(ObjectMapper.class);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

}
