package io.aurora.provider.service;

import io.aurora.provider.service.dto.MetaDataDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing MetaData.
 */
public interface MetaDataService {

    /**
     * Save a metaData.
     *
     * @param metaDataDTO the entity to save
     * @return the persisted entity
     */
    MetaDataDTO save(MetaDataDTO metaDataDTO);

    /**
     * Get all the metaData.
     *
     * @return the list of entities
     */
    List<MetaDataDTO> findAll();


    /**
     * Get the "id" metaData.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<MetaDataDTO> findOne(String id);

    /**
     * Delete the "id" metaData.
     *
     * @param id the id of the entity
     */
    void delete(String id);
}
