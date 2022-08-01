package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Quote;
import com.mycompany.myapp.domain.UserData;
import com.mycompany.myapp.service.dto.QuoteDTO;
import com.mycompany.myapp.service.dto.UserDataDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Quote} and its DTO {@link QuoteDTO}.
 */
@Mapper(componentModel = "spring")
public interface QuoteMapper extends EntityMapper<QuoteDTO, Quote> {
    @Mapping(target = "author", source = "author", qualifiedByName = "userDataId")
    QuoteDTO toDto(Quote s);

    @Named("userDataId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDataDTO toDtoUserDataId(UserData userData);
}
