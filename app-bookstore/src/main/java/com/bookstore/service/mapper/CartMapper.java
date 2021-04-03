package com.bookstore.service.mapper;

import com.bookstore.domain.*;
import com.bookstore.service.dto.CartDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Cart} and its DTO {@link CartDTO}.
 */
@Mapper(componentModel = "spring", uses = { CustomerMapper.class, BookMapper.class })
public interface CartMapper extends EntityMapper<CartDTO, Cart> {
    @Mapping(target = "customer", source = "customer", qualifiedByName = "id")
    @Mapping(target = "books", source = "books", qualifiedByName = "idSet")
    CartDTO toDto(Cart s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CartDTO toDtoId(Cart cart);

    @Mapping(target = "removeBook", ignore = true)
    Cart toEntity(CartDTO cartDTO);
}
