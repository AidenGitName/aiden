package com.wonders.hms.config;

import com.wonders.hms.util.GZIPCompression;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.lang.Nullable;

import java.io.IOException;

public class RedisCompressSerializer implements RedisSerializer<String> {

    @Override
    public String deserialize(@Nullable byte[] bytes) {
        try {
            return (bytes == null ? null : GZIPCompression.decompress(bytes));
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public byte[] serialize(@Nullable String string) {
        try {
            return (string == null ? null : GZIPCompression.compress(string));
        } catch (IOException e) {
            return null;
        }
    }
}
