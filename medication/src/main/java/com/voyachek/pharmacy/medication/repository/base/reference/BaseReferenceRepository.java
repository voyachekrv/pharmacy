package com.voyachek.pharmacy.medication.repository.base.reference;

import com.voyachek.pharmacy.medication.entity.base.BaseReference;
import com.voyachek.pharmacy.medication.repository.base.BaseRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

/**
 * Базовый репозиторий для справочника
 */
@NoRepositoryBean
public interface BaseReferenceRepository<T extends BaseReference> extends BaseRepository<T> {

    /**
     * Поиск значения справочника по его ключу
     */
    Optional<T> findByKey(String key);
}
