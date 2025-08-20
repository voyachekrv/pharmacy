package com.voyachek.pharmacy.medication.grpc.reference;

import com.voyachek.pharmacy.grpclib.medication.RefSubstanceEndpointGrpc;
import com.voyachek.pharmacy.grpclib.utils.reference.DefaultReferenceUtil;
import com.voyachek.pharmacy.medication.service.reference.RefSubstanceService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.grpc.server.service.GrpcService;

/**
 * GRPC-обработчик для работы со справочником "Действующее вещество" ({@link com.voyachek.pharmacy.medication.entity.reference.RefSubstance})
 */
@GrpcService
@RequiredArgsConstructor
@Slf4j
public class RefSubstanceGrpcHandler extends RefSubstanceEndpointGrpc.RefSubstanceEndpointImplBase {

    private final RefSubstanceService refSubstanceService;

    @Override
    public void findByKey(DefaultReferenceUtil.DefaultReferenceKey request,
                          StreamObserver<DefaultReferenceUtil.DefaultReference> responseObserver) {

        log.info("Принят запрос на поиск справочника 'Действующее вещество' по ключу, request = [{}]", request);
        var result = refSubstanceService.findByKey(request.getKey());
        log.info("Справочник 'Действующее вещество' найден, response = [{}]", result);

        responseObserver.onNext(result);
        responseObserver.onCompleted();
    }

    @Override
    public void findAll(DefaultReferenceUtil.DefaultReferenceQuery request,
                        StreamObserver<DefaultReferenceUtil.DefaultReferenceCollection>  responseObserver) {
        log.info("Принят запрос на поиск справочника 'Действующее вещество' по фильтру, request = [{}]", request);
        var result = refSubstanceService.findAll(request);
        log.info("Значения справочника 'Действующее вещество' найдены, length = [{}]", result.getContentCount());

        responseObserver.onNext(result);
        responseObserver.onCompleted();
    }
}
