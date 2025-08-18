package com.voyachek.pharmacy.grpclib.utils;

import com.google.protobuf.Timestamp;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public final class TimeUtil {

    private TimeUtil() {}

    public static Timestamp toTimestamp(LocalDateTime dateTime) {
        return Timestamp.newBuilder()
                .setSeconds(dateTime.toEpochSecond(ZoneOffset.UTC))
                .setNanos(dateTime.getNano())
                .build();
    }

    public static LocalDateTime fromTimestamp(Timestamp timestamp) {
        return LocalDateTime.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos(), ZoneOffset.UTC);
    }
}
