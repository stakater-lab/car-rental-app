package io.aurora.provider.service;

import io.aurora.provider.service.dto.FeedBackDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing FeedBack.
 */
public interface FeedBackService {

    /**
     * Save a feedBack.
     *
     * @param feedBackDTO the entity to save
     * @return the persisted entity
     */
    FeedBackDTO save(FeedBackDTO feedBackDTO);

    /**
     * Get all the feedBacks.
     *
     * @return the list of entities
     */
    List<FeedBackDTO> findAll();


    /**
     * Get the "id" feedBack.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<FeedBackDTO> findOne(String id);

    /**
     * Delete the "id" feedBack.
     *
     * @param id the id of the entity
     */
    void delete(String id);
}
