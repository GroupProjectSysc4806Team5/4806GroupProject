package com.bookstore.service.mapper;

import com.bookstore.domain.*;
import com.bookstore.service.dto.SaleDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Sale} and its DTO {@link SaleDTO}.
 */
@Mapper(componentModel = "spring", uses = { CartMapper.class })
public interface SaleMapper extends EntityMapper<SaleDTO, Sale> {
    @Mapping(target = "cart", source = "cart", qualifiedByName = "id")
    SaleDTO toDto(Sale s);
}
