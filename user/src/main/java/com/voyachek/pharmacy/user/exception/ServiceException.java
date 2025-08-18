package com.voyachek.pharmacy.user.exception;

import io.grpc.Metadata;
import io.grpc.Status;
import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException {

    private final Status status;
    private final Metadata metadata;

    public ServiceException(Status status, String message) {
        super(message);
        this.status = status.withDescription(message);
        this.metadata = new Metadata();
    }

    public ServiceException(Status status, String message, Throwable cause) {
        super(message, cause);
        this.status = status.withDescription(message);
        this.metadata = new Metadata();
    }
}
