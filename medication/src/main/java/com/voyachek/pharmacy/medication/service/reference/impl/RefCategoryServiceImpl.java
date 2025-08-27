package com.voyachek.pharmacy.medication.service.reference.impl;

import com.voyachek.pharmacy.grpclib.utils.reference.DefaultReferenceUtil;
import com.voyachek.pharmacy.medication.entity.base.BaseReference;
import com.voyachek.pharmacy.medication.entity.reference.RefCategory;
import com.voyachek.pharmacy.medication.mapper.reference.RefCategoryMapper;
import com.voyachek.pharmacy.medication.repository.reference.RefCategoryRepository;
import com.voyachek.pharmacy.medication.service.reference.RefCategoryService;
import com.voyachek.pharmacy.medication.specification.base.reference.DefaultReferenceSpecifications;
import com.voyachek.pharmacy.medication.specification.reference.RefCategorySpecificationApplier;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Сервис для справочника типа "Категория препарата"
 */
@Service
@RequiredArgsConstructor
public class RefCategoryServiceImpl implements RefCategoryService {

    private final RefCategoryRepository refCategoryRepository;
    private final RefCategoryMapper refCategoryMapper;
    private final RefCategorySpecificationApplier refCategorySpecificationApplier;
    private final EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public DefaultReferenceUtil.DefaultReference findByKey(String key) {
        return this.refCategoryMapper.toProtobuf(this.findEntityByKey(key));
    }

    @Override
    @Transactional(readOnly = true)
    public RefCategory findEntityByKey(String key) {
       var entity = refCategoryRepository
                .findByKey(key)
                .orElseThrow(() -> new StatusRuntimeException(Status.NOT_FOUND));

       return entityManager.getReference(RefCategory.class, entity.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public DefaultReferenceUtil.DefaultReferenceCollection findAll(DefaultReferenceUtil.DefaultReferenceQuery request) {
        var values = refCategoryRepository
                .findAll(refCategorySpecificationApplier.applyDefault(request),
                        Sort.by(Sort.Direction.ASC, "ordering"))
                .stream().map(refCategoryMapper::toProtobuf)
                .toList();

        return DefaultReferenceUtil.DefaultReferenceCollection.newBuilder()
                .addAllContent(values)
                .build();
    }
}
