package com.app.quantitymeasurement.models;

import com.app.quantitymeasurement.enumsimplm.WeightUnit;

import java.util.Objects;

public class QuantityWeight {

    private final Quantity<WeightUnit> delegate;

    public QuantityWeight(double value, WeightUnit unit) {
        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }
        this.delegate = new Quantity<>(value, unit);
    }

    public double getValue() {
        return delegate.getValue();
    }

    public WeightUnit getUnit() {
        return delegate.getUnit();
    }

    public double toKilogram() {
        return delegate.convertTo(WeightUnit.KILOGRAM).getValue();
    }

    public double toGram() {
        return delegate.convertTo(WeightUnit.GRAM).getValue();
    }

    public double toPound() {
        return delegate.convertTo(WeightUnit.POUND).getValue();
    }

    // Conversion
    public QuantityWeight convert(WeightUnit targetUnit) {
        Quantity<WeightUnit> converted = delegate.convertTo(targetUnit);
        return new QuantityWeight(converted.getValue(), converted.getUnit());
    }

    // Addition
    public QuantityWeight add(QuantityWeight other) {
        if (other == null) {
            throw new IllegalArgumentException("Second operand cannot be null");
        }
        Quantity<WeightUnit> result = delegate.add(other.delegate, this.getUnit());
        return new QuantityWeight(result.getValue(), result.getUnit());
    }

    public QuantityWeight add(QuantityWeight other, WeightUnit targetUnit) {
        if (other == null || targetUnit == null) {
            throw new IllegalArgumentException("Operands and target unit cannot be null");
        }
        Quantity<WeightUnit> result = delegate.add(other.delegate, targetUnit);
        return new QuantityWeight(result.getValue(), result.getUnit());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof QuantityWeight other)) return false;

        // Normalize both to kilograms
        double thisKg = this.delegate.getUnit().convertToBaseUnit(this.delegate.getValue());
        double otherKg = other.delegate.getUnit().convertToBaseUnit(other.delegate.getValue());

        // Compare with tolerance
        return Math.abs(thisKg - otherKg) < 1e-6;
    }

    @Override
    public int hashCode() {
        // Normalize to kilograms for consistent hashing
        double baseKg = this.delegate.getUnit().convertToBaseUnit(this.delegate.getValue());
        return Objects.hash(Math.round(baseKg * 1e6));
    }

    @Override
    public String toString() {
        return delegate.toString();
    }
}
