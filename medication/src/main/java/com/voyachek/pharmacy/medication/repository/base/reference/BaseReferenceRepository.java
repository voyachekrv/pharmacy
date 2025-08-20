package com.voyachek.pharmacy.medication.repository.base.reference;

import com.voyachek.pharmacy.medication.entity.base.BaseReference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;
import java.util.UUID;

/**
 * Базовый репозиторий для справочника
 */
@NoRepositoryBean
public interface BaseReferenceRepository<T extends BaseReference> extends
        JpaRepository<T, UUID>, JpaSpecificationExecutor<T> {

    /**
     * Поиск значения справочника по его ключу
     */
    Optional<T> findByKey(String key);
}
