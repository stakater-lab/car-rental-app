package io.aurora.provider.repository;

import io.aurora.provider.domain.Rating;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Rating entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RatingRepository extends MongoRepository<Rating, String> {

}
