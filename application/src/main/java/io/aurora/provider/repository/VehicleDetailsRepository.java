package io.aurora.provider.repository;

import io.aurora.provider.domain.VehicleDetails;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the VehicleDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VehicleDetailsRepository extends MongoRepository<VehicleDetails, String> {

}
