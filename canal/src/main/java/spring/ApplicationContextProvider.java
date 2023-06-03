package spring;

import jakarta.validation.constraints.NotNull;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 本类通过实现{@link ApplicationContextAware}接口提供{@link ApplicationContext}对象
 *
 * @author Zaki
 * @version 1.0
 * @since 2023-06-03
 **/
public class ApplicationContextProvider implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(@NotNull ApplicationContext context) {
        applicationContext = context;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

}
