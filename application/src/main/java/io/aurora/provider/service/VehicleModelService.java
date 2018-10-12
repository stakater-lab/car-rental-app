package io.aurora.provider.service;

import io.aurora.provider.service.dto.VehicleModelDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing VehicleModel.
 */
public interface VehicleModelService {

    /**
     * Save a vehicleModel.
     *
     * @param vehicleModelDTO the entity to save
     * @return the persisted entity
     */
    VehicleModelDTO save(VehicleModelDTO vehicleModelDTO);

    /**
     * Get all the vehicleModels.
     *
     * @return the list of entities
     */
    List<VehicleModelDTO> findAll();


    /**
     * Get the "id" vehicleModel.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<VehicleModelDTO> findOne(String id);

    /**
     * Delete the "id" vehicleModel.
     *
     * @param id the id of the entity
     */
    void delete(String id);
}
