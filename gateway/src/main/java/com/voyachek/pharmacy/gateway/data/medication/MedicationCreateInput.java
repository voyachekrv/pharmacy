package com.voyachek.pharmacy.gateway.data.medication;

import lombok.Data;

import java.util.List;

/**
 * Контракт на создание нового препарата
 */
@Data
public class MedicationCreateInput {
    /**
     * Название
     */
    private String name;
    /**
     * Цена
     */
    private double price;
    /**
     * Отпускается ли препарат только по рецепту
     */
    private boolean isPrescriptionOnly;
    /**
     * Ключи справочника "Категория препарата"
     */
    private List<String> medicationCategories;
    /**
     * Концентрация действующего вещества в препарате
     */
    private List<MedicationSubstanceConcentrationCreateInput> activeSubstances;
}
