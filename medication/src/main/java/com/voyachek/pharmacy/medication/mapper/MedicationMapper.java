package com.voyachek.pharmacy.medication.mapper;

import com.voyachek.pharmacy.grpclib.medication.MedicationCreateContract;
import com.voyachek.pharmacy.grpclib.medication.MedicationFindAllContract;
import com.voyachek.pharmacy.grpclib.medication.MedicationRemoveContract;
import com.voyachek.pharmacy.grpclib.medication.MedicationUpdateContract;
import com.voyachek.pharmacy.grpclib.utils.TimeUtil;
import com.voyachek.pharmacy.grpclib.utils.page.PageUtil;
import com.voyachek.pharmacy.medication.entity.Medication;
import com.voyachek.pharmacy.medication.mapper.reference.RefCategoryMapper;
import com.voyachek.pharmacy.medication.mapper.reference.RefCategoryMapperImpl;
import com.voyachek.pharmacy.medication.mapper.reference.RefSubstanceMapper;
import com.voyachek.pharmacy.medication.mapper.reference.RefSubstanceMapperImpl;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.Optional;

/**
 * Маппер для сущности "Препарат"
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {RefCategoryMapper.class, RefSubstanceMapper.class})
public interface MedicationMapper {

    @Autowired
    RefCategoryMapper refCategoryMapper = new RefCategoryMapperImpl();

    @Autowired
    RefSubstanceMapper refSubstanceMapper = new RefSubstanceMapperImpl();

    default MedicationCreateContract.MedicationCreateResponse toCreationResult(Medication entity) {
        return MedicationCreateContract.MedicationCreateResponse.newBuilder()
                .setId(entity.getId().toString())
                .build();
    }

    default MedicationRemoveContract.MedicationRemoveResponse toRemoveResult(Medication entity) {
        return MedicationRemoveContract.MedicationRemoveResponse.newBuilder()
                .setId(entity.getId().toString())
                .setName(entity.getName())
                .build();
    }

    default MedicationUpdateContract.MedicationUpdatePriceResponse toUpdatePriceResult(Medication entity) {
        return  MedicationUpdateContract.MedicationUpdatePriceResponse.newBuilder()
                .setId(entity.getId().toString())
                .setName(entity.getName())
                .setOldPrice(Optional.of(entity.getOldPrice()).orElse(null).doubleValue())
                .setNewPrice(entity.getPrice().doubleValue())
                .setUpdatedAt(TimeUtil.toTimestamp(entity.getUpdatedAt()))
                .build();
    }

    default MedicationFindAllContract.Medication toMedicationProtobuf(Medication entity) {
        var categories = entity.getMedicationCategories()
                .stream()
                .map(category -> refCategoryMapper.toProtobuf(category.getCategory()))
                .toList();

        var activeSubstances = entity.getActiveSubstances()
                .stream()
                .map(medicationSubstance -> MedicationFindAllContract.MedicationActiveSubstance
                        .newBuilder()
                        .setSubstance(refSubstanceMapper.toProtobuf(medicationSubstance.getRefSubstance()))
                        .setConcentration(medicationSubstance.getConcentration()
                                .doubleValue())
                        .build())
                .toList();

        return MedicationFindAllContract.Medication.newBuilder()
                .setId(entity.getId().toString())
                .setName(entity.getName())
                .setOldPrice(Optional.of(entity.getOldPrice().doubleValue()).orElse(null))
                .setPrice(entity.getPrice().doubleValue())
                .setIsPrescriptionOnly(entity.isPrescriptionOnly())
                .addAllMedicationCategories(categories)
                .addAllActiveSubstances(activeSubstances)
                .build();

    }

    default MedicationFindAllContract.MedicationFindAllResponse toProtobufPage(Page<Medication> page) {
        var content = page.getContent()
                .stream()
                .map(medication -> toMedicationProtobuf(medication))
                .toList();

        var pageResponse = PageUtil.PageResponse.newBuilder()
                .setPage(page.getNumber())
                .setSize(page.getSize())
                .setTotalElements(page.getTotalElements())
                .setTotalPages(page.getTotalPages())
                .setLast(page.isLast())
                .build();

        return MedicationFindAllContract.MedicationFindAllResponse.newBuilder()
                .addAllContent(content)
                .setPage(pageResponse)
                .build();
    }
}
