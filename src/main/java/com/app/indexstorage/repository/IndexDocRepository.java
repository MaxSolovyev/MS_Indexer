package com.app.indexstorage.repository;

import com.app.indexstorage.model.IndexDoc;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface IndexDocRepository extends MongoRepository<IndexDoc, ObjectId> {
    List<IndexDoc> findByWord(String word);

}

