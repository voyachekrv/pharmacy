package com.voyachek.pharmacy.gateway.data.medication;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MedicationCreateOutput {
    /**
     * Идентификатор записи
     */
    private String id;
}
