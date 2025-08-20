package com.voyachek.pharmacy.gateway.graphql.medication;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.voyachek.pharmacy.gateway.data.utils.Reference;
import com.voyachek.pharmacy.gateway.data.utils.ReferenceQuery;
import com.voyachek.pharmacy.gateway.mapper.reference.DefaultReferenceMapper;
import com.voyachek.pharmacy.grpclib.medication.RefCategoryEndpointGrpc;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.util.Collection;

/**
 * GraphQL-компонент для работы со справочником "Категория препарата"
 */
@DgsComponent
@ConditionalOnProperty(name = "app.services.medication.enabled", havingValue = "true", matchIfMissing = true)
@RequiredArgsConstructor
@Slf4j
public class RefCategoryGraphQLComponent {

    private final RefCategoryEndpointGrpc.RefCategoryEndpointBlockingStub refCategoryEndpointBlockingStub;
    private final DefaultReferenceMapper referenceMapper;

    /**
     * Получение объекта справочника "Категория препарата" по его ключу
     */
    @DgsQuery
    public Reference getRefCategoryByKey(String key) {
        log.info("Принят запрос на поиск справочника 'Категория препарата' по ключу key = [{}]", key);
        var result = refCategoryEndpointBlockingStub.findByKey(referenceMapper.toKeyRequest(key));
        log.info("Справочник 'Категория препарата' найден, response = [{}]", result);
        return referenceMapper.toReference(result);
    }

    /**
     * Получение отфильтрованного списка объектов справочника "Категория препарата"
     */
    @DgsQuery
    public Collection<Reference> getAllRefCategory(ReferenceQuery query) {
        log.info("Принят запрос на поиск справочника 'Категория препарата' по фильтру query = [{}]", query);
        var result = refCategoryEndpointBlockingStub.findAll(referenceMapper.toQuery(query));
        log.info("Список значений справочника 'Категория препарата' получен, response.size = [{}]", result.getContentCount());
        return result.getContentList()
                .stream().map(referenceMapper::toReference)
                .toList();
    }
}
