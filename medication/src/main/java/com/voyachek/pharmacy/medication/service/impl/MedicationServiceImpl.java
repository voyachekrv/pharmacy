package com.voyachek.pharmacy.medication.service.impl;

import com.voyachek.pharmacy.grpclib.medication.MedicationCreateContract;
import com.voyachek.pharmacy.grpclib.medication.MedicationFindAllContract;
import com.voyachek.pharmacy.grpclib.medication.MedicationRemoveContract;
import com.voyachek.pharmacy.grpclib.medication.MedicationUpdateContract;
import com.voyachek.pharmacy.grpclib.utils.search.SearchUtil;
import com.voyachek.pharmacy.medication.entity.Medication;
import com.voyachek.pharmacy.medication.entity.MedicationCategory;
import com.voyachek.pharmacy.medication.entity.MedicationSubstance;
import com.voyachek.pharmacy.medication.entity.pkey.MedicationSubstancePKey;
import com.voyachek.pharmacy.medication.mapper.MedicationMapper;
import com.voyachek.pharmacy.medication.repository.MedicationRepository;
import com.voyachek.pharmacy.medication.service.MedicationService;
import com.voyachek.pharmacy.medication.service.reference.RefCategoryService;
import com.voyachek.pharmacy.medication.service.reference.RefSubstanceService;
import com.voyachek.pharmacy.medication.specification.MedicationSpecificationApplier;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MedicationServiceImpl implements MedicationService {

    private final MedicationRepository medicationRepository;
    private final MedicationMapper medicationMapper;
    private final RefCategoryService refCategoryService;
    private final RefSubstanceService refSubstanceService;
    private final MedicationSpecificationApplier medicationSpecificationApplier;

    @Override
    @Transactional
    public MedicationCreateContract.MedicationCreateResponse create(MedicationCreateContract.MedicationCreateRequest request) {
        var medication = Medication.builder()
                .name(request.getName())
                .price(BigDecimal.valueOf(request.getPrice()))
                .isPrescriptionOnly(request.getIsPrescriptionOnly())
                .build();

        var newMedication = medicationRepository.save(medication);

        createCategories(request, newMedication);
        createActiveSubstances(request, newMedication);

        var medicationWithNestedData = medicationRepository.save(newMedication);

        return medicationMapper.toCreationResult(medicationWithNestedData);
    }

    @Override
    @Transactional
    public MedicationRemoveContract.MedicationRemoveResponse remove(MedicationRemoveContract.MedicationRemoveRequest request) {
        var entity = medicationRepository
                .findById(UUID.fromString(request.getId()))
                .orElseThrow(() -> new StatusRuntimeException(Status.NOT_FOUND));

        medicationRepository.delete(entity);

        return this.medicationMapper.toRemoveResult(entity);
    }

    @Override
    @Transactional
    public MedicationUpdateContract.MedicationUpdatePriceResponse updatePrice(MedicationUpdateContract.MedicationUpdatePriceRequest request) {
        var entity = medicationRepository
                .findById(UUID.fromString(request.getId()))
                .orElseThrow(() -> new StatusRuntimeException(Status.NOT_FOUND));

        var newPrice = BigDecimal.valueOf(request.getNewPrice());

        if (!entity.getPrice().equals(newPrice)) {
            entity.setOldPrice(entity.getPrice());
            entity.setPrice(newPrice);
        }

        var updatedEntity = medicationRepository.save(entity);

        return medicationMapper.toUpdatePriceResult(updatedEntity);
    }

    @Override
    public MedicationFindAllContract.MedicationFindAllResponse findAll(MedicationFindAllContract.MedicationFindAllRequest request) {
        var page = medicationRepository.findAll(medicationSpecificationApplier.apply(request.getFilter()),
                PageRequest.of(request.getPagination().getPage(), request.getPagination().getSize(),
                        applySorting(request)));

        return medicationMapper.toProtobufPage(page);
    }

    private void createCategories(MedicationCreateContract.MedicationCreateRequest request,
                                                      Medication medication) {
        for (var category: request.getMedicationCategoriesList()) {
            medication.getMedicationCategories().add(createCategory(category, medication));
        }
    }

    private MedicationCategory createCategory(String key, Medication medication) {
        var category = refCategoryService.findEntityByKey(key);
        return MedicationCategory.builder()
                .category(category)
                .medication(medication)
                .build();
    }

    private void createActiveSubstances(MedicationCreateContract.MedicationCreateRequest request,
                                        Medication medication) {
        for (var activeSubstance: request.getActiveSubstancesList()) {
            medication.getActiveSubstances().add(createActiveSubstance(activeSubstance.getRefSubstanceKey(),
                    BigDecimal.valueOf(activeSubstance.getConcentration()),
                    medication));
        }
    }

    private MedicationSubstance createActiveSubstance(String substanceKey, BigDecimal concentration, Medication medication) {
        var substance = refSubstanceService.findEntityByKey(substanceKey);
        return MedicationSubstance.builder()
                .id(new MedicationSubstancePKey(medication.getId(), substance.getId()))
                .refSubstance(substance)
                .medication(medication)
                .concentration(concentration)
                .build();
    }

    private Sort applySorting(MedicationFindAllContract.MedicationFindAllRequest request) {
        var direction = Sort.Direction.DESC;

        if (request.hasSort()) {
            if (request.getSort().getDirection().equals(SearchUtil.SortDirection.ASC)) {
                direction = Sort.Direction.ASC;
            }

            return  Sort.by(direction, request.getSort().getField());
        }

        return Sort.by(direction, "createdAt");
    }
}
