package io.aurora.provider.repository;

import io.aurora.provider.domain.FeedBack;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the FeedBack entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FeedBackRepository extends MongoRepository<FeedBack, String> {

}
