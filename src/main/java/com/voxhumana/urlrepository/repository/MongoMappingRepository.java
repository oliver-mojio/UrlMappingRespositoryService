package com.voxhumana.urlrepository.repository;

import com.voxhumana.urlrepository.model.mongo.Mapping;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoMappingRepository extends MongoRepository<Mapping, String> {
    public Mapping findByValue(String value);
}