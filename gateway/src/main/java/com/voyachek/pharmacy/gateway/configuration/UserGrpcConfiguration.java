package com.voyachek.pharmacy.gateway.configuration;

import com.voyachek.pharmacy.grpclib.user.UserEndpointGrpc;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.grpc.client.GrpcChannelFactory;

/**
 * Конфигурация gRPC-клиента для сервиса `user` для работы с сущностью "Пользователь"
 */
@Configuration
public class UserGrpcConfiguration {

    @Value("${com.voyachek.pharmacy.user.grpc.host}")
    private String host;

    @Value("${com.voyachek.pharmacy.user.grpc.port}")
    private int port;

    @Bean
    public UserEndpointGrpc.UserEndpointBlockingStub userEndpointBlockingStub(GrpcChannelFactory channels) {
        return UserEndpointGrpc.newBlockingStub(channels.createChannel(String.format("%s:%d", host, port)));
    }
}
