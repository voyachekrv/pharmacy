package com.voyachek.pharmacy.gateway.data.utils;

import lombok.Data;

/**
 * Запрос на поиск страницы
 */
@Data
public class PageRequest {

    /**
     * Номер страницы
     */
    private int page;
    /**
     * Размер страницы
     */
    private int size;
}
