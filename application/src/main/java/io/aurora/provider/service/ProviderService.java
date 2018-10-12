package io.aurora.provider.service;

import io.aurora.provider.service.dto.ProviderDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Provider.
 */
public interface ProviderService {

    /**
     * Save a provider.
     *
     * @param providerDTO the entity to save
     * @return the persisted entity
     */
    ProviderDTO save(ProviderDTO providerDTO);

    /**
     * Get all the providers.
     *
     * @return the list of entities
     */
    List<ProviderDTO> findAll();


    /**
     * Get the "id" provider.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ProviderDTO> findOne(String id);

    /**
     * Delete the "id" provider.
     *
     * @param id the id of the entity
     */
    void delete(String id);
}
