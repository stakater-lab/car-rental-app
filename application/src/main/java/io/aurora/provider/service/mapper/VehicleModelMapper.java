package io.aurora.provider.service.mapper;

import io.aurora.provider.domain.*;
import io.aurora.provider.service.dto.VehicleModelDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity VehicleModel and its DTO VehicleModelDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface VehicleModelMapper extends EntityMapper<VehicleModelDTO, VehicleModel> {



    default VehicleModel fromId(String id) {
        if (id == null) {
            return null;
        }
        VehicleModel vehicleModel = new VehicleModel();
        vehicleModel.setId(id);
        return vehicleModel;
    }
}
