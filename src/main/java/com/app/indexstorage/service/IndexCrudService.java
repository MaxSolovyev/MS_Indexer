package com.app.indexstorage.service;

import com.app.indexstorage.model.IndexDoc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class IndexCrudService {
    private MongoTemplate mongoTemplate;

    @Autowired
    public IndexCrudService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<IndexDoc> getIndexDocByWords(List<String> words) {
        return mongoTemplate.find(query(where("word").in(words)
                ), IndexDoc.class);
    }
}