package com.voyachek.pharmacy.gateway.mapper.page;

import com.voyachek.pharmacy.gateway.data.utils.PageRequest;
import com.voyachek.pharmacy.grpclib.utils.page.PageUtil;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * Маппер для страниц со списком объектов
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PaginationMapper {

    /**
     * Маппинг GraphQL-страницы в Protobuf-страницу
     * @param pageRequest Объект запроса в виде GraphQL-представления
     * @return Объект Protobuf-страницы
     */
    default PageUtil.PageRequest toPageRequest(PageRequest pageRequest) {
        return PageUtil.PageRequest.newBuilder()
                .setPage(pageRequest.getPage())
                .setSize(pageRequest.getSize())
                .build();
    }
}
