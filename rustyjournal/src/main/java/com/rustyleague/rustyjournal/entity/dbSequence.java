package com.rustyleague.rustyjournal.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "db-seq")
@Data
public class dbSequence {

    @Id
    private String id;
    private long seqNo;
    public dbSequence() {};
}
