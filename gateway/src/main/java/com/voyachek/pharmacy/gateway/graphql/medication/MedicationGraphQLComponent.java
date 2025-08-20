package com.voyachek.pharmacy.gateway.graphql.medication;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.voyachek.pharmacy.gateway.data.medication.MedicationCreateInput;
import com.voyachek.pharmacy.gateway.data.medication.MedicationCreateOutput;
import com.voyachek.pharmacy.gateway.data.medication.MedicationDeleteOutput;
import com.voyachek.pharmacy.gateway.data.medication.MedicationUpdatePriceOutput;
import com.voyachek.pharmacy.gateway.mapper.medication.MedicationMapper;
import com.voyachek.pharmacy.grpclib.medication.MedicationEndpointGrpc;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

/**
 * GraphQL-компонент для работы с сущностью "Препарат"
 */
@DgsComponent
@ConditionalOnProperty(name = "app.services.medication.enabled", havingValue = "true", matchIfMissing = true)
@RequiredArgsConstructor
@Slf4j
public class MedicationGraphQLComponent {

    private final MedicationEndpointGrpc.MedicationEndpointBlockingStub medicationEndpointBlockingStub;
    private final MedicationMapper medicationMapper;

    @DgsMutation
    public MedicationCreateOutput createMedication(MedicationCreateInput input) {
        log.info("Принят запрос на создание препарата, request = [{}]", input);
        var result = medicationEndpointBlockingStub.create(this.medicationMapper.toCreateRequest(input));
        log.info("Пользователь создан, response = [{}]", result);

        return this.medicationMapper.toCreateOutput(result);
    }

    @DgsMutation
    public MedicationDeleteOutput deleteMedication(String id) {
        log.info("Принят запрос на удаление препарата, id = [{}]", id);
        var result = medicationEndpointBlockingStub.remove(this.medicationMapper.toRemoveRequest(id));
        log.info("Препарат удален, response = [{}]", result);

        return this.medicationMapper.toDeleteOutput(result);
    }

    @DgsMutation
    public MedicationUpdatePriceOutput updateMedicationPrice(String id, double newPrice) {
        log.info("Принят запрос на изменение цены препарата, id = {}, newPrice = {}", id, newPrice);
        var result = medicationEndpointBlockingStub.updatePrice(medicationMapper.toUpdatePriceRequest(id, newPrice));
        log.info("Препарат изменен, response = [{}]", result);

        return this.medicationMapper.toUpdatePriceOutput(result);
    }
}
