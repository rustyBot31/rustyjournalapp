package com.rustyleague.rustyjournal.entity;

import java.sql.Date;
import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "journals")
@Data
@NoArgsConstructor
public class journal {
    @Id
    private String id;
    private String title;
    private String content;
    private LocalDateTime date;
    @DBRef
    private user author;
    
}
