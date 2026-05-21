package com.app.quantitymeasurement.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class QuantitiesWrapper {

    @JsonProperty("thisQuantityDTO")
    private QuantityDTO thisQuantityDTO;

    @JsonProperty("thatQuantityDTO")
    private QuantityDTO thatQuantityDTO;

    public QuantitiesWrapper() {}

    public QuantityDTO getThisQuantityDTO() {
        return this.thisQuantityDTO;
    }

    public void setThisQuantityDTO(QuantityDTO thisQuantityDTO) {
        this.thisQuantityDTO = thisQuantityDTO;
    }

    public QuantityDTO getThatQuantityDTO() {
        return this.thatQuantityDTO;
    }

    public void setThatQuantityDTO(QuantityDTO thatQuantityDTO) {
        this.thatQuantityDTO = thatQuantityDTO;
    }
}
