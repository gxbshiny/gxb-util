package com.gxb.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @Author: gxb
 * @Time: 2019/11/29 16:19
 * @Description:
 */
public class BeanUtil implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        this.context = context;
    }

    public static void setContext(ApplicationContext context) {
        BeanUtil.context = context;
    }

    public static ApplicationContext getContext() {
        return context;
    }

    public static <T> T getBean(Class<T> clazz) {
        return context.getBean(clazz);
    }

    public static <T> T getBean(String bean, Class<T> clazz) {
        return context.getBean(bean, clazz);
    }

    public static Object getBean(String bean) {
        return context.getBean(bean);
    }

    public static boolean containBean(String bean) {
        return context.containsBean(bean);
    }

}
