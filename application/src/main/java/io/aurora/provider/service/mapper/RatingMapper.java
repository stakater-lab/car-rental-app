package io.aurora.provider.service.mapper;

import io.aurora.provider.domain.*;
import io.aurora.provider.service.dto.RatingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Rating and its DTO RatingDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RatingMapper extends EntityMapper<RatingDTO, Rating> {


    @Mapping(target = "feedbacks", ignore = true)
    Rating toEntity(RatingDTO ratingDTO);

    default Rating fromId(String id) {
        if (id == null) {
            return null;
        }
        Rating rating = new Rating();
        rating.setId(id);
        return rating;
    }
}
