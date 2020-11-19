package com.wang.dhxt.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextHolder implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static ApplicationContext getApplicationContext(){
        return context;
    }

    public static <T> T getBean(Class<T> clazz){
        return context.getBean(clazz);
    }

    public static <T> T getBean(String name){
        return (T)context.getBean(name);
    }
}