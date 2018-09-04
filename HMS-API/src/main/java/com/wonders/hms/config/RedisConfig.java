package com.wonders.hms.config;

import com.wonders.hms.util.vo.RedisVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.PostConstruct;

@Configuration
public class RedisConfig {

    private String redisHost;

    private int redisPort;

    @Autowired
    private RedisVO redisProp;

    @PostConstruct
    public void init(){

        this.redisHost = redisProp.getHost();
        this.redisPort = redisProp.getPort();
    }

    @Primary
    @Bean(name = "hotelSearchRedisConnectionFactory")
    public JedisConnectionFactory hotelSearchConnectionFactory() {
        RedisStandaloneConfiguration redisHotelSearchStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisHotelSearchStandaloneConfiguration.setHostName(redisHost);
        redisHotelSearchStandaloneConfiguration.setPort(redisPort);
        redisHotelSearchStandaloneConfiguration.setDatabase(0);

       return new JedisConnectionFactory(redisHotelSearchStandaloneConfiguration);
    }

    @Bean(name = "autocompleteConnectionFactory")
    public JedisConnectionFactory autocompleteConnectionFactory() {
        RedisStandaloneConfiguration redisAutoCompleteStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisAutoCompleteStandaloneConfiguration.setHostName(redisHost);
        redisAutoCompleteStandaloneConfiguration.setPort(redisPort);
        redisAutoCompleteStandaloneConfiguration.setDatabase(1);

        return new JedisConnectionFactory(redisAutoCompleteStandaloneConfiguration);
    }

    @Bean(name = "scheduleConnectionFactory")
    public JedisConnectionFactory scheduleConnectionFactory() {
        RedisStandaloneConfiguration redisScheduleStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisScheduleStandaloneConfiguration.setHostName(redisHost);
        redisScheduleStandaloneConfiguration.setPort(redisPort);
        redisScheduleStandaloneConfiguration.setDatabase(15);

        return new JedisConnectionFactory(redisScheduleStandaloneConfiguration);
    }

    @Bean(name = "hotelSearchRedisTemplate")
    public RedisTemplate<String, Object> hotelSearchRedisTemplate() {
        RedisTemplate<String, Object> hotelSearchRedisTemplate = new RedisTemplate<>();
        hotelSearchRedisTemplate.setKeySerializer(new StringRedisSerializer());
        hotelSearchRedisTemplate.setValueSerializer(new RedisCompressSerializer());
        hotelSearchRedisTemplate.setHashKeySerializer(new StringRedisSerializer());
        hotelSearchRedisTemplate.setHashValueSerializer(new RedisCompressSerializer());
        hotelSearchRedisTemplate.setConnectionFactory(hotelSearchConnectionFactory());

        return hotelSearchRedisTemplate;
    }

    @Bean(name = "autocompleteRedisTemplate")
    public RedisTemplate<String, Object> autocompleteRedisTemplate() {
        RedisTemplate<String, Object> autocompleteRedisTemplate = new RedisTemplate<>();
        autocompleteRedisTemplate.setKeySerializer(new StringRedisSerializer());
        autocompleteRedisTemplate.setValueSerializer(new RedisCompressSerializer());
        autocompleteRedisTemplate.setConnectionFactory(autocompleteConnectionFactory());

        return autocompleteRedisTemplate;
    }

    @Bean(name = "scheduleRedisTemplate")
    public RedisTemplate<String, Object> scheduleRedisTemplate() {
        RedisTemplate<String, Object> scheduleRedisTemplate = new RedisTemplate<>();
        scheduleRedisTemplate.setKeySerializer(new StringRedisSerializer());
        scheduleRedisTemplate.setValueSerializer(new RedisIntegerSerializer());
        scheduleRedisTemplate.setConnectionFactory(scheduleConnectionFactory());

        return scheduleRedisTemplate;
    }
   }
