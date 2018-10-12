package io.aurora.provider.repository;

import io.aurora.provider.domain.VehicleModel;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the VehicleModel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VehicleModelRepository extends MongoRepository<VehicleModel, String> {

}
