package com.app.quantitymeasurement.repository;

import com.app.quantitymeasurement.model.QuantityMeasurementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuantityMeasurementRepository extends JpaRepository<QuantityMeasurementEntity, Long> {
    List<QuantityMeasurementEntity> findByMeasurementTypeIgnoreCase(String measurementType);
    List<QuantityMeasurementEntity> findByOperationIgnoreCase(String operation);
    long countByOperationIgnoreCase(String operation);
    List<QuantityMeasurementEntity> findByOwnerEmail(String ownerEmail);
}
