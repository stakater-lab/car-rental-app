package io.aurora.provider.service.mapper;

import io.aurora.provider.domain.*;
import io.aurora.provider.service.dto.VehicleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Vehicle and its DTO VehicleDTO.
 */
@Mapper(componentModel = "spring", uses = {RatingMapper.class, VehicleTypeMapper.class, RateMapper.class, VehicleDetailsMapper.class})
public interface VehicleMapper extends EntityMapper<VehicleDTO, Vehicle> {

    @Mapping(source = "rating.id", target = "ratingId")
    @Mapping(source = "type.id", target = "typeId")
    @Mapping(source = "rate.id", target = "rateId")
    @Mapping(source = "detail.id", target = "detailId")
    VehicleDTO toDto(Vehicle vehicle);

    @Mapping(source = "ratingId", target = "rating")
    @Mapping(source = "typeId", target = "type")
    @Mapping(source = "rateId", target = "rate")
    @Mapping(source = "detailId", target = "detail")
    @Mapping(target = "metaData", ignore = true)
    Vehicle toEntity(VehicleDTO vehicleDTO);

    default Vehicle fromId(String id) {
        if (id == null) {
            return null;
        }
        Vehicle vehicle = new Vehicle();
        vehicle.setId(id);
        return vehicle;
    }
}
