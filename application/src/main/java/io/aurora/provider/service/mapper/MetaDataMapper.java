package io.aurora.provider.service.mapper;

import io.aurora.provider.domain.*;
import io.aurora.provider.service.dto.MetaDataDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MetaData and its DTO MetaDataDTO.
 */
@Mapper(componentModel = "spring", uses = {VehicleMapper.class})
public interface MetaDataMapper extends EntityMapper<MetaDataDTO, MetaData> {

    @Mapping(source = "vehicle.id", target = "vehicleId")
    MetaDataDTO toDto(MetaData metaData);

    @Mapping(source = "vehicleId", target = "vehicle")
    MetaData toEntity(MetaDataDTO metaDataDTO);

    default MetaData fromId(String id) {
        if (id == null) {
            return null;
        }
        MetaData metaData = new MetaData();
        metaData.setId(id);
        return metaData;
    }
}
