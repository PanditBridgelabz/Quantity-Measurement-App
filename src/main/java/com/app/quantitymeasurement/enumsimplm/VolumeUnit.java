package com.app.quantitymeasurement.enumsimplm;

import com.app.quantitymeasurement.enums.IMeasurable;
import com.app.quantitymeasurement.dto.QuantityDTO;


public enum VolumeUnit implements IMeasurable {

    LITRE(1.0),                // base unit
    MILLILITRE(0.001),         // 1 mL = 0.001 L
    GALLON(3.78541);           // 1 US gallon = 3.78541 L

    private final double toLitreFactor;

    VolumeUnit(double toLitreFactor) {
        this.toLitreFactor = toLitreFactor;
    }

    @Override
    public double convertToBaseUnit(double value) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Invalid value");
        }
        return value * toLitreFactor; // convert to litres
    }

    @Override
    public double convertFromBaseUnit(double baseValue) {
        if (!Double.isFinite(baseValue)) {
            throw new IllegalArgumentException("Invalid value");
        }
        return baseValue / toLitreFactor; // convert from litres
    }

    @Override
    public String getUnitName() {
        return name();
    }

    @Override
    public QuantityDTO.MeasurementType getMeasurementType() {
        return QuantityDTO.MeasurementType.VOLUME;
    }

    public double getConversionFactor() {
        return toLitreFactor;
    }

    /**
     * Resolve a VolumeUnit from string name.
     */
    public static VolumeUnit fromString(String unitName) {
        if (unitName == null) throw new IllegalArgumentException("Unit name cannot be null");
        switch (unitName.trim().toUpperCase()) {
            case "LITRE": return LITRE;
            case "MILLILITRE":
            case "MILLILITER": return MILLILITRE;
            case "GALLON": return GALLON;
            default: throw new IllegalArgumentException("Unknown volume unit: " + unitName);
        }
    }

    @Override
    public void validateOperationSupport(String operation) {
        // Volume supports arithmetic operations
    }
}
