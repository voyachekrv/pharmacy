package com.voyachek.pharmacy.gateway.data.medication;

import lombok.Builder;
import lombok.Data;

/**
 * Результат создания препарата
 */
@Data
@Builder
public class MedicationCreateOutput {

    /**
     * Идентификатор записи
     */
    private String id;
}
