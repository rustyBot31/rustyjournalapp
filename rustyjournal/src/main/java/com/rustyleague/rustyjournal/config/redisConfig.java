package com.rustyleague.rustyjournal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class redisConfig {

    @Bean
    @Primary
    public RedisTemplate redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate redisTemp=new RedisTemplate<>();
        redisTemp.setConnectionFactory(factory);
        redisTemp.setKeySerializer(new StringRedisSerializer());
        redisTemp.setValueSerializer(new StringRedisSerializer());

        return redisTemp;
    }
}
