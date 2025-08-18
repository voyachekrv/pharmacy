package com.voyachek.pharmacy.user.interceptor;

import io.grpc.*;
import lombok.extern.slf4j.Slf4j;

/**
 * Логгирование grpc
 */
@Slf4j
public class LoggingInterceptor implements ServerInterceptor {

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call,
                                                                 Metadata headers,
                                                                 ServerCallHandler<ReqT, RespT> next) {
        log.info("→ вызов gRPC: {} {}", call.getMethodDescriptor().getFullMethodName(), headers);

        ServerCall.Listener<ReqT> listener = next.startCall(
                new ForwardingServerCall.SimpleForwardingServerCall<>(call) {
                    @Override
                    public void sendMessage(RespT message) {
                        log.info("← ответ gRPC: [{}]", message);
                        super.sendMessage(message);
                    }
                },
                headers
        );

        return new ForwardingServerCallListener.SimpleForwardingServerCallListener<>(listener) {
            @Override
            public void onMessage(ReqT message) {
                log.info("Тело запроса: [{}]", message);
                super.onMessage(message);
            }
        };
    }
}
