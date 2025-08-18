package com.voyachek.pharmacy.gateway.data.utils;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Страница типа `T`
 * @param <T> Тип объектов на странице
 */
@Data
@Builder
public class Page<T> {

    /**
     * Записи на странице
     */
    private List<T> content;
    /**
     * Информация о странице
     */
    private PageInfo pageInfo;
}
