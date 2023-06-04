package com.cnzakii.common;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;

/**
 * 本类通过实现{@link ApplicationListener}接口实现获取ApplicationContext
 *
 * @author Zaki
 * @version 1.0
 * @since 2023-06-04
 **/
public class ApplicationContextProvider implements ApplicationListener<ApplicationContextEvent> {

    public static ApplicationContext applicationContext;

    @Override
    public void onApplicationEvent(ApplicationContextEvent event) {
        applicationContext = event.getApplicationContext();
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
