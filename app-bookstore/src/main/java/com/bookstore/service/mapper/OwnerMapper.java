package com.bookstore.service.mapper;

import com.bookstore.domain.*;
import com.bookstore.service.dto.OwnerDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Owner} and its DTO {@link OwnerDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface OwnerMapper extends EntityMapper<OwnerDTO, Owner> {
    @Mapping(target = "user", source = "user", qualifiedByName = "id")
    OwnerDTO toDto(Owner s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OwnerDTO toDtoId(Owner owner);
}
