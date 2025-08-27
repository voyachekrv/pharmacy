package com.voyachek.pharmacy.gateway.data.medication;

import com.voyachek.pharmacy.gateway.data.utils.NumericSearchConditions;
import lombok.Data;

/**
 * Фильтр для поиска действующего вещества в препарате
 */
@Data
public class MedicationFindAllActiveSubstanceInput {

    /**
     * Название записи в справочнике
     */
    private String name;
    /**
     * Фильтр поиска концентрации
     */
    private NumericSearchConditions concentration;
}
