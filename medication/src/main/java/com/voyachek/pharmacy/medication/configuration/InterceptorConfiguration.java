package com.voyachek.pharmacy.medication.configuration;

import com.voyachek.pharmacy.medication.interceptor.LoggingInterceptor;
import com.voyachek.pharmacy.medication.interceptor.ExceptionInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.grpc.server.GlobalServerInterceptor;

@Configuration
public class InterceptorConfiguration {

    @Bean
    @Order(100)
    @GlobalServerInterceptor
    public LoggingInterceptor loggingInterceptor() {
        return new LoggingInterceptor();
    }

    @Bean
    @Order(101)
    @GlobalServerInterceptor
    public ExceptionInterceptor exceptionInterceptor() {
        return new ExceptionInterceptor();
    }
}
