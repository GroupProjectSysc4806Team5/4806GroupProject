package com.bookstore.service.mapper;

import com.bookstore.domain.*;
import com.bookstore.service.dto.BookDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Book} and its DTO {@link BookDTO}.
 */
@Mapper(componentModel = "spring", uses = { BookstoreMapper.class })
public interface BookMapper extends EntityMapper<BookDTO, Book> {
    @Mapping(target = "bookstore", source = "bookstore", qualifiedByName = "id")
    BookDTO toDto(Book s);

    @Named("idSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    Set<BookDTO> toDtoIdSet(Set<Book> book);
}
