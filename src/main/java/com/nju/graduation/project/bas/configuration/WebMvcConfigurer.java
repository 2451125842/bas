package com.nju.graduation.project.bas.configuration;

import com.nju.graduation.project.bas.interceptor.UserInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author shanhe
 * @className WebMvcConfigurer
 * @date 2021-02-25 15:29
 **/
@Configuration
public class WebMvcConfigurer extends WebMvcConfigurationSupport {

    @Bean
    public UserInterceptor getConsumerInterceptor() {
        return new UserInterceptor();
    }


    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getConsumerInterceptor())
                    .excludePathPatterns("/")
                    .excludePathPatterns("/consumer/verify")
                    .excludePathPatterns("/manager/verify")
                    .excludePathPatterns("/consumer/login")
                    .excludePathPatterns("/manager/login");

        super.addInterceptors(registry);
    }
}
