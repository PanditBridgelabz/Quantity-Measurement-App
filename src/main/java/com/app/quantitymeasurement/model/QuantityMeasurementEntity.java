package com.app.quantitymeasurement.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "quantity_measurements", indexes = {
        @Index(name = "idx_measurement_type", columnList = "measurementType"),
        @Index(name = "idx_operation", columnList = "operation"),
        @Index(name = "idx_owner_email", columnList = "ownerEmail")
})
public class QuantityMeasurementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String measurementType;
    private String operation;

    @Column(length = 1024)
    private String input;

    @Column(length = 1024)
    private String result;

    private String ownerEmail;

    private LocalDateTime timestamp;

    public QuantityMeasurementEntity() {}

    public QuantityMeasurementEntity(String measurementType, String operation, String input, String result, String ownerEmail) {
        this.measurementType = measurementType;
        this.operation = operation;
        this.input = input;
        this.result = result;
        this.ownerEmail = ownerEmail;
        this.timestamp = LocalDateTime.now();
    }

    // getters and setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMeasurementType() { return measurementType; }
    public void setMeasurementType(String measurementType) { this.measurementType = measurementType; }

    public String getOperation() { return operation; }
    public void setOperation(String operation) { this.operation = operation; }

    public String getInput() { return input; }
    public void setInput(String input) { this.input = input; }

    public String getResult() { return result; }
    public void setResult(String result) { this.result = result; }

    public String getOwnerEmail() { return ownerEmail; }
    public void setOwnerEmail(String ownerEmail) { this.ownerEmail = ownerEmail; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
