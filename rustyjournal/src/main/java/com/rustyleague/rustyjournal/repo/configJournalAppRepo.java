package com.rustyleague.rustyjournal.repo;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.rustyleague.rustyjournal.entity.configJournalApp;

public interface configJournalAppRepo extends MongoRepository<configJournalApp , ObjectId> {

}
