package io.aurora.provider.service.impl;

import io.aurora.provider.service.VehicleModelService;
import io.aurora.provider.domain.VehicleModel;
import io.aurora.provider.repository.VehicleModelRepository;
import io.aurora.provider.service.dto.VehicleModelDTO;
import io.aurora.provider.service.mapper.VehicleModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing VehicleModel.
 */
@Service
public class VehicleModelServiceImpl implements VehicleModelService {

    private final Logger log = LoggerFactory.getLogger(VehicleModelServiceImpl.class);

    private final VehicleModelRepository vehicleModelRepository;

    private final VehicleModelMapper vehicleModelMapper;

    public VehicleModelServiceImpl(VehicleModelRepository vehicleModelRepository, VehicleModelMapper vehicleModelMapper) {
        this.vehicleModelRepository = vehicleModelRepository;
        this.vehicleModelMapper = vehicleModelMapper;
    }

    /**
     * Save a vehicleModel.
     *
     * @param vehicleModelDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public VehicleModelDTO save(VehicleModelDTO vehicleModelDTO) {
        log.debug("Request to save VehicleModel : {}", vehicleModelDTO);

        VehicleModel vehicleModel = vehicleModelMapper.toEntity(vehicleModelDTO);
        vehicleModel = vehicleModelRepository.save(vehicleModel);
        return vehicleModelMapper.toDto(vehicleModel);
    }

    /**
     * Get all the vehicleModels.
     *
     * @return the list of entities
     */
    @Override
    public List<VehicleModelDTO> findAll() {
        log.debug("Request to get all VehicleModels");
        return vehicleModelRepository.findAll().stream()
            .map(vehicleModelMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one vehicleModel by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public Optional<VehicleModelDTO> findOne(String id) {
        log.debug("Request to get VehicleModel : {}", id);
        return vehicleModelRepository.findById(id)
            .map(vehicleModelMapper::toDto);
    }

    /**
     * Delete the vehicleModel by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete VehicleModel : {}", id);
        vehicleModelRepository.deleteById(id);
    }
}
