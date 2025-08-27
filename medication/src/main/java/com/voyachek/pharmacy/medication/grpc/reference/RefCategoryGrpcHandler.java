package com.voyachek.pharmacy.medication.grpc.reference;

import com.voyachek.pharmacy.grpclib.medication.RefCategoryEndpointGrpc;
import com.voyachek.pharmacy.grpclib.utils.reference.DefaultReferenceUtil;
import com.voyachek.pharmacy.medication.service.reference.RefCategoryService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.grpc.server.service.GrpcService;

/**
 * GRPC-обработчик для работы со справочником "Категория препарата" ({@link com.voyachek.pharmacy.medication.entity.reference.RefCategory})
 */
@GrpcService
@RequiredArgsConstructor
@Slf4j
public class RefCategoryGrpcHandler extends RefCategoryEndpointGrpc.RefCategoryEndpointImplBase {

    private final RefCategoryService refCategoryService;

    /**
     * Поиск значения справочника "Категория препарата" по его ключу
     * @param request Объект ключа справочного значения {@link DefaultReferenceUtil.DefaultReferenceKey}
     * @param responseObserver {@link DefaultReferenceUtil.DefaultReference}
     */
    @Override
    public void findByKey(DefaultReferenceUtil.DefaultReferenceKey request,
                          StreamObserver<DefaultReferenceUtil.DefaultReference>  responseObserver) {

        log.info("Принят запрос на поиск справочника 'Категория препарата' по ключу, request = [{}]", request);
        var result = refCategoryService.findByKey(request.getKey());
        log.info("Справочник 'Категория препарата' найден, response = [{}]", result);

        responseObserver.onNext(result);
        responseObserver.onCompleted();
    }

    /**
     * Поиск всех значений "Категория препарата"
     * @param request Объект поиска справочного значения {@link DefaultReferenceUtil.DefaultReferenceQuery}
     * @param responseObserver {@link DefaultReferenceUtil.DefaultReferenceCollection}
     */
    @Override
    public void findAll(DefaultReferenceUtil.DefaultReferenceQuery request,
                        StreamObserver<DefaultReferenceUtil.DefaultReferenceCollection>  responseObserver) {
        log.info("Принят запрос на поиск справочника 'Категория препарата' по фильтру, request = [{}]", request);
        var result = refCategoryService.findAll(request);
        log.info("Значения справочника 'Категория препарата' найдены, length = [{}]", result.getContentCount());

        responseObserver.onNext(result);
        responseObserver.onCompleted();
    }
}
