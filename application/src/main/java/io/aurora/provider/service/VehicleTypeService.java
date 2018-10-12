package io.aurora.provider.service;

import io.aurora.provider.service.dto.VehicleTypeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing VehicleType.
 */
public interface VehicleTypeService {

    /**
     * Save a vehicleType.
     *
     * @param vehicleTypeDTO the entity to save
     * @return the persisted entity
     */
    VehicleTypeDTO save(VehicleTypeDTO vehicleTypeDTO);

    /**
     * Get all the vehicleTypes.
     *
     * @return the list of entities
     */
    List<VehicleTypeDTO> findAll();


    /**
     * Get the "id" vehicleType.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<VehicleTypeDTO> findOne(String id);

    /**
     * Delete the "id" vehicleType.
     *
     * @param id the id of the entity
     */
    void delete(String id);
}
