package com.app.quantitymeasurement.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Objects;

/**
 * Data transfer object representing a quantity with a numeric value, unit and measurement type.
 * Designed to be safely deserializable by Jackson from JSON like:
 * {"value":1.0,"unit":"FEET","type":"LENGTH"}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuantityDTO {

    public enum MeasurementType {
        LENGTH, WEIGHT, VOLUME, TEMPERATURE;

        @JsonCreator
        public static MeasurementType fromString(String key) {
            if (key == null) return null;
            return MeasurementType.valueOf(key.toUpperCase());
        }

        @JsonValue
        public String toValue() {
            return this.name();
        }
    }

    public enum Unit {
        FEET, INCH, YARD, CENTIMETER, METER,
        KILOGRAM, GRAM, POUND,
        CELSIUS, FAHRENHEIT, KELVIN,
        LITRE, MILLILITRE, GALLON;

        @JsonCreator
        public static Unit fromString(String key) {
            if (key == null) return null;
            return Unit.valueOf(key.toUpperCase());
        }

        @JsonValue
        public String toValue() {
            return this.name();
        }
    }

    @JsonProperty("value")
    private Double value;

    @JsonProperty("unit")
    private Unit unit;

    @JsonProperty("type")
    private MeasurementType type;

    public QuantityDTO() {
        // no-arg constructor for Jackson
    }

    public QuantityDTO(Double value, Unit unit, MeasurementType type) {
        this.value = value;
        this.unit = unit;
        this.type = type;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public MeasurementType getType() {
        return type;
    }

    public void setType(MeasurementType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        String unitName = unit == null ? "" : unit.name();
        return (value == null ? "null" : value.toString()) + " " + unitName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QuantityDTO)) return false;
        QuantityDTO that = (QuantityDTO) o;
        return Objects.equals(value, that.value) &&
                unit == that.unit &&
                type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, unit, type);
    }
}
