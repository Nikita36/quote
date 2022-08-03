package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Quote;
import com.mycompany.myapp.domain.UserData;
import com.mycompany.myapp.service.dto.QuoteDTO;
import com.mycompany.myapp.service.dto.UserDataDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Quote} and its DTO {@link QuoteDTO}.
 */
@Mapper(componentModel = "spring", uses = { QuoteRatingMapper.class, UserDataMapper.class })
public interface QuoteMapper extends EntityMapper<QuoteDTO, Quote> {
    QuoteDTO toDto(Quote s);
}
