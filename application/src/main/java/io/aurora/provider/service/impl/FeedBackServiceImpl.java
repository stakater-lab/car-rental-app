package io.aurora.provider.service.impl;

import io.aurora.provider.service.FeedBackService;
import io.aurora.provider.domain.FeedBack;
import io.aurora.provider.repository.FeedBackRepository;
import io.aurora.provider.service.dto.FeedBackDTO;
import io.aurora.provider.service.mapper.FeedBackMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing FeedBack.
 */
@Service
public class FeedBackServiceImpl implements FeedBackService {

    private final Logger log = LoggerFactory.getLogger(FeedBackServiceImpl.class);

    private final FeedBackRepository feedBackRepository;

    private final FeedBackMapper feedBackMapper;

    public FeedBackServiceImpl(FeedBackRepository feedBackRepository, FeedBackMapper feedBackMapper) {
        this.feedBackRepository = feedBackRepository;
        this.feedBackMapper = feedBackMapper;
    }

    /**
     * Save a feedBack.
     *
     * @param feedBackDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public FeedBackDTO save(FeedBackDTO feedBackDTO) {
        log.debug("Request to save FeedBack : {}", feedBackDTO);

        FeedBack feedBack = feedBackMapper.toEntity(feedBackDTO);
        feedBack = feedBackRepository.save(feedBack);
        return feedBackMapper.toDto(feedBack);
    }

    /**
     * Get all the feedBacks.
     *
     * @return the list of entities
     */
    @Override
    public List<FeedBackDTO> findAll() {
        log.debug("Request to get all FeedBacks");
        return feedBackRepository.findAll().stream()
            .map(feedBackMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one feedBack by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public Optional<FeedBackDTO> findOne(String id) {
        log.debug("Request to get FeedBack : {}", id);
        return feedBackRepository.findById(id)
            .map(feedBackMapper::toDto);
    }

    /**
     * Delete the feedBack by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete FeedBack : {}", id);
        feedBackRepository.deleteById(id);
    }
}
