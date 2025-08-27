package com.voyachek.pharmacy.medication.mapper.base.reference;

import com.voyachek.pharmacy.grpclib.utils.reference.DefaultReferenceUtil;
import com.voyachek.pharmacy.medication.entity.base.BaseReference;

/**
 * Базовый маппер для справочника
 */
public interface DefaultReferenceMapper<T extends BaseReference> {

    /**
     * Преобразование сущности справочника в Protobuf-объект
     * @param entity Сущность справочника {@link BaseReference}
     * @return {@link DefaultReferenceUtil.DefaultReference}
     */
    default DefaultReferenceUtil.DefaultReference toProtobuf(T entity) {
        return DefaultReferenceUtil.DefaultReference.newBuilder()
                .setId(entity.getId().toString())
                .setKey(entity.getKey())
                .setName(entity.getName())
                .setOrdering(entity.getOrdering())
                .build();
    }
}
