package io.aurora.provider.service.impl;

import io.aurora.provider.service.VehicleTypeService;
import io.aurora.provider.domain.VehicleType;
import io.aurora.provider.repository.VehicleTypeRepository;
import io.aurora.provider.service.dto.VehicleTypeDTO;
import io.aurora.provider.service.mapper.VehicleTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing VehicleType.
 */
@Service
public class VehicleTypeServiceImpl implements VehicleTypeService {

    private final Logger log = LoggerFactory.getLogger(VehicleTypeServiceImpl.class);

    private final VehicleTypeRepository vehicleTypeRepository;

    private final VehicleTypeMapper vehicleTypeMapper;

    public VehicleTypeServiceImpl(VehicleTypeRepository vehicleTypeRepository, VehicleTypeMapper vehicleTypeMapper) {
        this.vehicleTypeRepository = vehicleTypeRepository;
        this.vehicleTypeMapper = vehicleTypeMapper;
    }

    /**
     * Save a vehicleType.
     *
     * @param vehicleTypeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public VehicleTypeDTO save(VehicleTypeDTO vehicleTypeDTO) {
        log.debug("Request to save VehicleType : {}", vehicleTypeDTO);

        VehicleType vehicleType = vehicleTypeMapper.toEntity(vehicleTypeDTO);
        vehicleType = vehicleTypeRepository.save(vehicleType);
        return vehicleTypeMapper.toDto(vehicleType);
    }

    /**
     * Get all the vehicleTypes.
     *
     * @return the list of entities
     */
    @Override
    public List<VehicleTypeDTO> findAll() {
        log.debug("Request to get all VehicleTypes");
        return vehicleTypeRepository.findAll().stream()
            .map(vehicleTypeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one vehicleType by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public Optional<VehicleTypeDTO> findOne(String id) {
        log.debug("Request to get VehicleType : {}", id);
        return vehicleTypeRepository.findById(id)
            .map(vehicleTypeMapper::toDto);
    }

    /**
     * Delete the vehicleType by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete VehicleType : {}", id);
        vehicleTypeRepository.deleteById(id);
    }
}
