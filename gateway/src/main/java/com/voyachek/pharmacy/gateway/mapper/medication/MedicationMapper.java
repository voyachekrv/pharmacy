package com.voyachek.pharmacy.gateway.mapper.medication;

import com.google.protobuf.Timestamp;
import com.voyachek.pharmacy.gateway.data.medication.MedicationCreateInput;
import com.voyachek.pharmacy.gateway.data.medication.MedicationCreateOutput;
import com.voyachek.pharmacy.gateway.data.medication.MedicationDeleteOutput;
import com.voyachek.pharmacy.gateway.data.medication.MedicationUpdatePriceOutput;
import com.voyachek.pharmacy.grpclib.medication.MedicationCreateContract;
import com.voyachek.pharmacy.grpclib.medication.MedicationRemoveContract;
import com.voyachek.pharmacy.grpclib.medication.MedicationSubstanceConcentrationContract;
import com.voyachek.pharmacy.grpclib.medication.MedicationUpdateContract;
import com.voyachek.pharmacy.grpclib.utils.TimeUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MedicationMapper {

    default MedicationCreateContract.MedicationCreateRequest toCreateRequest(MedicationCreateInput input) {
        var activeSubstances = input.getActiveSubstances()
                .stream()
                .map(substance -> MedicationSubstanceConcentrationContract.MedicationSubstanceConcentration
                        .newBuilder()
                        .setRefSubstanceKey(substance.getRefSubstanceKey())
                        .setConcentration(substance.getConcentration())
                        .build())
                .toList();

        return MedicationCreateContract.MedicationCreateRequest.newBuilder()
                .setName(input.getName())
                .setPrice(input.getPrice())
                .setIsPrescriptionOnly(input.isPrescriptionOnly())
                .addAllMedicationCategories(input.getMedicationCategories())
                .addAllActiveSubstances(activeSubstances)
                .build();
    }

    @Mapping(target = "id", source = "id")
    MedicationCreateOutput toCreateOutput(MedicationCreateContract.MedicationCreateResponse response);

    default MedicationRemoveContract.MedicationRemoveRequest toRemoveRequest(String id) {
        return MedicationRemoveContract.MedicationRemoveRequest.newBuilder()
                .setId(id)
                .build();
    }

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    MedicationDeleteOutput toDeleteOutput(MedicationRemoveContract.MedicationRemoveResponse response);

   default MedicationUpdateContract.MedicationUpdatePriceRequest toUpdatePriceRequest(String id, double newPrice) {
       return MedicationUpdateContract.MedicationUpdatePriceRequest.newBuilder()
               .setId(id)
               .setNewPrice(newPrice)
               .build();
   }

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "oldPrice", source = "oldPrice")
    @Mapping(target = "newPrice", source = "newPrice")
    @Mapping(target = "updatedAt", source = "updatedAt", qualifiedByName = "stringifyTimeStamp")
    MedicationUpdatePriceOutput toUpdatePriceOutput(MedicationUpdateContract.MedicationUpdatePriceResponse response);

    @Named("stringifyTimeStamp")
    default String stringifyTimeStamp(Timestamp ts) {
        return TimeUtil.fromTimestamp(ts).toString();
    }
}
