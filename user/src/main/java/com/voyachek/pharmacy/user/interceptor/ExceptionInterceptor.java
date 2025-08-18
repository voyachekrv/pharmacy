package com.voyachek.pharmacy.user.interceptor;

import com.voyachek.pharmacy.user.exception.ServiceException;
import io.grpc.*;
import lombok.extern.slf4j.Slf4j;

/**
 * Обработка исключений grpc
 */
@Slf4j
public class ExceptionInterceptor implements ServerInterceptor {

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
            ServerCall<ReqT, RespT> call,
            Metadata headers,
            ServerCallHandler<ReqT, RespT> next) {

        ServerCall.Listener<ReqT> listener = next.startCall(
                new ForwardingServerCall.SimpleForwardingServerCall<>(call) {},
                headers
        );

        return new ForwardingServerCallListener.SimpleForwardingServerCallListener<>(listener) {
            @Override
            public void onHalfClose() {
                try {
                    super.onHalfClose();
                } catch (ServiceException e) {
                    log.warn("Исключение: {}", e.getMessage());
                    call.close(e.getStatus(), e.getMetadata());
                } catch (StatusRuntimeException e) {
                    log.warn("Исключение со статусом: {}", e.getMessage());
                    call.close(e.getStatus().withDescription(e.getMessage()), e.getTrailers());
                } catch (Exception e) {
                    log.error("Ошибка: ", e);
                    call.close(Status.INTERNAL.withDescription("Ошибка сервера").withCause(e), new Metadata());
                }
            }
        };
    }
}
