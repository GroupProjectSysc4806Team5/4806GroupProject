package com.bookstore.service.mapper;

import com.bookstore.domain.*;
import com.bookstore.service.dto.CustomerDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Customer} and its DTO {@link CustomerDTO}.
 */
@Mapper(componentModel = "spring", uses = { CartMapper.class, UserMapper.class })
public interface CustomerMapper extends EntityMapper<CustomerDTO, Customer> {
    @Mapping(target = "cart", source = "cart", qualifiedByName = "id")
    @Mapping(target = "user", source = "user", qualifiedByName = "id")
    CustomerDTO toDto(Customer s);
}
