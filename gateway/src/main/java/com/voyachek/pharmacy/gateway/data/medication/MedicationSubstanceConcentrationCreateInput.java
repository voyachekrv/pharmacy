package com.voyachek.pharmacy.gateway.data.medication;

import lombok.Data;

/**
 * Концентрация действующего вещества в препарате
 */
@Data
public class MedicationSubstanceConcentrationCreateInput {

    /**
     * Ключ справочника "Действующее вещество"
     */
    private String refSubstanceKey;

    /**
     * Количество вещества
     */
    private double concentration;
}
