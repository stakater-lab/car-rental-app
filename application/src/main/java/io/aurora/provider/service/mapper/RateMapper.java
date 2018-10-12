package io.aurora.provider.service.mapper;

import io.aurora.provider.domain.*;
import io.aurora.provider.service.dto.RateDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Rate and its DTO RateDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RateMapper extends EntityMapper<RateDTO, Rate> {



    default Rate fromId(String id) {
        if (id == null) {
            return null;
        }
        Rate rate = new Rate();
        rate.setId(id);
        return rate;
    }
}
