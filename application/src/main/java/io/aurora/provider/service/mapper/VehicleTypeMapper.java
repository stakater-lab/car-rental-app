package io.aurora.provider.service.mapper;

import io.aurora.provider.domain.*;
import io.aurora.provider.service.dto.VehicleTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity VehicleType and its DTO VehicleTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface VehicleTypeMapper extends EntityMapper<VehicleTypeDTO, VehicleType> {



    default VehicleType fromId(String id) {
        if (id == null) {
            return null;
        }
        VehicleType vehicleType = new VehicleType();
        vehicleType.setId(id);
        return vehicleType;
    }
}
