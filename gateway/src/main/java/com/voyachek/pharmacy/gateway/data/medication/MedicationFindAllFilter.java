package com.voyachek.pharmacy.gateway.data.medication;

import lombok.Data;

import java.util.List;

/**
 * Фильтр для поиска списка препаратов
 */
@Data
public class MedicationFindAllFilter {

    /**
     * Отпускается ли препарат только по рецепту
     */
    private boolean isPrescriptionOnly;
    /**
     * Наименования категорий
     */
    private List<String> categories;
    /**
     * Фильтр для поиска действующего вещества в препарате
     */
    private MedicationFindAllActiveSubstanceInput activeSubstance;
}
