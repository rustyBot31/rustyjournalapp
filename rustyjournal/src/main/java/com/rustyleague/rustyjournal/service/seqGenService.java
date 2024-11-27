package com.rustyleague.rustyjournal.service;

import java.util.Objects;


import static org.springframework.data.mongodb.core.FindAndModifyOptions.*;
import static org.springframework.data.mongodb.core.query.Criteria.*;
import static org.springframework.data.mongodb.core.query.Query.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.rustyleague.rustyjournal.entity.dbSequence;

@Service
public class seqGenService {

    @Autowired
    private static MongoOperations mongoOps;

    @Autowired
    public seqGenService(MongoOperations mongoOps) {
        this.mongoOps=mongoOps;
    }

    public static long generateSequence(String seqName) {
        dbSequence counter = mongoOps.findAndModify(query(where("_id").is(seqName)), new Update().inc("seqNo",1),  options().returnNew(true).upsert(true),
        dbSequence.class);
        return !Objects.isNull(counter) ? counter.getSeqNo() : 1;
    }
}
