package com.rustyleague.rustyjournal.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rustyleague.rustyjournal.entity.configJournalApp;
import com.rustyleague.rustyjournal.repo.configJournalAppRepo;

import jakarta.annotation.PostConstruct;

@Component
public class appCache {

    @Autowired
    public configJournalAppRepo appRepo;

    public Map<String, String> APP_CACHE;

    @PostConstruct
    public void init() {
        APP_CACHE=new HashMap<>();
        List<configJournalApp> list=appRepo.findAll();
        for(int i=0;i<list.size();i++) {
            APP_CACHE.put(list.get(i).getKey(),list.get(i).getValue());
        }
    }
}
