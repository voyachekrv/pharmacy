package com.voyachek.pharmacy.medication.mapper;

import com.voyachek.pharmacy.grpclib.medication.MedicationCreateContract;
import com.voyachek.pharmacy.grpclib.medication.MedicationRemoveContract;
import com.voyachek.pharmacy.grpclib.medication.MedicationUpdateContract;
import com.voyachek.pharmacy.grpclib.utils.TimeUtil;
import com.voyachek.pharmacy.medication.entity.Medication;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.Optional;

/**
 * Маппер для сущности "Препарат"
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MedicationMapper {

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
}
