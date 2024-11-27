package com.rustyleague.rustyjournal.entity;

import java.util.ArrayList;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;
@Document(collection = "users")
@Data
@NoArgsConstructor
public class user {
    @Transient
    public static final String SEQUENCE_NAME="user-sequence";
    @Id
    private Long id;
    @NonNull
    @Indexed(unique=true)
    private String username;
    @NonNull
    private String password;
    private String email;
    private boolean sentimentAnalysis;
    HashSet<String> list=new HashSet<>();
    private long noOfJournals;
    List<String> roles=new ArrayList<>();
}
