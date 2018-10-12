package io.aurora.provider.service.impl;

import io.aurora.provider.service.ProviderService;
import io.aurora.provider.domain.Provider;
import io.aurora.provider.repository.ProviderRepository;
import io.aurora.provider.service.dto.ProviderDTO;
import io.aurora.provider.service.mapper.ProviderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Provider.
 */
@Service
public class ProviderServiceImpl implements ProviderService {

    private final Logger log = LoggerFactory.getLogger(ProviderServiceImpl.class);

    private final ProviderRepository providerRepository;

    private final ProviderMapper providerMapper;

    public ProviderServiceImpl(ProviderRepository providerRepository, ProviderMapper providerMapper) {
        this.providerRepository = providerRepository;
        this.providerMapper = providerMapper;
    }

    /**
     * Save a provider.
     *
     * @param providerDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ProviderDTO save(ProviderDTO providerDTO) {
        log.debug("Request to save Provider : {}", providerDTO);

        Provider provider = providerMapper.toEntity(providerDTO);
        provider = providerRepository.save(provider);
        return providerMapper.toDto(provider);
    }

    /**
     * Get all the providers.
     *
     * @return the list of entities
     */
    @Override
    public List<ProviderDTO> findAll() {
        log.debug("Request to get all Providers");
        return providerRepository.findAll().stream()
            .map(providerMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one provider by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public Optional<ProviderDTO> findOne(String id) {
        log.debug("Request to get Provider : {}", id);
        return providerRepository.findById(id)
            .map(providerMapper::toDto);
    }

    /**
     * Delete the provider by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Provider : {}", id);
        providerRepository.deleteById(id);
    }
}
