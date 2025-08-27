package com.voyachek.pharmacy.gateway.data.medication;

import com.voyachek.pharmacy.gateway.data.utils.Reference;
import lombok.Builder;
import lombok.Data;

/**
 * Концентрация действующего вещества в препарате
 */
@Data
@Builder
public class MedicationActiveSubstance {

    /**
     * Действующее вещество
     */
    private Reference substance;
    /**
     * Количество вещества
     */
    private double concentration;
}
