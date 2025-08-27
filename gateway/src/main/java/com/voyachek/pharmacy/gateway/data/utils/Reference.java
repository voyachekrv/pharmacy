package com.voyachek.pharmacy.gateway.data.utils;

import lombok.Builder;
import lombok.Data;

/**
 * Справочник
 */
@Data
@Builder
public class Reference {

    /**
     * Идентификатор записи
     */
    private String id;
    /**
     * Ключ справочного значения
     */
    private String key;
    /**
     * Имя значения
     */
    private String name;
    /**
     * Порядок сортировки значения для вывода
     */
    private int ordering;
}
