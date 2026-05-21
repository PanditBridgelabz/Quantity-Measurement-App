package com.app.quantitymeasurement.service;

import com.app.quantitymeasurement.dto.QuantityDTO;

public interface QuantityMeasurementService {
    QuantityDTO add(QuantityDTO a, QuantityDTO b, QuantityDTO.Unit targetUnit);
    QuantityDTO subtract(QuantityDTO a, QuantityDTO b, QuantityDTO.Unit targetUnit);
    QuantityDTO divide(QuantityDTO a, QuantityDTO b);
    QuantityDTO compare(QuantityDTO a, QuantityDTO b);
    QuantityDTO convert(QuantityDTO source, QuantityDTO.Unit targetUnit);
}
