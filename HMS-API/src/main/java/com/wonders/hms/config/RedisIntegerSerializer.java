package com.wonders.hms.config;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.lang.Nullable;

public class RedisIntegerSerializer  implements RedisSerializer<Integer> {
    private final static String DEFAULT_CHARSET = "UTF-8";

    @Override
    public Integer deserialize(@Nullable byte[] bytes) {
        try {
            return (bytes == null ? null : Integer.valueOf(new String(bytes, DEFAULT_CHARSET)));
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public byte[] serialize(@Nullable Integer integer) {
        try {
            return (integer == null ? null : integer.toString().getBytes(DEFAULT_CHARSET));
        } catch (Exception e) {
            return null;
        }
    }
}
