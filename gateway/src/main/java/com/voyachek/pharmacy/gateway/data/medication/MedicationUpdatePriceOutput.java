package com.voyachek.pharmacy.gateway.data.medication;

import lombok.Builder;
import lombok.Data;

/**
 * Результат обновления цены препарата
 */
@Data
@Builder
public class MedicationUpdatePriceOutput {

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
    private double newPrice;
    /**
     * Дата обновления
     */
    private String updatedAt;
}
