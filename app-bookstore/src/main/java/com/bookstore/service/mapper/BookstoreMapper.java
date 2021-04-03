package com.bookstore.service.mapper;

import com.bookstore.domain.*;
import com.bookstore.service.dto.BookstoreDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Bookstore} and its DTO {@link BookstoreDTO}.
 */
@Mapper(componentModel = "spring", uses = { OwnerMapper.class })
public interface BookstoreMapper extends EntityMapper<BookstoreDTO, Bookstore> {
    @Mapping(target = "owner", source = "owner", qualifiedByName = "id")
    BookstoreDTO toDto(Bookstore s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BookstoreDTO toDtoId(Bookstore bookstore);
}
