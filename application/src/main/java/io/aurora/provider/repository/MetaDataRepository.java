package io.aurora.provider.repository;

import io.aurora.provider.domain.MetaData;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the MetaData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MetaDataRepository extends MongoRepository<MetaData, String> {

}
