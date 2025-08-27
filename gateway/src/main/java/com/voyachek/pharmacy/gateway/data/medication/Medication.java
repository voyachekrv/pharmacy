package com.voyachek.pharmacy.gateway.data.medication;

import com.voyachek.pharmacy.gateway.data.utils.Reference;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Препарат
 */
@Data
@Builder
public class Medication {

    /**
     * ID записи
     */
    private String id;
    /**
     * Название
     */
    private String name;
    /**
     * Предыдущая цена
     */
    private double oldPrice;
    /**
     * Текущая цена
     */
    private double price;
    /**
     * Отпускается ли препарат только по рецепту
     */
    private boolean isPrescriptionOnly;
    /**
     * Категории препарата
     */
    private List<Reference> medicationCategories;
    /**
     * Действующие вещества
     */
    private List<MedicationActiveSubstance> activeSubstances;
    /**
     * Дата создания
     */
    private String createdAt;
    /**
     * Дата обновления
     */
    private String updatedAt;
}
