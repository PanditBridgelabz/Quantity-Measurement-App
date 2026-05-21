package com.app.quantitymeasurement.controller;

import com.app.quantitymeasurement.dto.QuantityDTO;
import com.app.quantitymeasurement.service.QuantityMeasurementService;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for quantity measurement endpoints.
 * This version validates incoming request payloads and maps JSON property names
 * used by clients (thisQuantityDTO / thatQuantityDTO) to the service layer.
 */
@RestController
@RequestMapping("/api/v1/quantities")
public class QuantityMeasurementController {

    private static final Logger log = LoggerFactory.getLogger(QuantityMeasurementController.class);

    private final QuantityMeasurementService service;

    public QuantityMeasurementController(QuantityMeasurementService service) {
        this.service = service;
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody AddRequest req) {
        if (req == null || req.thisQuantityDTO == null || req.thatQuantityDTO == null) {
            log.warn("Invalid add request: missing thisQuantityDTO or thatQuantityDTO");
            return ResponseEntity.badRequest().body(java.util.Map.of(
                    "status", 400,
                    "message", "thisQuantityDTO and thatQuantityDTO are required"
            ));
        }

        try {
            QuantityDTO res = service.add(req.thisQuantityDTO, req.thatQuantityDTO, req.targetUnit);
            return ResponseEntity.ok(res);
        } catch (Exception ex) {
            log.error("Error in /add", ex);
            return ResponseEntity.status(500).body(java.util.Map.of(
                    "status", 500,
                    "message", "Internal server error"
            ));
        }
    }

    @PostMapping("/subtract")
    public ResponseEntity<?> subtract(@RequestBody AddRequest req) {
        if (req == null || req.thisQuantityDTO == null || req.thatQuantityDTO == null) {
            log.warn("Invalid subtract request: missing thisQuantityDTO or thatQuantityDTO");
            return ResponseEntity.badRequest().body(java.util.Map.of(
                    "status", 400,
                    "message", "thisQuantityDTO and thatQuantityDTO are required"
            ));
        }

        try {
            QuantityDTO res = service.subtract(req.thisQuantityDTO, req.thatQuantityDTO, req.targetUnit);
            return ResponseEntity.ok(res);
        } catch (Exception ex) {
            log.error("Error in /subtract", ex);
            return ResponseEntity.status(500).body(java.util.Map.of(
                    "status", 500,
                    "message", "Internal server error"
            ));
        }
    }

    @PostMapping("/divide")
    public ResponseEntity<?> divide(@RequestBody DivideRequest req) {
        if (req == null || req.thisQuantityDTO == null || req.thatQuantityDTO == null) {
            log.warn("Invalid divide request: missing a or b");
            return ResponseEntity.badRequest().body(java.util.Map.of(
                    "status", 400,
                    "message", "a and b are required"
            ));
        }

        try {
            QuantityDTO res = service.divide(req.thisQuantityDTO, req.thatQuantityDTO);
            return ResponseEntity.ok(res);
        } catch (Exception ex) {
            log.error("Error in /divide", ex);
            return ResponseEntity.status(500).body(java.util.Map.of(
                    "status", 500,
                    "message", "Internal server error"
            ));
        }
    }

    @PostMapping("/compare")
    public ResponseEntity<?> compare(@RequestBody CompareRequest req) {
        if (req == null || req.thisQuantityDTO == null || req.thatQuantityDTO == null) {
            log.warn("Invalid compare request: missing thisQuantityDTO or thatQuantityDTO");
            return ResponseEntity.badRequest().body(java.util.Map.of(
                    "status", 400,
                    "message", "thisQuantityDTO and thatQuantityDTO are required"
            ));
        }

        try {
            QuantityDTO res = service.compare(req.thisQuantityDTO, req.thatQuantityDTO);
            return ResponseEntity.ok(res);
        } catch (Exception ex) {
            log.error("Error in /compare", ex);
            return ResponseEntity.status(500).body(java.util.Map.of(
                    "status", 500,
                    "message", "Internal server error"
            ));
        }
    }

    @PostMapping("/convert")
    public ResponseEntity<?> convert(@RequestBody ConvertRequest req) {
        if (req == null || req.source == null || req.targetUnit == null) {
            log.warn("Invalid convert request: missing source or targetUnit");
            return ResponseEntity.badRequest().body(java.util.Map.of(
                    "status", 400,
                    "message", "source and targetUnit are required"
            ));
        }

        try {
            QuantityDTO res = service.convert(req.source, req.targetUnit);
            return ResponseEntity.ok(res);
        } catch (Exception ex) {
            log.error("Error in /convert", ex);
            return ResponseEntity.status(500).body(java.util.Map.of(
                    "status", 500,
                    "message", "Internal server error"
            ));
        }
    }

    // Request DTOs mapped to the JSON property names clients send.
    public static class AddRequest {
        @JsonProperty("thisQuantityDTO")
        public QuantityDTO thisQuantityDTO;

        @JsonProperty("thatQuantityDTO")
        public QuantityDTO thatQuantityDTO;

        @JsonProperty("targetUnit")
        public QuantityDTO.Unit targetUnit;
    }

    public static class DivideRequest {
        // keep names a and b but map JSON to the same fields used elsewhere
        @JsonProperty("a")
        public QuantityDTO thisQuantityDTO;

        @JsonProperty("b")
        public QuantityDTO thatQuantityDTO;
    }

    public static class CompareRequest {
        @JsonProperty("thisQuantityDTO")
        public QuantityDTO thisQuantityDTO;

        @JsonProperty("thatQuantityDTO")
        public QuantityDTO thatQuantityDTO;
    }

    public static class ConvertRequest {
        @JsonProperty("source")
        public QuantityDTO source;

        @JsonProperty("targetUnit")
        public QuantityDTO.Unit targetUnit;
    }
}
