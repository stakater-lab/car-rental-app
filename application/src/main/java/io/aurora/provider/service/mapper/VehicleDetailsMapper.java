package io.aurora.provider.service.mapper;

import io.aurora.provider.domain.*;
import io.aurora.provider.service.dto.VehicleDetailsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity VehicleDetails and its DTO VehicleDetailsDTO.
 */
@Mapper(componentModel = "spring", uses = {VehicleModelMapper.class})
public interface VehicleDetailsMapper extends EntityMapper<VehicleDetailsDTO, VehicleDetails> {

    @Mapping(source = "model.id", target = "modelId")
    VehicleDetailsDTO toDto(VehicleDetails vehicleDetails);

    @Mapping(source = "modelId", target = "model")
    VehicleDetails toEntity(VehicleDetailsDTO vehicleDetailsDTO);

    default VehicleDetails fromId(String id) {
        if (id == null) {
            return null;
        }
        VehicleDetails vehicleDetails = new VehicleDetails();
        vehicleDetails.setId(id);
        return vehicleDetails;
    }
}
