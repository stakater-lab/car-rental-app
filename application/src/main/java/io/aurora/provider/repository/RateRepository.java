package io.aurora.provider.repository;

import io.aurora.provider.domain.Rate;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Rate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RateRepository extends MongoRepository<Rate, String> {

}
