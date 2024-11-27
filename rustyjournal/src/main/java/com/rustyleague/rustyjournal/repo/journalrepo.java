package com.rustyleague.rustyjournal.repo;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.rustyleague.rustyjournal.entity.journal;
import com.rustyleague.rustyjournal.entity.user;

import java.util.*;
public interface journalrepo extends MongoRepository<journal,String> {
    List<journal> findAllById(user myUser);
}


