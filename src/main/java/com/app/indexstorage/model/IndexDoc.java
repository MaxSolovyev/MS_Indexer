package com.app.indexstorage.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;

@Document(collection="words")
public class IndexDoc {
    @Id
    public ObjectId id;
    public String word;
    public List<Long> references;

    public IndexDoc(String word, List<Long> references) {
        this.word = word;
        this.references = references;
    }
}
