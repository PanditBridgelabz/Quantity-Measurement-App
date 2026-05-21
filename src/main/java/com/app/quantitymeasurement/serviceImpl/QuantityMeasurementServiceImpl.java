package com.app.quantitymeasurement.serviceImpl;

import com.app.quantitymeasurement.dto.QuantityDTO;
import com.app.quantitymeasurement.model.QuantityMeasurementEntity;
import com.app.quantitymeasurement.repository.QuantityMeasurementRepository;
import com.app.quantitymeasurement.service.QuantityMeasurementService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Locale;

@Service
public class QuantityMeasurementServiceImpl implements QuantityMeasurementService {

    private final QuantityMeasurementRepository repo;

    public QuantityMeasurementServiceImpl(QuantityMeasurementRepository repo) {
        this.repo = repo;
    }

    @Override
    public QuantityDTO add(QuantityDTO a, QuantityDTO b, QuantityDTO.Unit targetUnit) {
        if (a.getType() == QuantityDTO.MeasurementType.TEMPERATURE) {
            throw new UnsupportedOperationException("Temperature arithmetic not supported");
        }
        double result = convertToTarget(a, targetUnit) + convertToTarget(b, targetUnit);
        QuantityDTO dto = new QuantityDTO(result, targetUnit, a.getType());
        persist("ADD", a, b, dto);
        return dto;
    }

    @Override
    public QuantityDTO subtract(QuantityDTO a, QuantityDTO b, QuantityDTO.Unit targetUnit) {
        if (a.getType() == QuantityDTO.MeasurementType.TEMPERATURE) {
            throw new UnsupportedOperationException("Temperature arithmetic not supported");
        }
        double result = convertToTarget(a, targetUnit) - convertToTarget(b, targetUnit);
        QuantityDTO dto = new QuantityDTO(result, targetUnit, a.getType());
        persist("SUBTRACT", a, b, dto);
        return dto;
    }

    @Override
    public QuantityDTO divide(QuantityDTO a, QuantityDTO b) {
        if (a.getType() == QuantityDTO.MeasurementType.TEMPERATURE) {
            throw new UnsupportedOperationException("Temperature division not supported");
        }
        if (b.getValue() == 0.0) {
            throw new ArithmeticException("Division by zero");
        }
        double valA = convertToTarget(a, a.getUnit());
        double valB = convertToTarget(b, a.getUnit());
        double result = valA / valB;
        QuantityDTO dto = new QuantityDTO(result, a.getUnit(), a.getType());
        persist("DIVIDE", a, b, dto);
        return dto;
    }

    @Override
    public QuantityDTO compare(QuantityDTO a, QuantityDTO b) {
        double valA = convertToTarget(a, b.getUnit());
        double valB = b.getValue();
        double result = (Math.abs(valA - valB) < 1e-6) ? 1.0 : 0.0;
        QuantityDTO dto = new QuantityDTO(result, b.getUnit(), a.getType());
        persist("COMPARE", a, b, dto);
        return dto;
    }

    @Override
    public QuantityDTO convert(QuantityDTO source, QuantityDTO.Unit targetUnit) {
        double result = convertToTarget(source, targetUnit);
        QuantityDTO dto = new QuantityDTO(result, targetUnit, source.getType());
        persist("CONVERT", source, null, dto);
        return dto;
    }

