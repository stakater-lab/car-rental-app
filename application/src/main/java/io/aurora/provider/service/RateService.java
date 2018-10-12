package io.aurora.provider.service;

import io.aurora.provider.service.dto.RateDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Rate.
 */
public interface RateService {

    /**
     * Save a rate.
     *
     * @param rateDTO the entity to save
     * @return the persisted entity
     */
    RateDTO save(RateDTO rateDTO);

    /**
     * Get all the rates.
     *
     * @return the list of entities
     */
    List<RateDTO> findAll();


    /**
     * Get the "id" rate.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<RateDTO> findOne(String id);

    /**
     * Delete the "id" rate.
     *
     * @param id the id of the entity
     */
    void delete(String id);
}
