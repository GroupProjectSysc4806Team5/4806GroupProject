package com.bookstore.service.mapper;

import com.bookstore.domain.*;
import com.bookstore.service.dto.SaleDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Sale} and its DTO {@link SaleDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SaleMapper extends EntityMapper<SaleDTO, Sale> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SaleDTO toDtoId(Sale sale);
}
