package com.voyachek.pharmacy.medication.service;

import com.voyachek.pharmacy.grpclib.medication.MedicationCreateContract;
import com.voyachek.pharmacy.grpclib.medication.MedicationRemoveContract;
import com.voyachek.pharmacy.grpclib.medication.MedicationUpdateContract;

/**
 * Интерфейс сервиса для сущности типа "Препарат"
 */
public interface MedicationService {

    /**
     * Создание новой записи типа "Препарат"
     * @param request Запрос на создание {@link MedicationCreateContract.MedicationCreateRequest}
     * @return {@link MedicationCreateContract.MedicationCreateResponse}
     */
    MedicationCreateContract.MedicationCreateResponse create(MedicationCreateContract.MedicationCreateRequest request);

    /**
     * Удаление записи типа "Препарат"
     * @param request Запрос на удаление {@link MedicationRemoveContract.MedicationRemoveRequest}
     * @return {@link MedicationRemoveContract.MedicationRemoveResponse}
     */
    MedicationRemoveContract.MedicationRemoveResponse remove(MedicationRemoveContract.MedicationRemoveRequest request);

    /**
     * Обновление цены препарата
     * @param request Запрос на обновление {@link MedicationUpdateContract.MedicationUpdatePriceRequest}
     * @return {@link MedicationUpdateContract.MedicationUpdatePriceResponse}
     */
    MedicationUpdateContract.MedicationUpdatePriceResponse updatePrice(MedicationUpdateContract.MedicationUpdatePriceRequest request);
}
