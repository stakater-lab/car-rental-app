package io.aurora.provider.service.impl;

import io.aurora.provider.service.RateService;
import io.aurora.provider.domain.Rate;
import io.aurora.provider.repository.RateRepository;
import io.aurora.provider.service.dto.RateDTO;
import io.aurora.provider.service.mapper.RateMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Rate.
 */
@Service
public class RateServiceImpl implements RateService {

    private final Logger log = LoggerFactory.getLogger(RateServiceImpl.class);

    private final RateRepository rateRepository;

    private final RateMapper rateMapper;

    public RateServiceImpl(RateRepository rateRepository, RateMapper rateMapper) {
        this.rateRepository = rateRepository;
        this.rateMapper = rateMapper;
    }

    /**
     * Save a rate.
     *
     * @param rateDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RateDTO save(RateDTO rateDTO) {
        log.debug("Request to save Rate : {}", rateDTO);

        Rate rate = rateMapper.toEntity(rateDTO);
        rate = rateRepository.save(rate);
        return rateMapper.toDto(rate);
    }

    /**
     * Get all the rates.
     *
     * @return the list of entities
     */
    @Override
    public List<RateDTO> findAll() {
        log.debug("Request to get all Rates");
        return rateRepository.findAll().stream()
            .map(rateMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one rate by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public Optional<RateDTO> findOne(String id) {
        log.debug("Request to get Rate : {}", id);
        return rateRepository.findById(id)
            .map(rateMapper::toDto);
    }

    /**
     * Delete the rate by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Rate : {}", id);
        rateRepository.deleteById(id);
    }
}
