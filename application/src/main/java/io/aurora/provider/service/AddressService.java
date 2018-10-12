package io.aurora.provider.service;

import io.aurora.provider.service.dto.AddressDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Address.
 */
public interface AddressService {

    /**
     * Save a address.
     *
     * @param addressDTO the entity to save
     * @return the persisted entity
     */
    AddressDTO save(AddressDTO addressDTO);

    /**
     * Get all the addresses.
     *
     * @return the list of entities
     */
    List<AddressDTO> findAll();


    /**
     * Get the "id" address.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<AddressDTO> findOne(String id);

    /**
     * Delete the "id" address.
     *
     * @param id the id of the entity
     */
    void delete(String id);
}
