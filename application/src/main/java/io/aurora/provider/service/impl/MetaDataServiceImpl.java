package io.aurora.provider.service.impl;

import io.aurora.provider.service.MetaDataService;
import io.aurora.provider.domain.MetaData;
import io.aurora.provider.repository.MetaDataRepository;
import io.aurora.provider.service.dto.MetaDataDTO;
import io.aurora.provider.service.mapper.MetaDataMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing MetaData.
 */
@Service
public class MetaDataServiceImpl implements MetaDataService {

    private final Logger log = LoggerFactory.getLogger(MetaDataServiceImpl.class);

    private final MetaDataRepository metaDataRepository;

    private final MetaDataMapper metaDataMapper;

    public MetaDataServiceImpl(MetaDataRepository metaDataRepository, MetaDataMapper metaDataMapper) {
        this.metaDataRepository = metaDataRepository;
        this.metaDataMapper = metaDataMapper;
    }

    /**
     * Save a metaData.
     *
     * @param metaDataDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MetaDataDTO save(MetaDataDTO metaDataDTO) {
        log.debug("Request to save MetaData : {}", metaDataDTO);

        MetaData metaData = metaDataMapper.toEntity(metaDataDTO);
        metaData = metaDataRepository.save(metaData);
        return metaDataMapper.toDto(metaData);
    }

    /**
     * Get all the metaData.
     *
     * @return the list of entities
     */
    @Override
    public List<MetaDataDTO> findAll() {
        log.debug("Request to get all MetaData");
        return metaDataRepository.findAll().stream()
            .map(metaDataMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one metaData by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public Optional<MetaDataDTO> findOne(String id) {
        log.debug("Request to get MetaData : {}", id);
        return metaDataRepository.findById(id)
            .map(metaDataMapper::toDto);
    }

    /**
     * Delete the metaData by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete MetaData : {}", id);
        metaDataRepository.deleteById(id);
    }
}
