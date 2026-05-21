package com.app.quantitymeasurement.enumsimplm;

import com.app.quantitymeasurement.enums.IMeasurable;
import com.app.quantitymeasurement.dto.QuantityDTO;


public enum TemperatureUnit implements IMeasurable {

    CELSIUS {
        @Override
        public double convertToBaseUnit(double value) {
            return value; // Celsius as base
        }
        @Override
        public double convertFromBaseUnit(double baseValue) {
            return baseValue;
        }
    },
    FAHRENHEIT {
        @Override
        public double convertToBaseUnit(double value) {
            return (value - 32) * 5.0 / 9.0; // to Celsius
        }
        @Override
        public double convertFromBaseUnit(double baseValue) {
            return (baseValue * 9.0 / 5.0) + 32; // from Celsius
        }
    },
    KELVIN {
        @Override
        public double convertToBaseUnit(double value) {
            return value - 273.15; // to Celsius
        }
        @Override
        public double convertFromBaseUnit(double baseValue) {
            return baseValue + 273.15; // from Celsius
        }
    };

    @Override
    public String getUnitName() {
        return name();
    }

    @Override
    public QuantityDTO.MeasurementType getMeasurementType() {
        return QuantityDTO.MeasurementType.TEMPERATURE;
    }

    /**
     * Temperature does not support arithmetic operations.
     */
    @Override
    public SupportsArithmetic supportsArithmetic() {
        return () -> false;
    }

    @Override
    public void validateOperationSupport(String operation) {
        // Any arithmetic operation is unsupported for temperature
        if ("ADD".equalsIgnoreCase(operation) ||
                "SUBTRACT".equalsIgnoreCase(operation) ||
                "DIVIDE".equalsIgnoreCase(operation)) {
            throw new UnsupportedOperationException(
                    "Operation '" + operation + "' is not supported for Temperature measurements."
            );
        }
    }

    /**
     * Resolve a TemperatureUnit from string name.
     */
    public static TemperatureUnit fromString(String unitName) {
        if (unitName == null) throw new IllegalArgumentException("Unit name cannot be null");
        switch (unitName.trim().toUpperCase()) {
            case "CELSIUS": return CELSIUS;
            case "FAHRENHEIT": return FAHRENHEIT;
            case "KELVIN": return KELVIN;
            default: throw new IllegalArgumentException("Unknown temperature unit: " + unitName);
        }
    }
}
