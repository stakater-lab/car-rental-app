package io.aurora.provider.service.impl;

import io.aurora.provider.service.VehicleDetailsService;
import io.aurora.provider.domain.VehicleDetails;
import io.aurora.provider.repository.VehicleDetailsRepository;
import io.aurora.provider.service.dto.VehicleDetailsDTO;
import io.aurora.provider.service.mapper.VehicleDetailsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing VehicleDetails.
 */
@Service
public class VehicleDetailsServiceImpl implements VehicleDetailsService {

    private final Logger log = LoggerFactory.getLogger(VehicleDetailsServiceImpl.class);

    private final VehicleDetailsRepository vehicleDetailsRepository;

    private final VehicleDetailsMapper vehicleDetailsMapper;

    public VehicleDetailsServiceImpl(VehicleDetailsRepository vehicleDetailsRepository, VehicleDetailsMapper vehicleDetailsMapper) {
        this.vehicleDetailsRepository = vehicleDetailsRepository;
        this.vehicleDetailsMapper = vehicleDetailsMapper;
    }

    /**
     * Save a vehicleDetails.
     *
     * @param vehicleDetailsDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public VehicleDetailsDTO save(VehicleDetailsDTO vehicleDetailsDTO) {
        log.debug("Request to save VehicleDetails : {}", vehicleDetailsDTO);

        VehicleDetails vehicleDetails = vehicleDetailsMapper.toEntity(vehicleDetailsDTO);
        vehicleDetails = vehicleDetailsRepository.save(vehicleDetails);
        return vehicleDetailsMapper.toDto(vehicleDetails);
    }

    /**
     * Get all the vehicleDetails.
     *
     * @return the list of entities
     */
    @Override
    public List<VehicleDetailsDTO> findAll() {
        log.debug("Request to get all VehicleDetails");
        return vehicleDetailsRepository.findAll().stream()
            .map(vehicleDetailsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one vehicleDetails by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public Optional<VehicleDetailsDTO> findOne(String id) {
        log.debug("Request to get VehicleDetails : {}", id);
        return vehicleDetailsRepository.findById(id)
            .map(vehicleDetailsMapper::toDto);
    }

    /**
     * Delete the vehicleDetails by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete VehicleDetails : {}", id);
        vehicleDetailsRepository.deleteById(id);
    }
}
