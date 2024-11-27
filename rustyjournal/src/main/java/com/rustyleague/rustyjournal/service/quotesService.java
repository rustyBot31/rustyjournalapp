package com.rustyleague.rustyjournal.service;

import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rustyleague.rustyjournal.cache.appCache;
import com.rustyleague.rustyjournal.entity.quote;

@Component
public class quotesService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private appCache appCache;

    @Autowired
    private redisService redisService;

    public quote[] quoteConvert(String s) throws JsonMappingException, JsonProcessingException {
        ObjectMapper mapper=new ObjectMapper();
        quote[] res=mapper.readValue(s, quote[].class);
        return res;
    }

    public quote[] getQuote() throws JsonMappingException, JsonProcessingException {
        quote[] resp = redisService.get("quote",quote[].class);
        if(resp!= null) {
            return resp;
        }
        HttpHeaders headers = new HttpHeaders();
        String apiKey=appCache.APP_CACHE.get("API_KEY");
        headers.add("X-Api-Key", apiKey);

        // Create request entity with headers
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        // Make the request
        ResponseEntity<String> response = restTemplate.exchange(
                appCache.APP_CACHE.get("API"), 
                HttpMethod.GET, 
                requestEntity, 
                String.class);
        String s=response.getBody();
        quote[] quote=quoteConvert(s);
        if(quote!=null) {
            redisService.set("quote",quote,10L);
        }
        return quote;
    }

}
