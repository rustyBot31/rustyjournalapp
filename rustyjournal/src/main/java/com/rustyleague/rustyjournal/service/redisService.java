package com.rustyleague.rustyjournal.service;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rustyleague.rustyjournal.entity.quote;

@Component
public class redisService {
    
    @Autowired
    private RedisTemplate redisTemp;

    public <T> T get(String key, Class<T> quoteClass) {
        try {
            Object obj = redisTemp.opsForValue().get(key);
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(obj.toString(),quoteClass);
        } catch (Exception e) {
            return null;
        }
    }
    
    public void set(String key, Object obj, Long ttl) {
        try {
            ObjectMapper objMap=new ObjectMapper();
            String jsonString=objMap.writeValueAsString(obj);
            redisTemp.opsForValue().set(key,jsonString,ttl,TimeUnit.MINUTES);
        } catch(Exception e) {
            System.out.println("error");
        }
        
    } 
}
