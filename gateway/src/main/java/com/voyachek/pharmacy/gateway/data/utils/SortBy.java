package com.voyachek.pharmacy.gateway.data.utils;

import lombok.Data;

/**
 * Сортировка по полю
 */
@Data
public class SortBy {

    /**
     * Название поля
     */
    private String field;
    /**
     * Направление
     */
    private SortDirection direction;
}
