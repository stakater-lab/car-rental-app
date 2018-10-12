package io.aurora.provider.service;

import io.aurora.provider.service.dto.VehicleDetailsDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing VehicleDetails.
 */
public interface VehicleDetailsService {

    /**
     * Save a vehicleDetails.
     *
     * @param vehicleDetailsDTO the entity to save
     * @return the persisted entity
     */
    VehicleDetailsDTO save(VehicleDetailsDTO vehicleDetailsDTO);

    /**
     * Get all the vehicleDetails.
     *
     * @return the list of entities
     */
    List<VehicleDetailsDTO> findAll();


    /**
     * Get the "id" vehicleDetails.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<VehicleDetailsDTO> findOne(String id);

    /**
     * Delete the "id" vehicleDetails.
     *
     * @param id the id of the entity
     */
    void delete(String id);
}
