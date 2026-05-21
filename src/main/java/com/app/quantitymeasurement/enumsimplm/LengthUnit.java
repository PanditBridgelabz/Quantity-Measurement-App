package com.app.quantitymeasurement.enumsimplm;

import com.app.quantitymeasurement.enums.IMeasurable;
import com.app.quantitymeasurement.dto.QuantityDTO;

public enum LengthUnit implements IMeasurable {

    FEET(1.0),                  // base unit (feet)
    INCH(1.0 / 12.0),           // 1 inch = 1/12 feet
    YARD(3.0),                  // 1 yard = 3 feet
    CENTIMETER(1.0 / 30.48),    // 1 cm = 1/30.48 feet
    METER(3.28084);             // 1 meter = 3.28084 feet

    private final double toFeetFactor;

    LengthUnit(double toFeetFactor) {
        this.toFeetFactor = toFeetFactor;
    }

    @Override
    public double convertToBaseUnit(double value) {
        if (!Double.isFinite(value)) throw new IllegalArgumentException("Invalid value");
        return value * toFeetFactor;
    }

    @Override
    public double convertFromBaseUnit(double baseValue) {
        if (!Double.isFinite(baseValue)) throw new IllegalArgumentException("Invalid value");
        return baseValue / toFeetFactor;
    }

    @Override
    public String getUnitName() { return name(); }

    @Override
    public QuantityDTO.MeasurementType getMeasurementType() {
        return QuantityDTO.MeasurementType.LENGTH;
    }

    public double getConversionFactor() { return toFeetFactor; }

    public static LengthUnit fromString(String unitName) {
        if (unitName == null) throw new IllegalArgumentException("Unit name cannot be null");
        switch (unitName.trim().toUpperCase()) {
            case "FEET": return FEET;
            case "INCH": return INCH;
            case "YARD": return YARD;
            case "CENTIMETER":
            case "CENTIMETERS": return CENTIMETER;
            case "METER":
            case "METERS":
            case "METRE":
            case "METRES": return METER;
            default: throw new IllegalArgumentException("Unknown length unit: " + unitName);
        }
    }

    @Override
    public void validateOperationSupport(String operation) {
        // Length supports arithmetic operations
    }
}
