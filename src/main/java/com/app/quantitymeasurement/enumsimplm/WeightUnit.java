package com.app.quantitymeasurement.enumsimplm;

import com.app.quantitymeasurement.enums.IMeasurable;
import com.app.quantitymeasurement.dto.QuantityDTO;

public enum WeightUnit implements IMeasurable {

    KILOGRAM(1.0),             // base unit
    GRAM(0.001),               // 1 gram = 0.001 kg
    TONNE(1000.0),             // 1 tonne = 1000 kg
    POUND(0.453592);           // 1 pound = 0.453592 kg

    private final double toKgFactor;

    WeightUnit(double toKgFactor) {
        this.toKgFactor = toKgFactor;
    }

    @Override
    public double convertToBaseUnit(double value) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Invalid value");
        }
        return value * toKgFactor; // convert to kilograms
    }

    @Override
    public double convertFromBaseUnit(double baseValue) {
        if (!Double.isFinite(baseValue)) {
            throw new IllegalArgumentException("Invalid value");
        }
        return baseValue / toKgFactor; // convert from kilograms
    }

    @Override
    public String getUnitName() {
        return name();
    }

    @Override
    public QuantityDTO.MeasurementType getMeasurementType() {
        return QuantityDTO.MeasurementType.WEIGHT;
    }

    public double getConversionFactor() {
        return toKgFactor;
    }

    /**
     * Resolve a WeightUnit from string name.
     */
    public static WeightUnit fromString(String unitName) {
        if (unitName == null) throw new IllegalArgumentException("Unit name cannot be null");
        switch (unitName.trim().toUpperCase()) {
            case "KILOGRAM": return KILOGRAM;
            case "GRAM": return GRAM;
            case "TONNE": return TONNE;
            case "POUND": return POUND;
            default: throw new IllegalArgumentException("Unknown weight unit: " + unitName);
        }
    }
}
