package com.rustyleague.rustyjournal.repo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.rustyleague.rustyjournal.entity.user;

public class userRepoImpl {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<user> getUserForSA() {
        Query query=new Query();
        query.addCriteria(Criteria.where("username").is("vipul"));
        List<user> users=mongoTemplate.find(query,user.class);
        return users;
    }
}
