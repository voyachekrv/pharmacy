package com.voyachek.pharmacy.gateway.data.medication;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MedicationDeleteOutput {
    /**
     * Идентификатор записи
     */
    private String id;
    /**
     * Название
     */
    private String name;
}
