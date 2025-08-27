package com.voyachek.pharmacy.gateway.configuration.factory;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Фабрика для создания GRPC-канала, сервис которого зарегистрирован в Consul
 */
@Slf4j
@Component
@ConditionalOnProperty(name = "spring.cloud.consul.discovery.enabled", havingValue = "true", matchIfMissing = true)
public class GrpcConsulChannelFactory {

    private final DiscoveryClient discoveryClient;

    private final ConcurrentHashMap<String, ManagedChannel> channels = new ConcurrentHashMap<>();

    public GrpcConsulChannelFactory(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    /**
     * Получить GRPC-канал сервиса по его названию
     * @param serviceName Имя сервиса
     * @return {@link ManagedChannel}
     */
    public ManagedChannel getChannel(String serviceName) {
        return channels.computeIfAbsent(serviceName, name -> {
            var instances = discoveryClient.getInstances(name);

            if (instances.isEmpty()) {
                log.error("No instances found in Consul for service: {}", name);
                throw new IllegalStateException(String.format("No instances found in Consul for service: %s", name));
            } else {
                log.info("Found {} instances for service {}", instances.size(), name);
            }

            var instance = instances.get(0);
            log.info("{}, {}, {}", instance.getInstanceId(), instance.getHost(), instance.getPort());

            return ManagedChannelBuilder
                    .forAddress(instance.getHost(), instance.getPort())
                    .usePlaintext()
                    .build();
        });
    }
}
