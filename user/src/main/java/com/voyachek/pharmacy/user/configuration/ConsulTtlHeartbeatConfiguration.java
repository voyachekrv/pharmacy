package com.voyachek.pharmacy.user.configuration;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.consul.serviceregistry.ConsulAutoServiceRegistration;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ConsulTtlHeartbeatConfiguration {

    private final ConsulAutoServiceRegistration registration;

    @PostConstruct
    public void forceRegister() {
        registration.start();
        log.info(">>> Consul registration started manually");
    }
}
