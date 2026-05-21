package com.app.quantitymeasurement.enums;

import com.app.quantitymeasurement.dto.QuantityDTO;
import com.app.quantitymeasurement.enumsimplm.LengthUnit;
import com.app.quantitymeasurement.enumsimplm.TemperatureUnit;
import com.app.quantitymeasurement.enumsimplm.VolumeUnit;
import com.app.quantitymeasurement.enumsimplm.WeightUnit;

public interface IMeasurable {
    double convertToBaseUnit(double value);
    double convertFromBaseUnit(double baseValue);

    QuantityDTO.MeasurementType getMeasurementType();

    static IMeasurable getUnitInstance(String name) {
        if (name == null) return null;
        String n = name.trim().toUpperCase();
        try {
            for (LengthUnit u : LengthUnit.values()) if (u.name().equals(n)) return u;
        } catch (Throwable ignored) {}
        try {
            for (WeightUnit u : WeightUnit.values()) if (u.name().equals(n)) return u;
        } catch (Throwable ignored) {}
        try {
            for (VolumeUnit u : VolumeUnit.values()) if (u.name().equals(n)) return u;
        } catch (Throwable ignored) {}
        try {
            for (TemperatureUnit u : TemperatureUnit.values()) if (u.name().equals(n)) return u;
        } catch (Throwable ignored) {}
        return null;
    }

    @FunctionalInterface
    interface SupportsArithmetic {
        boolean isSupported();
    }

    default String getUnitName() {
        return this.getClass().getSimpleName();
    }

    default SupportsArithmetic supportsArithmetic() {
        return () -> true;
    }

    default void validateOperationSupport(String operation) {
        // no-op by default
    }
}
