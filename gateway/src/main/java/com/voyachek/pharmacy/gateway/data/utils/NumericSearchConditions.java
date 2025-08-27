package com.voyachek.pharmacy.gateway.data.utils;

import lombok.Data;

/**
 * Фильтр для чисел
 */
@Data
public class NumericSearchConditions {

    /**
     * Фильтр "<"
     */
    private int lt;
    /**
     * Фильтр "<="
     */
    private int lte;
    /**
     * Фильтр ">"
     */
    private int gt;
    /**
     * Фильтр ">="
     */
    private int gte;
    /**
     * Фильтр "="
     */
    private int eq;
    /**
     * Фильтр "!="
     */
    private int neq;
}
