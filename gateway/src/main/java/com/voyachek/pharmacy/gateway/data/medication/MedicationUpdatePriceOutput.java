package com.voyachek.pharmacy.gateway.data.medication;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MedicationUpdatePriceOutput {

    private String id;
    private String name;
    private double oldPrice;
    private double newPrice;
    private String updatedAt;
}
