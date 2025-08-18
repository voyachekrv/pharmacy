package com.voyachek.pharmacy.gateway.mapper.page;

import com.voyachek.pharmacy.gateway.data.utils.PageRequest;
import com.voyachek.pharmacy.grpclib.utils.page.PageUtil;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PaginationMapper {

    default PageUtil.PageRequest toPageRequest(PageRequest pageRequest) {
        return PageUtil.PageRequest.newBuilder()
                .setPage(pageRequest.getPage())
                .setSize(pageRequest.getSize())
                .build();
    }
}
