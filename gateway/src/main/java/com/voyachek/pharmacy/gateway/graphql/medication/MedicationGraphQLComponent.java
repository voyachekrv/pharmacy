package com.voyachek.pharmacy.gateway.graphql.medication;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.voyachek.pharmacy.gateway.data.medication.*;
import com.voyachek.pharmacy.gateway.data.utils.Page;
import com.voyachek.pharmacy.gateway.data.utils.PageRequest;
import com.voyachek.pharmacy.gateway.data.utils.SortBy;
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

    /**
     * Создание сущности типа "Препарат"
     * @param input Входные данные для создания препарата {@link MedicationCreateInput}
     * @return {@link MedicationCreateOutput}
     */
    @DgsMutation
    public MedicationCreateOutput createMedication(MedicationCreateInput input) {
        log.info("Принят запрос на создание препарата, request = [{}]", input);
        var result = medicationEndpointBlockingStub.create(this.medicationMapper.toCreateRequest(input));
        log.info("Пользователь создан, response = [{}]", result);

        return this.medicationMapper.toCreateOutput(result);
    }

    /**
     * Удаление сущности типа "Препарат"
     * @param id Идентификатор записи
     * @return {@link MedicationDeleteOutput}
     */
    @DgsMutation
    public MedicationDeleteOutput deleteMedication(String id) {
        log.info("Принят запрос на удаление препарата, id = [{}]", id);
        var result = medicationEndpointBlockingStub.remove(this.medicationMapper.toRemoveRequest(id));
        log.info("Препарат удален, response = [{}]", result);

        return this.medicationMapper.toDeleteOutput(result);
    }

    /**
     * Обновление цены сущности типа "Препарат"
     * @param id Идентификатор записи
     * @param newPrice Новая цена препарата
     * @return {@link MedicationUpdatePriceOutput}
     */
    @DgsMutation
    public MedicationUpdatePriceOutput updateMedicationPrice(String id, double newPrice) {
        log.info("Принят запрос на изменение цены препарата, id = {}, newPrice = {}", id, newPrice);
        var result = medicationEndpointBlockingStub.updatePrice(medicationMapper.toUpdatePriceRequest(id, newPrice));
        log.info("Препарат изменен, response = [{}]", result);

        return this.medicationMapper.toUpdatePriceOutput(result);
    }

    /**
     * Получение отфильтрованной страницы списка препаратов
     * @param filter Фильтр для поиска списка препаратов {@link MedicationFindAllFilter}
     * @param pagination Запрос на поиск страницы {@link PageRequest}
     * @param sort Сортировка по полю {@link SortBy}
     * @return {@link Page<Medication>}
     */
    @DgsQuery
    public Page<Medication> medications(MedicationFindAllFilter filter, PageRequest pagination, SortBy sort) {
        log.info("Принят запрос на поиск множества препаратов по фильтру, filter = {}, pagination = {}, sort= {}", filter, pagination, sort);
        var result = medicationEndpointBlockingStub.findAll(medicationMapper.toFindAllRequest(filter, pagination, sort));
        log.info("Список препаратов найден, response.size = [{}]", result.getContentCount());

        return medicationMapper.toMedicationPage(result);
    }
}
