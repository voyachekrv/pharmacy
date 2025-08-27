package com.voyachek.pharmacy.gateway.data.utils;

import com.voyachek.pharmacy.grpclib.utils.search.SearchUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Направление сортировки (ASC / DESC)
 */
@Getter
@RequiredArgsConstructor
@ToString
public enum SortDirection {
    ASC("ASC"),
    DESC("DESC");
    private final String name;

    public SearchUtil.SortDirection toProtobuf() {
        return SearchUtil.SortDirection.valueOf(this.name);
    }
}
