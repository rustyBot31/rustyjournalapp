package com.rustyleague.rustyjournal.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Document(collection = "configJournalApp")
public class configJournalApp {
    @Id
    private ObjectId id;
    private String key;
    private String value;
}
