package com.voyachek.pharmacy.medication.grpc;

import com.voyachek.pharmacy.grpclib.medication.*;
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

    /**
     * Создание нового препарата
     * @param request Запрос на создание препарата {@link MedicationCreateContract.MedicationCreateRequest}
     * @param responseObserver {@link StreamObserver<MedicationCreateContract.MedicationCreateResponse>}
     */
    @Override
    public void create(MedicationCreateContract.MedicationCreateRequest request,
                       StreamObserver<MedicationCreateContract.MedicationCreateResponse> responseObserver) {
        log.info("Принят запрос на создание препарата, request = [{}]", request);
        var result = medicationService.create(request);
        log.info("Препарат создан, response = [{}]", result);

        responseObserver.onNext(result);
        responseObserver.onCompleted();
    }

    /**
     * Удаление препарата
     * @param request Запрос на удаление препарата {@link MedicationRemoveContract.MedicationRemoveRequest}
     * @param responseObserver {@link StreamObserver<MedicationRemoveContract.MedicationRemoveResponse>}
     */
    @Override
    public void remove(MedicationRemoveContract.MedicationRemoveRequest request,
                       StreamObserver<MedicationRemoveContract.MedicationRemoveResponse> responseObserver) {
        log.info("Принят запрос на удаление препарата, request = [{}]", request);
        var result = medicationService.remove(request);
        log.info("Препарат удален, response = [{}]", result);

        responseObserver.onNext(result);
        responseObserver.onCompleted();
    }

    /**
     * Изменение цены препарата
     * @param request Запрос на обновление цены препарата {@link MedicationUpdateContract.MedicationUpdatePriceRequest}
     * @param responseObserver {@link StreamObserver<MedicationUpdateContract.MedicationUpdatePriceResponse>}
     */
    @Override
    public void updatePrice(MedicationUpdateContract.MedicationUpdatePriceRequest request,
                            StreamObserver<MedicationUpdateContract.MedicationUpdatePriceResponse> responseObserver) {
        log.info("Принят запрос на изменение цены препарата, request = [{}]", request);
        var result = medicationService.updatePrice(request);
        log.info("Препарат изменен, response = [{}]", result);

        responseObserver.onNext(result);
        responseObserver.onCompleted();
    }

    /**
     * Поиск списка препаратов по фильтру
     * @param request ЗЗапрос на получение отфильтрованной страницы списка препаратов {@link MedicationFindAllContract.MedicationFindAllRequest}
     * @param responseObserver {@link StreamObserver<MedicationFindAllContract.MedicationFindAllResponse>}
     */
    @Override
    public void findAll(MedicationFindAllContract.MedicationFindAllRequest request,
                        StreamObserver<MedicationFindAllContract.MedicationFindAllResponse> responseObserver) {
        log.info("Принят запрос на поиск страницы списка препаратов, request = [{}]", request);
        var result = medicationService.findAll(request);
        log.info("Страница найдена, response.size = [{}]", result.getContentCount());

        responseObserver.onNext(result);
        responseObserver.onCompleted();
    }
}
