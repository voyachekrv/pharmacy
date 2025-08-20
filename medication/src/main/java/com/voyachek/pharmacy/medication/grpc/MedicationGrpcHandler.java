package com.voyachek.pharmacy.medication.grpc;

import com.voyachek.pharmacy.grpclib.medication.MedicationCreateContract;
import com.voyachek.pharmacy.grpclib.medication.MedicationEndpointGrpc;
import com.voyachek.pharmacy.grpclib.medication.MedicationRemoveContract;
import com.voyachek.pharmacy.grpclib.medication.MedicationUpdateContract;
import com.voyachek.pharmacy.medication.service.MedicationService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.grpc.server.service.GrpcService;

/**
 * GRPC-обработчик для работы с препаратами ({@link com.voyachek.pharmacy.medication.entity.Medication})
 */
@GrpcService
@RequiredArgsConstructor
@Slf4j
public class MedicationGrpcHandler extends MedicationEndpointGrpc.MedicationEndpointImplBase {

    private final MedicationService medicationService;

    @Override
    public void create(MedicationCreateContract.MedicationCreateRequest request,
                       StreamObserver<MedicationCreateContract.MedicationCreateResponse> responseObserver) {
        log.info("Принят запрос на создание препарата, request = [{}]", request);
        var result = medicationService.create(request);
        log.info("Препарат создан, response = [{}]", result);

        responseObserver.onNext(result);
        responseObserver.onCompleted();
    }

    @Override
    public void remove(MedicationRemoveContract.MedicationRemoveRequest request,
                       StreamObserver<MedicationRemoveContract.MedicationRemoveResponse> responseObserver) {
        log.info("Принят запрос на удаление препарата, request = [{}]", request);
        var result = medicationService.remove(request);
        log.info("Препарат удален, response = [{}]", result);

        responseObserver.onNext(result);
        responseObserver.onCompleted();
    }

    @Override
    public void updatePrice(MedicationUpdateContract.MedicationUpdatePriceRequest request,
                            StreamObserver<MedicationUpdateContract.MedicationUpdatePriceResponse> responseObserver) {
        log.info("Принят запрос на изменение цены препарата, request = [{}]", request);
        var result = medicationService.updatePrice(request);
        log.info("Препарат изменен, response = [{}]", result);

        responseObserver.onNext(result);
        responseObserver.onCompleted();
    }
}
