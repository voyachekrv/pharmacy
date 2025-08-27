package com.voyachek.pharmacy.gateway.mapper.reference;

import com.voyachek.pharmacy.gateway.data.utils.Reference;
import com.voyachek.pharmacy.gateway.data.utils.ReferenceQuery;
import com.voyachek.pharmacy.grpclib.utils.reference.DefaultReferenceUtil;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.Optional;

/**
 * Маппер для объектов справочника
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DefaultReferenceMapper {

    default DefaultReferenceUtil.DefaultReferenceKey toKeyRequest(String key) {
        return DefaultReferenceUtil.DefaultReferenceKey.newBuilder()
                .setKey(key)
                .build();
    }

    default DefaultReferenceUtil.DefaultReferenceQuery toQuery(ReferenceQuery query) {
       return DefaultReferenceUtil.DefaultReferenceQuery.newBuilder()
               .setName(Optional.of(query.getName()).orElse(""))
               .build();
    }

    Reference toReference(DefaultReferenceUtil.DefaultReference reference);
}