    private double convertToTarget(QuantityDTO dto, QuantityDTO.Unit targetUnit) {
        QuantityDTO.Unit src = dto.getUnit();
        double v = dto.getValue();

        // Length
        if (src == QuantityDTO.Unit.FEET) {
            if (targetUnit == QuantityDTO.Unit.INCH) return v * 12.0;
            if (targetUnit == QuantityDTO.Unit.YARD) return v / 3.0;
            if (targetUnit == QuantityDTO.Unit.CENTIMETER) return v * 30.48;
            if (targetUnit == QuantityDTO.Unit.METER) return v * 0.3048;
            if (targetUnit == QuantityDTO.Unit.FEET) return v;
        }
        if (src == QuantityDTO.Unit.INCH) {
            if (targetUnit == QuantityDTO.Unit.FEET) return v / 12.0;
            if (targetUnit == QuantityDTO.Unit.CENTIMETER) return v * 2.54;
            if (targetUnit == QuantityDTO.Unit.METER) return v * 0.0254;
            if (targetUnit == QuantityDTO.Unit.INCH) return v;
        }
        if (src == QuantityDTO.Unit.YARD) {
            if (targetUnit == QuantityDTO.Unit.FEET) return v * 3.0;
            if (targetUnit == QuantityDTO.Unit.YARD) return v;
            if (targetUnit == QuantityDTO.Unit.METER) return v * 0.9144;
        }
        if (src == QuantityDTO.Unit.CENTIMETER) {
            if (targetUnit == QuantityDTO.Unit.FEET) return v / 30.48;
            if (targetUnit == QuantityDTO.Unit.METER) return v / 100.0;
            if (targetUnit == QuantityDTO.Unit.CENTIMETER) return v;
        }
        if (src == QuantityDTO.Unit.METER) {
            if (targetUnit == QuantityDTO.Unit.CENTIMETER) return v * 100.0;
            if (targetUnit == QuantityDTO.Unit.FEET) return v * 3.28084;
            if (targetUnit == QuantityDTO.Unit.METER) return v;
        }

        // Weight
        if (src == QuantityDTO.Unit.KILOGRAM) {
            if (targetUnit == QuantityDTO.Unit.GRAM) return v * 1000.0;
            if (targetUnit == QuantityDTO.Unit.POUND) return v * 2.20462;
            if (targetUnit == QuantityDTO.Unit.KILOGRAM) return v;
        }
        if (src == QuantityDTO.Unit.GRAM) {
            if (targetUnit == QuantityDTO.Unit.KILOGRAM) return v / 1000.0;
            if (targetUnit == QuantityDTO.Unit.GRAM) return v;
        }
        if (src == QuantityDTO.Unit.POUND) {
            if (targetUnit == QuantityDTO.Unit.KILOGRAM) return v * 0.453592;
            if (targetUnit == QuantityDTO.Unit.POUND) return v;
        }

        // Temperature
        if (src == QuantityDTO.Unit.CELSIUS) {
            if (targetUnit == QuantityDTO.Unit.FAHRENHEIT) return (v * 9.0/5.0) + 32.0;
            if (targetUnit == QuantityDTO.Unit.KELVIN) return v + 273.15;
            if (targetUnit == QuantityDTO.Unit.CELSIUS) return v;
        }
        if (src == QuantityDTO.Unit.FAHRENHEIT) {
            if (targetUnit == QuantityDTO.Unit.CELSIUS) return (v - 32.0) * 5.0/9.0;
            if (targetUnit == QuantityDTO.Unit.FAHRENHEIT) return v;
        }
        if (src == QuantityDTO.Unit.KELVIN) {
            if (targetUnit == QuantityDTO.Unit.CELSIUS) return v - 273.15;
            if (targetUnit == QuantityDTO.Unit.KELVIN) return v;
        }

        // Volume
        if (src == QuantityDTO.Unit.LITRE) {
            if (targetUnit == QuantityDTO.Unit.MILLILITRE) return v * 1000.0;
            if (targetUnit == QuantityDTO.Unit.LITRE) return v;
        }
        if (src == QuantityDTO.Unit.MILLILITRE) {
            if (targetUnit == QuantityDTO.Unit.LITRE) return v / 1000.0;
            if (targetUnit == QuantityDTO.Unit.MILLILITRE) return v;
        }
        if (src == QuantityDTO.Unit.GALLON) {
            if (targetUnit == QuantityDTO.Unit.LITRE) return v * 3.78541;
            if (targetUnit == QuantityDTO.Unit.GALLON) return v;
        }

        return v;
    }

    private void persist(String operation, QuantityDTO a, QuantityDTO b, QuantityDTO result) {
        try {
            String owner = "anonymous";
            var auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.isAuthenticated() && auth.getPrincipal() instanceof String) {
                owner = (String) auth.getPrincipal();
            } else if (auth != null && auth.getName() != null) {
                owner = auth.getName();
            }

            QuantityMeasurementEntity entity = new QuantityMeasurementEntity(
                    result.getType() != null ? result.getType().name() : null,
                    operation,
                    a != null ? a.toString() : "",
                    result != null ? result.toString() : "",
                    owner
            );
            entity.setTimestamp(LocalDateTime.now());
            repo.save(entity);
        } catch (Exception ex) {
            // log if you have logger; swallow to not break main flow
        }
    }
}
