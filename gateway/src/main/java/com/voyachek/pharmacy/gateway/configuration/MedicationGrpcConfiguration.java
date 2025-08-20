package com.voyachek.pharmacy.gateway.configuration;

import com.voyachek.pharmacy.gateway.configuration.factory.GrpcConsulChannelFactory;
import com.voyachek.pharmacy.grpclib.medication.MedicationEndpointGrpc;
import com.voyachek.pharmacy.grpclib.medication.RefCategoryEndpointGrpc;
import com.voyachek.pharmacy.grpclib.medication.RefSubstanceEndpointGrpc;
import io.grpc.ManagedChannel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурация gRPC-клиента для сервиса `medication`
 */
@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(name = {"spring.cloud.consul.discovery.enabled", "app.services.medication.enabled"},
        havingValue = "true",
        matchIfMissing = true)
public class MedicationGrpcConfiguration {

    @Value("${app.services.medication.host}")
    private String host;

    @Bean
    public RefCategoryEndpointGrpc.RefCategoryEndpointBlockingStub refCategoryEndpointBlockingStub(GrpcConsulChannelFactory channelFactory) {
        return RefCategoryEndpointGrpc.newBlockingStub(getChannel(channelFactory));
    }

    @Bean
    public RefSubstanceEndpointGrpc.RefSubstanceEndpointBlockingStub refSubstanceEndpointBlockingStub(GrpcConsulChannelFactory channelFactory) {
        return RefSubstanceEndpointGrpc.newBlockingStub(getChannel(channelFactory));
    }

    @Bean
    public MedicationEndpointGrpc.MedicationEndpointBlockingStub  medicationEndpointBlockingStub(GrpcConsulChannelFactory channelFactory) {
        return MedicationEndpointGrpc.newBlockingStub(getChannel(channelFactory));
    }

    private ManagedChannel getChannel(GrpcConsulChannelFactory channelFactory) {
        return channelFactory.getChannel(host);
    }
}
