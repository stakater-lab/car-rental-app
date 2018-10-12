package io.aurora.provider.service.mapper;

import io.aurora.provider.domain.*;
import io.aurora.provider.service.dto.FeedBackDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity FeedBack and its DTO FeedBackDTO.
 */
@Mapper(componentModel = "spring", uses = {RatingMapper.class})
public interface FeedBackMapper extends EntityMapper<FeedBackDTO, FeedBack> {

    @Mapping(source = "rating.id", target = "ratingId")
    FeedBackDTO toDto(FeedBack feedBack);

    @Mapping(source = "ratingId", target = "rating")
    FeedBack toEntity(FeedBackDTO feedBackDTO);

    default FeedBack fromId(String id) {
        if (id == null) {
            return null;
        }
        FeedBack feedBack = new FeedBack();
        feedBack.setId(id);
        return feedBack;
    }
}
