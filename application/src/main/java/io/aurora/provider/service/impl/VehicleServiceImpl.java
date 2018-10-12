package io.aurora.provider.service.impl;

import io.aurora.provider.service.VehicleService;
import io.aurora.provider.domain.Vehicle;
import io.aurora.provider.repository.VehicleRepository;
import io.aurora.provider.service.dto.VehicleDTO;
import io.aurora.provider.service.mapper.VehicleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Vehicle.
 */
@Service
public class VehicleServiceImpl implements VehicleService {

    private final Logger log = LoggerFactory.getLogger(VehicleServiceImpl.class);

    private final VehicleRepository vehicleRepository;

    private final VehicleMapper vehicleMapper;

    public VehicleServiceImpl(VehicleRepository vehicleRepository, VehicleMapper vehicleMapper) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleMapper = vehicleMapper;
    }

    /**
     * Save a vehicle.
     *
     * @param vehicleDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public VehicleDTO save(VehicleDTO vehicleDTO) {
        log.debug("Request to save Vehicle : {}", vehicleDTO);

        Vehicle vehicle = vehicleMapper.toEntity(vehicleDTO);
        vehicle = vehicleRepository.save(vehicle);
        return vehicleMapper.toDto(vehicle);
    }

    /**
     * Get all the vehicles.
     *
     * @return the list of entities
     */
    @Override
    public List<VehicleDTO> findAll() {
        log.debug("Request to get all Vehicles");
        return vehicleRepository.findAll().stream()
            .map(vehicleMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one vehicle by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public Optional<VehicleDTO> findOne(String id) {
        log.debug("Request to get Vehicle : {}", id);
        return vehicleRepository.findById(id)
            .map(vehicleMapper::toDto);
    }

    /**
     * Delete the vehicle by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Vehicle : {}", id);
        vehicleRepository.deleteById(id);
    }
}
