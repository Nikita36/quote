package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.QuoteRating;
import com.mycompany.myapp.domain.UserData;
import com.mycompany.myapp.service.dto.QuoteRatingDTO;
import com.mycompany.myapp.service.dto.UserDataDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link QuoteRating} and its DTO {@link QuoteRatingDTO}.
 */
@Mapper(componentModel = "spring")
public interface QuoteRatingMapper extends EntityMapper<QuoteRatingDTO, QuoteRating> {
    @Mapping(target = "user.id", source = "user.id")
    QuoteRatingDTO toDto(QuoteRating s);
}
