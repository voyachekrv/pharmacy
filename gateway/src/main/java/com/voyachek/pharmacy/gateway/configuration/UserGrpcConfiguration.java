package com.voyachek.pharmacy.gateway.configuration;

import com.voyachek.pharmacy.gateway.configuration.factory.GrpcConsulChannelFactory;
import com.voyachek.pharmacy.grpclib.user.UserEndpointGrpc;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурация gRPC-клиента для сервиса `user` для работы с сущностью "Пользователь"
 */
@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(name = {"spring.cloud.consul.discovery.enabled", "app.services.user.enabled"},
        havingValue = "true",
        matchIfMissing = true)
public class UserGrpcConfiguration {

    @Value("${app.services.user.host}")
    private String host;

    @Bean
    public UserEndpointGrpc.UserEndpointBlockingStub userEndpointBlockingStub(GrpcConsulChannelFactory channelFactory) {
        var channel = channelFactory.getChannel(host);
        return UserEndpointGrpc.newBlockingStub(channel);
    }
}
