package com.rustyleague.rustyjournal.repo;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.rustyleague.rustyjournal.entity.user;

public interface userrepo extends MongoRepository<user, Long> {
    user findByusername(String name);
}
