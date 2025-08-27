package com.voyachek.pharmacy.medication.service.base;

import com.voyachek.pharmacy.grpclib.utils.reference.DefaultReferenceUtil;
import com.voyachek.pharmacy.medication.entity.base.BaseReference;

/**
 * Базовый сервис для справочника
 */
public interface BaseReferenceService<T extends BaseReference> {

    /**
     * Поиск значения справочника по его ключу
     * @param key Ключ записи справочника
     * @return {@link DefaultReferenceUtil.DefaultReference}
     */
    DefaultReferenceUtil.DefaultReference findByKey(String key);

    /**
     * Поиск значения справочника по его ключу в виде JPA-сущности
     * @param key Ключ записи справочника
     * @return {@link BaseReference}
     */
    T findEntityByKey(String key);

    /**
     * Поиск списка значений справочника, отфильтрованных по строке имени
     * @param request Запрос на фильтрацию по имени
     * @return {@link DefaultReferenceUtil.DefaultReferenceCollection}
     */
    DefaultReferenceUtil.DefaultReferenceCollection findAll(DefaultReferenceUtil.DefaultReferenceQuery request);
}
