package com.voyachek.pharmacy.medication.service.reference.impl;

import com.voyachek.pharmacy.grpclib.utils.reference.DefaultReferenceUtil;
import com.voyachek.pharmacy.medication.entity.reference.RefSubstance;
import com.voyachek.pharmacy.medication.mapper.reference.RefSubstanceMapper;
import com.voyachek.pharmacy.medication.repository.reference.RefSubstanceRepository;
import com.voyachek.pharmacy.medication.service.reference.RefSubstanceService;
import com.voyachek.pharmacy.medication.specification.base.reference.DefaultReferenceSpecifications;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Сервис для справочника типа "Действующее вещество"
 */
@Service
@RequiredArgsConstructor
public class RefSubstanceServiceImpl implements RefSubstanceService {

    private final RefSubstanceRepository refSubstanceRepository;
    private final RefSubstanceMapper refSubstanceMapper;
    private final EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public DefaultReferenceUtil.DefaultReference findByKey(String key) {
        return this.refSubstanceMapper.toProtobuf(this.findEntityByKey(key));
    }

    @Override
    @Transactional(readOnly = true)
    public RefSubstance findEntityByKey(String key) {
        var entity = refSubstanceRepository
                .findByKey(key)
                .orElseThrow(() -> new StatusRuntimeException(Status.NOT_FOUND));

        return entityManager.getReference(RefSubstance.class, entity.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public DefaultReferenceUtil.DefaultReferenceCollection findAll(DefaultReferenceUtil.DefaultReferenceQuery request) {
        var values = refSubstanceRepository
                .findAll(DefaultReferenceSpecifications.nameLike(request.getName()),
                        Sort.by(Sort.Direction.ASC, "ordering"))
                .stream().map(refSubstanceMapper::toProtobuf)
                .toList();

        return DefaultReferenceUtil.DefaultReferenceCollection.newBuilder()
                .addAllContent(values)
                .build();
    }
}
