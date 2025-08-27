package com.voyachek.pharmacy.medication.repository.reference;

import com.voyachek.pharmacy.medication.entity.reference.RefCategory;
import com.voyachek.pharmacy.medication.repository.base.reference.BaseReferenceRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для справочника "Категория препарата"
 */
@Repository
public interface RefCategoryRepository extends BaseReferenceRepository<RefCategory> {}
