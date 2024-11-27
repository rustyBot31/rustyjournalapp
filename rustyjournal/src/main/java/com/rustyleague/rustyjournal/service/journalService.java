package com.rustyleague.rustyjournal.service;

import java.lang.classfile.ClassFile.Option;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.*;
import com.rustyleague.rustyjournal.entity.journal;
import com.rustyleague.rustyjournal.entity.user;
import com.rustyleague.rustyjournal.repo.journalrepo;
import com.rustyleague.rustyjournal.repo.userrepo;

@Component
public class journalService {

    @Autowired
    private journalrepo journalRepo;

    @Autowired
    private userrepo userRepo;

    public HashSet<String> getAllbyUser(String name) {
        user user=userRepo.findByusername(name);
        List<journal> res=journalRepo.findAllById(user);
        HashSet<String> set=new HashSet<>();
        for(int i=0;i<res.size();i++) {
            set.add(res.get(i).getId());
        }
        return set;
    }
    public boolean saveEntry(journal myEntry) {
        journalRepo.save(myEntry);
        return true;
    }

    public Optional<journal> get(String id) {
        return journalRepo.findById(id);
    }

    public void deleteJournal(String id) {
        journalRepo.deleteById(id);
    }
}
