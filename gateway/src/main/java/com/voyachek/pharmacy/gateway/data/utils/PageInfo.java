package com.voyachek.pharmacy.gateway.data.utils;

import lombok.Builder;
import lombok.Data;

/**
 * Данные о странице
 */
@Data
@Builder
public class PageInfo {

    /**
     * Номер страницы
     */
    private int page;
    /**
     * Является ли данная страница последней
     */
    private boolean last;
    /**
     * Размер страницы
     */
    private int size;
    /**
     * Общее количество записей
     */
    private long totalElements;
    /**
     * Общее количество страниц
     */
    private int totalPages;
}
