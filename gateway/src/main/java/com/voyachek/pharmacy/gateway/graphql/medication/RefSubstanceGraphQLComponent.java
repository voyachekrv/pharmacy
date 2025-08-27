package com.voyachek.pharmacy.gateway.graphql.medication;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.voyachek.pharmacy.gateway.data.utils.Reference;
import com.voyachek.pharmacy.gateway.data.utils.ReferenceQuery;
import com.voyachek.pharmacy.gateway.mapper.reference.DefaultReferenceMapper;
import com.voyachek.pharmacy.grpclib.medication.RefSubstanceEndpointGrpc;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.util.Collection;

/**
 * GraphQL-компонент для работы со справочником "Действующее вещество"
 */
@DgsComponent
@ConditionalOnProperty(name = "app.services.medication.enabled", havingValue = "true", matchIfMissing = true)
@RequiredArgsConstructor
@Slf4j
public class RefSubstanceGraphQLComponent {

    private final RefSubstanceEndpointGrpc.RefSubstanceEndpointBlockingStub refSubstanceEndpointBlockingStub;
    private final DefaultReferenceMapper referenceMapper;

    /**
     * Получение объекта справочника "Действующее вещество" по его ключу
     * @param key Ключ справочника
     * @return {@link Reference}
     */
    @DgsQuery
    public Reference getRefSubstanceByKey(String key) {
        log.info("Принят запрос на поиск справочника 'Действующее вещество' по ключу key = [{}]", key);
        var result = refSubstanceEndpointBlockingStub.findByKey(referenceMapper.toKeyRequest(key));
        log.info("Справочник 'Действующее вещество' найден, response = [{}]", result);
        return referenceMapper.toReference(result);
    }

    /**
     * Получение отфильтрованного списка объектов справочника "Действующее вещество"
     * @param query Фильтр справочника
     * @return {@link Collection<Reference>}
     */
    @DgsQuery
    public Collection<Reference> getAllRefSubstance(ReferenceQuery query) {
        log.info("Принят запрос на поиск справочника 'Действующее вещество' по фильтру query = [{}]", query);
        var result = refSubstanceEndpointBlockingStub.findAll(referenceMapper.toQuery(query));
        log.info("Список значений справочника 'Действующее вещество' получен, response.size = [{}]", result.getContentCount());
        return result.getContentList()
                .stream().map(referenceMapper::toReference)
                .toList();
    }
}
