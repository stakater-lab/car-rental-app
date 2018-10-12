package io.aurora.provider.repository;

import io.aurora.provider.domain.VehicleType;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the VehicleType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VehicleTypeRepository extends MongoRepository<VehicleType, String> {

}
