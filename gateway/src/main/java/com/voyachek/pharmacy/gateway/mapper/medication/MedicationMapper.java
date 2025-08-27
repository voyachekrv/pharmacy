package com.voyachek.pharmacy.gateway.mapper.medication;

import com.google.protobuf.Timestamp;
import com.voyachek.pharmacy.gateway.data.medication.*;
import com.voyachek.pharmacy.gateway.data.utils.*;
import com.voyachek.pharmacy.gateway.mapper.page.PaginationMapperImpl;
import com.voyachek.pharmacy.grpclib.medication.*;
import com.voyachek.pharmacy.grpclib.utils.TimeUtil;
import com.voyachek.pharmacy.grpclib.utils.search.SearchUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import com.voyachek.pharmacy.gateway.mapper.page.PaginationMapper;

import java.util.Objects;

/**
 * Маппер для сущности "Препарат"
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses={PaginationMapper.class})
public interface MedicationMapper {

    PaginationMapper paginationMapper = new PaginationMapperImpl();

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

    default MedicationFindAllContract.MedicationFindAllRequest toFindAllRequest(MedicationFindAllFilter filter,
                                                                                PageRequest pagination,
                                                                                SortBy sort) {
        var builder = MedicationFindAllContract.MedicationFindAllRequest.newBuilder();

        var protobufPagination = paginationMapper.toPageRequest(pagination);
        builder.setPagination(protobufPagination);

        if (Objects.nonNull(filter)) {
            var protobufFilter = MedicationFindAllContract.MedicationFindAllFilterRequest.newBuilder()
                    .setIsPrescriptionOnly(filter.isPrescriptionOnly());

            if (Objects.nonNull(filter.getCategories())) {
                protobufFilter.addAllCategories(filter.getCategories());
            }

            if (Objects.nonNull(filter.getActiveSubstance())) {
                var protobufActiveSubstance =  MedicationFindAllContract.MedicationFindAllActiveSubstanceRequest.newBuilder()
                        .setName(filter.getActiveSubstance().getName())
                        .setConcentration(
                                SearchUtil.NumericSearchConditions.newBuilder()
                                        .setLt(filter.getActiveSubstance().getConcentration().getLt())
                                        .setGt(filter.getActiveSubstance().getConcentration().getGt())
                                        .setLte(filter.getActiveSubstance().getConcentration().getLte())
                                        .setGte(filter.getActiveSubstance().getConcentration().getGte())
                                        .setEq(filter.getActiveSubstance().getConcentration().getEq())
                                        .setNeq(filter.getActiveSubstance().getConcentration().getNeq())
                                        .build()
                        )
                        .build();
                protobufFilter.setActiveSubstance(protobufActiveSubstance);
            }
            builder.setFilter(protobufFilter);
        }

        if (Objects.nonNull(sort)) {
            var protobufSort = SearchUtil.SortBy.newBuilder()
                    .setField(sort.getField())
                    .setDirection(sort.getDirection().toProtobuf())
                    .build();

            builder.setSort(protobufSort);
        }

        return builder.build();
    }

    default Medication toMedication(MedicationFindAllContract.Medication medication) {
        var medicationCategories = medication.getMedicationCategoriesList()
                .stream()
                .map(category -> Reference
                        .builder()
                        .id(category.getId())
                        .name(category.getName())
                        .key(category.getKey())
                        .ordering(category.getOrdering())
                        .build())
                .toList();

        var activeSubstances = medication.getActiveSubstancesList()
                .stream()
                .map(activeSubstance -> MedicationActiveSubstance
                        .builder()
                        .substance(Reference.builder()
                                        .id(activeSubstance.getSubstance().getId())
                                        .key(activeSubstance.getSubstance().getKey())
                                        .name(activeSubstance.getSubstance().getName())
                                        .ordering(activeSubstance.getSubstance().getOrdering())
                                        .build())
                        .concentration(activeSubstance.getConcentration())
                        .build())
                .toList();

        return Medication.builder()
                .id(medication.getId())
                .name(medication.getName())
                .oldPrice(medication.getOldPrice())
                .price(medication.getPrice())
                .isPrescriptionOnly(medication.getIsPrescriptionOnly())
                .medicationCategories(medicationCategories)
                .activeSubstances(activeSubstances)
                .createdAt(medication.getCreatedAt().toString())
                .updatedAt(medication.getUpdatedAt().toString())
                .build();
    }

    default Page<Medication> toMedicationPage(MedicationFindAllContract.MedicationFindAllResponse findAllResponse) {
        var pageInfo = PageInfo.builder()
                .page(findAllResponse.getPage().getPage())
                .last(findAllResponse.getPage().getLast())
                .size(findAllResponse.getPage().getSize())
                .totalElements(findAllResponse.getPage().getTotalElements())
                .totalPages(findAllResponse.getPage().getTotalPages())
                .build();

        return Page.<Medication>builder()
                .content(findAllResponse.getContentList()
                        .stream()
                        .map(this::toMedication)
                        .toList())
                .pageInfo(pageInfo)
                .build();
    }
}
