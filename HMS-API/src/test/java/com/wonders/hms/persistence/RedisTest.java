package com.wonders.hms.persistence;

import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest{

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @After
    public void tearDown() {
        Set<String> keys = redisTemplate.keys("*");
        redisTemplate.delete(keys);
    }

    @Test
    public void writeAndRead() throws InterruptedException {

        String key = "key5";
        String value = "hello world";
        Map<String, Object> data = new HashMap<>();
        data.put("aaa", value);

        redisTemplate.opsForHash().putAll(key, data);

        String result = (String) redisTemplate.opsForHash().get(key, "aaa");
        assertThat(result, is(value));

        redisTemplate.expire(key, 1, TimeUnit.SECONDS);

        Thread.sleep(1000);

        result = (String) redisTemplate.opsForHash().get(key, "aaa");
        assertThat(result, is(nullValue()));


    }

    @Test
    @Ignore("확인")
    public void writeAndReadSimply() {
        ValueOperations vo = redisTemplate.opsForValue();
        vo.set("key", "value");
        assertThat(vo.get("key"),is("value"));
    }

    @Test
    @Ignore("확인")
    public void readNullValue() {
        String result = (String) redisTemplate.opsForHash().get("no key", "aaa");
        assertThat(result, nullValue());
    }
}
